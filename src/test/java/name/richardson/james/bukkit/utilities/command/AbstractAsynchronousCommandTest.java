package name.richardson.james.bukkit.utilities.command;

import java.util.Set;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@SuppressWarnings("ALL")
@RunWith(MockitoJUnitRunner.class)
public class AbstractAsynchronousCommandTest {

	private AbstractAsynchronousCommand command;
	@Mock private CommandContext context;
	@Mock private Plugin plugin;
	@Mock private BukkitScheduler scheduler;

	@Before public void setup() {
		command = new TestAsynchronousCommand(plugin, scheduler);
	}

	@Test public void whenSchedulingCommandQueueCommandContextCorrectly() {
		command.schedule(context);
		Assert.assertEquals("Command context has not been added to the queue correctly!", context, command.getNextScheduledContext());
	}

	@Test public void whenSchedulingCommandRegisterAsAsynchronousTask() {
		command.schedule(context);
		verify(scheduler).runTaskAsynchronously(plugin, command);
	}

	private class TestAsynchronousCommand extends AbstractAsynchronousCommand {

		protected TestAsynchronousCommand(Plugin plugin, BukkitScheduler scheduler) {
			super(plugin, scheduler);
		}

		@Override
		public String getDescription() {
			return null;
		}

		@Override
		public String getName() {
			return null;
		}

		@Override
		public Set<String> getPermissions() {
			return null;
		}

		@Override
		protected void execute() {

		}
	}

}
