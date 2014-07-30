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
