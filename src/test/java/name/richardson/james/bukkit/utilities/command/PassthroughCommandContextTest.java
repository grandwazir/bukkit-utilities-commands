package name.richardson.james.bukkit.utilities.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class PassthroughCommandContextTest {

	private static final String[] ARGUMENTS = new String[]{"/ban", "one", "two", "three"};
	private PassthroughCommandContext context;

	@Before
	public void setup() {
		CommandSender sender = mock(CommandSender.class);
		context = new PassthroughCommandContext(ARGUMENTS, sender);
	}

	@Test
	public void contextContainsAllArguments() {
		String[] joined = StringUtils.split(context.getArguments(), " ");
		Assert.assertEquals("Passed arguments are not the same as the ones passed", ARGUMENTS, joined);
	}


}
