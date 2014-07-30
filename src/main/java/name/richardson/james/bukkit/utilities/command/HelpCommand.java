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
	private final PositionalArgument commandName;

	private final Map<String, Command> commands = new TreeMap<String, Command>();
	private final String prefix;
	private final PluginDescriptionFile description;

	public HelpCommand(Plugin plugin, BukkitScheduler scheduler, PluginDescriptionFile description, String prefix, Iterable<Command> commands) {
		super(plugin, scheduler);
		this.description = description;
		this.prefix = "/" + prefix;
		for (Command command : commands) {
			this.commands.put(command.getName(), command);
		}
		SimpleArgumentMetadata metadata = new SimpleArgumentMetadata(MESSAGES.helpCommandArgumentId(), MESSAGES.helpCommandArgumentName(), MESSAGES.helpCommandArgumentDesc());
		StringSuggester suggester = new StringSuggester(this.commands.keySet());
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
	public boolean isAuthorised(Permissible permissible) {
		return true;
	}

	@Override
	public String getArgumentUsage() {
		return commandName.getUsage();
	}

	@Override
	protected void execute() {
		Command selectedCommand = (commandName.getString() == null) ? null : commands.get(commandName.getString());
		CommandSender sender = getCurrentContext().getCommandSender();
		if (commandName.getString() != null && commandName.getString().equalsIgnoreCase("help")) {
			selectedCommand = this;
		}
		if (selectedCommand == null) {
			sender.sendMessage(MESSAGES.pluginName(description.getName(), description.getVersion()));
			sender.sendMessage(MESSAGES.pluginDescription(description.getDescription()));
			sender.sendMessage(MESSAGES.helpCommandUsage(prefix, this.getName(), commandName.getUsage()));
			for (Command command : commands.values()) {
				if (!command.isAuthorised(sender)) continue;
				sender.sendMessage(MESSAGES.helpCommandListItem(prefix, command.getName(), command.getArgumentUsage()));
			}
		} else {
			sender.sendMessage(MESSAGES.helpCommandExtendedDescription(selectedCommand.getDescription()));
			sender.sendMessage(MESSAGES.helpCommandListItem(prefix, selectedCommand.getName(), selectedCommand.getArgumentUsage()));
			for (String message : selectedCommand.getExtendedUsage()) {
				sender.sendMessage(message);
			}
		}
	}

	@Override
	/** Needed to remove the dummy argument from the usage table */
	public Collection<String> getExtendedUsage() {
		Collection<String> usage = super.getExtendedUsage();
		ArrayList<String> list = new ArrayList<String>(usage);
		list.remove(0);
		return list;
	}

	/**
	 * Return the short name of this command.
	 *
	 * @return the localised name of the command
	 */
	@Override
	public String getName() {
		return MESSAGES.helpCommandName();
	}

	/**
	 * Returns a brief description of what this command does.
	 *
	 * @return the localised description of the command
	 */
	@Override
	public String getDescription() {
		return MESSAGES.helpCommandDescription();
	}

}
