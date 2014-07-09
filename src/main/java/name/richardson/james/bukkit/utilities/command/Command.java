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

import java.util.Map;

import name.richardson.james.bukkit.utilities.command.argument.ArgumentInvoker;

import org.bukkit.permissions.Permissible;

/**
 * Executes actions on behalf of a user, notifies the user of the outcome. Used anywhere where a user is required instructions to a plugin interactively.
 * Commands also provide a method for users to check to see if they are authorised to use the command in the first place.
 */
public interface Command extends Runnable, ArgumentInvoker {

	/**
	 * Returns a brief description of what this command does.
	 *
	 * @return the localised description of the command
	 */
	public String getDescription();

	/**
	 * Return the short name of this command.
	 *
	 * @return the localised name of the command
	 */
	public String getName();

	/**
	 * Returns a map containing details of all the permissions the player has.
	 * The key is the name of the permission and the value is if the player has that
	 * permission or not.
	 *
	 * @param permissible
	 * @return the permissions the player has.
	 */
	public Map<String, Boolean> getPermissionMap(Permissible permissible);

	/**
	 * Returns the arguments that can be passed to this command.
	 *
	 * @return the localised usage message
	 */
	public String getUsage();

	/**
	 * Returns {@code true} if the user is authorised to use this command. <p/> Authorisation does not guarantee that the user may use all the features associated
	 * with a command.
	 *
	 * @param permissible the permissible requesting authorisation
	 * @return {@code true} if the user is authorised; {@code false} otherwise
	 * @since 6.0.0
	 */
	public boolean isAuthorised(Permissible permissible);

	public void schedule(CommandContext context);
}
