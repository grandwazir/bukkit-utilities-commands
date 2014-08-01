/*******************************************************************************
 Copyright (c) 2014 James Richardson.

 SimpleCommandInvoker.java is part of BukkitUtilities.

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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class SimpleCommandInvoker extends AbstractCommandInvoker {

	private final Command command;

	public SimpleCommandInvoker(Plugin plugin, BukkitScheduler scheduler, Command command) {
		super(plugin, scheduler);
		this.command = command;
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		CommandContext context = SimpleCommandContext.create(args, sender, command.getPermissions(), 0);
		command.schedule(context);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String alias, String[] args) {
		CommandContext context = SimpleCommandContext.create(args, sender, command.getPermissions(), 0);
		Set<String> suggestions = command.getSuggestions(context.getArguments());
		return new ArrayList<>(suggestions);
	}

}
