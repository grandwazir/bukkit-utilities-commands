/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 Command.java is part of bukkit-utilities.

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
import java.util.Map;
import java.util.Set;

import name.richardson.james.bukkit.utilities.command.argument.ArgumentInvoker;

import org.bukkit.permissions.Permissible;

/**
 * Executes actions on behalf of a user, notifies the user of the outcome. Used anywhere where a user is required instructions to a plugin interactively.
 * Commands also provide a method for users to check to see if they are authorised to use the command in the first place.
 */
public interface Command extends Runnable {

	public String getDescription();
	public Collection<String> getExtendedUsage();
	public String getName();
	public Set<String> getSuggestions(String arguments);
	public String getUsage();
	public boolean isAuthorised(Permissible permissible);
	public void schedule(CommandContext context);

}
