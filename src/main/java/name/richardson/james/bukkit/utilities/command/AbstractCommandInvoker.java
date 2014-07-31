/*******************************************************************************
 Copyright (c) 2014 James Richardson.

 AbstractCommandInvoker.java is part of BukkitUtilities.

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
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * This abstract implementation provides final methods for all the methods specified in the CommandInvoker interface. It
 * should be used for convenience when implementing your own CommandInvokers.
 */
public abstract class AbstractCommandInvoker implements CommandInvoker {

	private final Map<String, Command> commands;
	private final Plugin plugin;
	private final BukkitScheduler scheduler;

	public AbstractCommandInvoker(Plugin plugin, BukkitScheduler scheduler) {
		this.plugin = plugin;
		this.scheduler = scheduler;
		commands = new TreeMap<String, Command>(String.CASE_INSENSITIVE_ORDER);
	}

	@Override
	public final void addCommand(Command command) {
		Validate.notNull(command);
		commands.put(command.getName(), command);
	}

	@Override
	public final void addCommands(Collection<Command> commands) {
		Validate.notNull(commands);
		for (Command command : commands) {
			this.commands.put(command.getName(), command);
		}
	}

	@Override
	public final Map<String, Command> getCommands() {
		return Collections.unmodifiableMap(commands);
	}

	protected final Command getCommand(String[] arguments) {
		String name = (arguments.length == 0) ? null : arguments[0];
		System.out.print(name);

		if (name != null && getCommands().containsKey(name)) {
			return getCommands().get(name);
		} else {
			return null;
		}
	}

	protected Plugin getPlugin() {
		return plugin;
	}

	protected BukkitScheduler getScheduler() {
		return scheduler;
	}
}
