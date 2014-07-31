/*******************************************************************************
 Copyright (c) 2014 James Richardson.

 Messages.java is part of BukkitUtilities.

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
package name.richardson.james.bukkit.utilities.command.localisation;

import name.richardson.james.bukkit.utilities.localisation.ColouredMessage;
import name.richardson.james.bukkit.utilities.localisation.MessageType;

public interface Messages {

	String helpCommandArgumentDesc();
	String helpCommandArgumentId();
	String helpCommandArgumentName();
	@ColouredMessage(type = MessageType.HEADER, highlight = false)
	String helpCommandExtendedDescription(String description);
	@ColouredMessage(type = MessageType.WARNING, highlight = false)
	String helpCommandExtendedUsage(String usage);
	String helpCommandName();
	String helpCommandDescription();
	@ColouredMessage(type = MessageType.WARNING, highlight = false)
	String helpCommandListItem(String prefix, String name, String argumentUsage);
	@ColouredMessage(type = MessageType.NOTICE)
	String helpCommandUsage(String prefix, String name, String usage);
	@ColouredMessage(type = MessageType.ERROR)
	String invalidArgument();
	@ColouredMessage(type = MessageType.ERROR)
	String noPermission();
	@ColouredMessage(type = MessageType.HEADER, highlight = false)
	String pluginDescription(String description);
	@ColouredMessage(type = MessageType.HEADER)
	String pluginName(String name, String version);
}
