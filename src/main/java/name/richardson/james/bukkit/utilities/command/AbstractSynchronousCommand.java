/*******************************************************************************
 Copyright (c) 2014 James Richardson.

 AbstractSynchronousCommand.java is part of BukkitUtilities.

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

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class AbstractSynchronousCommand extends AbstractCommand {

	private final Queue<CommandContext> contexts;

	protected AbstractSynchronousCommand(Plugin plugin, BukkitScheduler scheduler) {
		super(plugin, scheduler);
		contexts = new LinkedList<>();
	}

	@Override
	public final void schedule(CommandContext context) {
		contexts.add(context);
		getScheduler().runTask(getPlugin(), this);
	}

	@Override
	protected final CommandContext getNextScheduledContext() {
		return contexts.remove();
	}
}

