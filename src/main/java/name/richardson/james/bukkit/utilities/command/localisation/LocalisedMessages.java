package name.richardson.james.bukkit.utilities.command.localisation;

import com.vityuk.ginger.Localizable;

public interface LocalisedMessages extends Localizable {

	String invalidArgument(String argumentName, String reason);
	String noPermission();

}
