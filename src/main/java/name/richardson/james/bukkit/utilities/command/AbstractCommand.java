/*
 * Copyright (c) 2014 James Richardson.
 *
 * AbstractCommand.java is part of BukkitUtilities.
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

import java.util.Collection;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.apache.commons.lang.Validate;

import name.richardson.james.bukkit.utilities.command.argument.Argument;
import name.richardson.james.bukkit.utilities.command.argument.ArgumentInvoker;
import name.richardson.james.bukkit.utilities.command.argument.InvalidArgumentException;
import name.richardson.james.bukkit.utilities.command.argument.SimpleArgumentInvoker;
import name.richardson.james.bukkit.utilities.localisation.Localised;

import static name.richardson.james.bukkit.utilities.localisation.BukkitUtilities.INVOKER_INVALID_ARGUMENT;
import static name.richardson.james.bukkit.utilities.localisation.BukkitUtilities.INVOKER_NO_PERMISSION;

public abstract class AbstractCommand implements Command {

	private final ArgumentInvoker argumentInvoker;
	private final String desc;
	private final String name;
	private CommandContext context;

	public AbstractCommand(Localised name, Localised desc) {
		argumentInvoker = new SimpleArgumentInvoker();
		this.name = name.asMessage();
		this.desc = desc.asMessage();
	}

	@Override
	public final void addArgument(final Argument argument) {
		argumentInvoker.addArgument(argument);
	}

	@Override
	public String getDescription() {
		return desc;
	}

	@Override
	public Collection<String> getExtendedUsage() {
		return argumentInvoker.getExtendedUsage();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getUsage() {
		String arguments = getColouredArgumentUsage();
		return ChatColor.YELLOW + getName() + " " + arguments;
	}

	@Override
	public final void parseArguments(final String arguments)
	throws InvalidArgumentException {
		argumentInvoker.parseArguments(arguments);
	}

	@Override
	public final void removeArgument(final Argument argument) {
		argumentInvoker.removeArgument(argument);
	}

	public final void run() {
		Validate.notNull(getContext());
		if (isAuthorised(getContext().getCommandSender())) {
			try {
				this.parseArguments(getContext().getArguments());
				this.execute();
			} catch (InvalidArgumentException e) {
				CommandSender sender = getContext().getCommandSender();
				sender.sendMessage(INVOKER_INVALID_ARGUMENT.asErrorMessage());
				sender.sendMessage(ChatColor.YELLOW + e.getError());
			}
		} else {
			CommandSender sender = getContext().getCommandSender();
			sender.sendMessage(INVOKER_NO_PERMISSION.asErrorMessage());
		}
	}

	@Override
	public void setContext(final CommandContext context) {
		this.context = context;
	}

	@Override
	public final Set<String> suggestArguments(final String arguments) {
		return argumentInvoker.suggestArguments(arguments);
	}

	protected abstract void execute();

	protected final CommandContext getContext() {
		return context;
	}

	private String getColouredArgumentUsage() {
		String usage = argumentInvoker.getUsage();
		usage = usage.replaceAll("\\<", ChatColor.YELLOW + "\\<");
		usage = usage.replaceAll("\\[", ChatColor.GREEN + "\\[");
		return usage;
	}

}
