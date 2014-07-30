package name.richardson.james.bukkit.utilities.command.argument;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class DummyArgument implements Argument {

	@Override
	public final String getId() {
		return "";
	}

	@Override
	public final String getName() {
		return "";
	}

	@Override
	public final String getError() {
		return "";
	}

	@Override
	public final String getDescription() {
		return "";
	}

	@Override
	public final String getString() {
		return "";
	}

	@Override
	public final Collection<String> getStrings() {
		return Collections.emptySet();
	}

	@Override
	public final boolean isLastArgument(String arguments) {
		return false;
	}

	@Override
	public final String getUsage() {
		return "";
	}

	@Override
	public final void parseValue(String argument) throws InvalidArgumentException {

	}

	@Override
	public final Set<String> suggestValue(String argument) {
		return Collections.emptySet();
	}
}
