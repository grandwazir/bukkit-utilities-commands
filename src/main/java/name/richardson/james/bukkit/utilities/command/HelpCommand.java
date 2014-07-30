/*
 * Copyright (c) 2014 James Richardson.
 *
 * HelpCommand.java is part of BukkitUtilities.
 *
 * bukkit-utilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * bukkit-utilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * bukkit-utilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.command;

import java.util.*;

import name.richardson.james.bukkit.utilities.command.argument.Argument;
import name.richardson.james.bukkit.utilities.command.argument.ArgumentMetadata;
import name.richardson.james.bukkit.utilities.command.argument.PositionalArgument;
import name.richardson.james.bukkit.utilities.command.argument.SimpleArgumentMetadata;
import name.richardson.james.bukkit.utilities.command.argument.suggester.StringSuggester;
import name.richardson.james.bukkit.utilities.command.argument.suggester.Suggester;
import name.richardson.james.bukkit.utilities.command.localisation.Messages;
import name.richardson.james.bukkit.utilities.command.localisation.MessagesFactory;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitScheduler;

public class HelpCommand extends AbstractSynchronousCommand {

	private static final Messages MESSAGES = MessagesFactory.getColouredMessages();
	private final Argument commandName;
	private final Map<String, Command> commands;
	private final String prefix;
	private final PluginDescriptionFile description;

	public HelpCommand(Plugin plugin, BukkitScheduler scheduler, Iterable<Command> commands, PluginDescriptionFile description, String prefix) {
		super(plugin, scheduler);
		this.description = description;
		this.prefix = "/" + prefix;
		this.commands = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		for (Command command : commands) {
			this.commands.put(command.getName(), command);
		}
		ArgumentMetadata metadata = new SimpleArgumentMetadata(MESSAGES.helpCommandArgumentId(), MESSAGES.helpCommandArgumentName(), MESSAGES.helpCommandArgumentDesc());
		Suggester suggester = new StringSuggester(this.commands.keySet());
		Argument unused = new PositionalArgument(metadata, suggester, 0);
		commandName = new PositionalArgument(metadata, suggester, 1);
		addArgument(unused);
		addArgument(commandName);
	}

	private static Suggester createSuggester(Iterable<Command> commands) {
		Set<String> names = new HashSet<String>();
		for (Command command : commands) {
			names.add(command.getName());
		}
		return new StringSuggester(names);
	}

	@Override
	protected final void execute() {
		String commandName = this.commandName.getString();
		Command selectedCommand = (commandName == null) ? null : commands.get(commandName);
		if (commandName != null && commandName.equalsIgnoreCase("help")) {
			selectedCommand = this;
		}
		if (selectedCommand == null) {
			addMessage(MESSAGES.pluginName(description.getName(), description.getVersion()));
			addMessage(MESSAGES.pluginDescription(description.getDescription()));
			addMessage(MESSAGES.helpCommandUsage(prefix, getName(), this.commandName.getUsage()));
			for (Command command : commands.values()) {
				CommandSender commandSender = getContext().getCommandSender();
				if (!command.isAuthorised(commandSender)) continue;
				addMessage(MESSAGES.helpCommandListItem(prefix, command.getName(), command.getUsage()));
			}
		} else {
			addMessage(MESSAGES.helpCommandExtendedDescription(selectedCommand.getDescription()));
			addMessage(MESSAGES.helpCommandListItem(prefix, selectedCommand.getName(), selectedCommand.getUsage()));
			for (String message : selectedCommand.getExtendedUsage()) {
				addMessage(message);
			}
		}
	}

	@Override
	protected final Set<String> getPermissions() {
		return Collections.emptySet();
	}

	@Override
	public final String getName() {
		return MESSAGES.helpCommandName();
	}

	@Override
	public final String getDescription() {
		return MESSAGES.helpCommandDescription();
	}

}
