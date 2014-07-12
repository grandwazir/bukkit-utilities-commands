/**
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

import name.richardson.james.bukkit.utilities.command.argument.Argument;
import name.richardson.james.bukkit.utilities.command.argument.ArgumentInvoker;
import name.richardson.james.bukkit.utilities.command.argument.InvalidArgumentException;
import name.richardson.james.bukkit.utilities.command.argument.SimpleArgumentInvoker;
import name.richardson.james.bukkit.utilities.command.localisation.Localisation;
import name.richardson.james.bukkit.utilities.command.localisation.LocalisedMessages;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class AbstractCommand implements Command {

	private static final LocalisedMessages LOCALISED_MESSAGES = Localisation.getLocalisedMessages();

	private final CommandPermissions annotation;
	private final ArgumentInvoker argumentInvoker;
	private final Plugin plugin;
	private final BukkitScheduler scheduler;

	public AbstractCommand(Plugin plugin, BukkitScheduler scheduler) {
		this.plugin = plugin;
		this.scheduler = scheduler;
		argumentInvoker = new SimpleArgumentInvoker();
		annotation = this.getClass().getAnnotation(CommandPermissions.class);
	}

	@Override
	public void addArgument(Argument argument) {
		argumentInvoker.addArgument(argument);
	}

	@Override
	public Collection<String> getExtendedUsage() {
		return argumentInvoker.getExtendedUsage();
	}

	@Override
	public String getUsage() {
		String arguments = getColouredArgumentUsage();
		return ChatColor.YELLOW + getName() + " " + arguments;
	}

	/**
	 * Returns {@code true} if the user is authorised to use this command. <p/> Authorisation does not guarantee that the user may use all the features associated
	 * with a command.
	 *
	 * @param permissible the permissible requesting authorisation
	 * @return {@code true} if the user is authorised; {@code false} otherwise
	 * @since 6.0.0
	 */
	@Override
	public boolean isAuthorised(Permissible permissible) {
		boolean result = false;
		if (getAnnotation() != null) {
			for (String permission : getAnnotation().permissions()) {
				if (permissible.hasPermission(permission)) {
					result = true;
				}
			}
		} else {
			result = true;
		}
		return result;
	}

	@Override
	public void parseArguments(String arguments) throws InvalidArgumentException {
		argumentInvoker.parseArguments(arguments);
	}

	@Override
	public void removeArgument(Argument argument) {
		argumentInvoker.removeArgument(argument);
	}

	@Override
	public synchronized void run() {
		Validate.notNull(getContext());
		if (isAuthorised(getContext().getCommandSender())) {
			try {
				this.parseArguments(getContext().getArguments());
				this.execute();
			} catch (InvalidArgumentException e) {
				CommandSender sender = getContext().getCommandSender();
				sender.sendMessage(ChatColor.RED + LOCALISED_MESSAGES.invalidArgument());
				sender.sendMessage(ChatColor.YELLOW + e.getError());
			}
		} else {
			CommandSender sender = getContext().getCommandSender();
			sender.sendMessage(ChatColor.RED + LOCALISED_MESSAGES.noPermission());
		}
	}

	@Override
	public Set<String> suggestArguments(String arguments) {
		return argumentInvoker.suggestArguments(arguments);
	}

	protected abstract void execute();

	protected CommandPermissions getAnnotation() {
		return annotation;
	}

	protected abstract CommandContext getContext();

	protected Plugin getPlugin() {
		return plugin;
	}

	protected BukkitScheduler getScheduler() {
		return scheduler;
	}

	private String getColouredArgumentUsage() {
		String usage = argumentInvoker.getUsage();
		usage = usage.replaceAll("\\<", ChatColor.YELLOW + "\\<");
		usage = usage.replaceAll("\\[", ChatColor.GREEN + "\\[");
		return usage;
	}
}
