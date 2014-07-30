/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractCommandContext.java is part of bukkit-utilities.

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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * An example implementation of {@link name.richardson.james.bukkit.utilities.command.CommandContext}. This implementation makes no additional verification checks on requested argumentsList and may throw
 * IndexOutOfBoundExceptions from the internal backing storage.
 */
public class AbstractCommandContext implements CommandContext {

	private final String arguments;
	private final CommandSender commandSender;

	/**
	 * Constructs a new AbstractCommandContext with the argumentsList and CommandSender.
	 *
	 * @param arguments     provided argumentsList
	 * @param commandSender the CommandSender executing the command
	 */
	protected AbstractCommandContext(String[] arguments, CommandSender commandSender) {
		Validate.notNull(arguments);
		Validate.notNull(commandSender);
		this.commandSender = commandSender;
		this.arguments = StringUtils.join(arguments, " ");
	}

	@Override
	public String getArguments() {
		return arguments;
	}

	/**
	 * Get the CommandSender who called this command.
	 *
	 * @return the CommandSender
	 */
	@Override
	public CommandSender getCommandSender() {
		return commandSender;
	}

	@Override
	public boolean isConsoleCommandSender() {
		return !(commandSender instanceof Player);
	}

}
