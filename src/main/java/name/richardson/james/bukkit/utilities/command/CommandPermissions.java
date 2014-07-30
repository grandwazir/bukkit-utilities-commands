package name.richardson.james.bukkit.utilities.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface CommandPermissions {

	String[] permissions();
}
