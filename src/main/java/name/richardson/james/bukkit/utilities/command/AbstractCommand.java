/*******************************************************************************
 Copyright (c) 2014 James Richardson.

 AbstractCommand.java is part of BukkitUtilities.

 BukkitUtilities is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.utilities.command;

import java.util.Collection;
import java.util.Set;

import name.richardson.james.bukkit.utilities.command.argument.Argument;
import name.richardson.james.bukkit.utilities.command.argument.ArgumentInvoker;
import name.richardson.james.bukkit.utilities.command.argument.InvalidArgumentException;
import name.richardson.james.bukkit.utilities.command.argument.SimpleArgumentInvoker;
import name.richardson.james.bukkit.utilities.command.localisation.Messages;
import name.richardson.james.bukkit.utilities.command.localisation.MessagesFactory;

import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class AbstractCommand implements Command {

	private static final Messages MESSAGES = MessagesFactory.getColouredMessages();
	private final ArgumentInvoker argumentInvoker;
	private final Plugin plugin;
	private final BukkitScheduler scheduler;
	private CommandContext context;

	public AbstractCommand(Plugin plugin, BukkitScheduler scheduler) {
		this.plugin = plugin;
		this.scheduler = scheduler;
		argumentInvoker = new SimpleArgumentInvoker();
	}

	@Override
	public final Collection<String> getExtendedUsage() {
		return argumentInvoker.getExtendedUsage();
	}

	@Override
	public final Set<String> getSuggestions(String arguments) {
		return argumentInvoker.suggestArguments(arguments);
	}

	@Override
	public final String getUsage() {
		return argumentInvoker.getUsage();
	}

	@Override
	public final boolean isAuthorised(Permissible permissible) {
		if (getPermissions().isEmpty()) {
			return true;
		} else {
			for (String permission : getPermissions()) {
				if (permissible.hasPermission(permission))
					return true;
			}
		}
		return false;
	}

	@Override
	public final synchronized void run() {
		context = getNextScheduledContext();
		if (getContext().isAuthorised()) {
			try {
				parseArguments(getContext().getArguments());
				execute();
			} catch (InvalidArgumentException e) {
				addMessage(MESSAGES.invalidArgument());
			}
		} else {
			addMessage(MESSAGES.noPermission());
		}
		sendMessages();
	}

	protected final void addArgument(Argument argument) {
		argumentInvoker.addArgument(argument);
	}

	protected final void addMessage(String message) {
		context.addMessage(message);
	}

	protected final void addMessages(Collection<String> messages) {
		context.addMessages(messages);
	}

	protected abstract void execute();

	protected final CommandContext getContext() {
		return context;
	}

	protected abstract CommandContext getNextScheduledContext();

	protected final Plugin getPlugin() {
		return plugin;
	}

	protected final BukkitScheduler getScheduler() {
		return scheduler;
	}

	protected final boolean isAuthorised() {
		return context.isAuthorised();
	}

	protected final boolean isAuthorised(String permission) {
		return context.isAuthorised(permission);
	}

	protected final void parseArguments(String arguments) throws InvalidArgumentException {
		argumentInvoker.parseArguments(arguments);
	}

	protected final int sendMessages() {
		return context.sendMessages();
	}
}
