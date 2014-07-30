package name.richardson.james.bukkit.utilities.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class NestedCommandContextTest {

	private static final String[] ARGUMENTS = new String[]{"/ban", "one", "two", "three"};
	private NestedCommandContext context;

	@Before
	public void setup() {
		CommandSender sender = mock(CommandSender.class);
		context = new NestedCommandContext(ARGUMENTS, sender);
	}

	@Test
	public void contextContainsAllArguments() {
		Assert.assertFalse("Arguments should not contain the first item from the passed array", context.getArguments().contains("/ban"));
	}
}
