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

import java.util.*;

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
	private final Map<String, Boolean> permissions;
	private List<String> messages;

	protected AbstractCommandContext(String[] arguments, CommandSender commandSender, Collection<String> permissions) {
		Validate.notNull(arguments);
		Validate.notNull(commandSender);
		this.commandSender = commandSender;
		this.arguments = StringUtils.join(arguments, " ");
		this.permissions = new HashMap<>(permissions.size());
		messages = new ArrayList<>();
		for (String permission : permissions) {
			boolean hasPermission = commandSender.hasPermission(permission);
			this.permissions.put(permission, hasPermission);
		}
	}

	@Override
	public final String getArguments() {
		return arguments;
	}

	@Override
	public final CommandSender getCommandSender() {
		return commandSender;
	}

	@Override
	public final boolean isAuthorised(String permission) {
		return permissions.get(permission);
	}

	@Override
	public final boolean isAuthorised() {
		return permissions.containsValue(true);
	}

	@Override
	public final void addMessage(String message) {
		messages.add(message);
	}

	@Override
	public final void addMessages(Collection<String> messages) {
		messages.addAll(messages);
	}

	@Override
	public final int sendMessages() {
		String[] messages = this.messages.toArray(new String[this.messages.size()]);
		commandSender.sendMessage(messages);
		this.messages.clear();
		return messages.length;
	}

}
