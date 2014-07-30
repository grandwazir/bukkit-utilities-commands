package name.richardson.james.bukkit.utilities.command;

import java.util.Map;

import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class AbstractAsynchronousCommandTest {

	private AbstractAsynchronousCommand command;
	private Plugin plugin;
	private BukkitScheduler scheduler;

	@Before
	public void setUp() throws Exception {
		plugin = mock(Plugin.class);
		scheduler = mock(BukkitScheduler.class, RETURNS_MOCKS);
		command = new TestCommand(plugin, scheduler);
	}

	@Test
	public void whenSchedulingAddContextToQueue() {
		CommandContext context = mock(CommandContext.class);
		command.schedule(context);
		assertSame("Command context should be the same!", context, command.getNextScheduledContext());
	}

	@Test
	public void whenSchedulingRunAsAsynchronousTask() {
		CommandContext context = mock(CommandContext.class);
		command.schedule(context);
		verify(scheduler, only()).runTaskAsynchronously(plugin, command);
	}

	@Test
	public void shouldCheckAllPermissionsAgainstPermissible() throws Exception {
		Permissible permissible = mock(Permissible.class);
		AbstractAsynchronousCommand.PermissionTask task = new AbstractAsynchronousCommand.PermissionTask(permissible, "a", "b");
		task.call();
		verify(permissible).hasPermission("a");
		verify(permissible).hasPermission("b");
	}

	@Test
	public void shouldReturnCorrectPermissionMap() throws Exception {
		Permissible permissible = mock(Permissible.class);
		when(permissible.hasPermission("a")).thenReturn(true);
		when(permissible.hasPermission("b")).thenReturn(false);
		AbstractAsynchronousCommand.PermissionTask task = new AbstractAsynchronousCommand.PermissionTask(permissible, "a", "b");
		Map<String, Boolean> map = task.call();
		assertTrue("Permission should be true!", map.get("a"));
		assertFalse("Permission should be false", map.get("b"));
	}


	@CommandPermissions(permissions = {"a","b"})
	private class TestCommand extends AbstractAsynchronousCommand {

		protected TestCommand(Plugin plugin, BukkitScheduler scheduler) {
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
		protected void execute() {

		}

	}
}
