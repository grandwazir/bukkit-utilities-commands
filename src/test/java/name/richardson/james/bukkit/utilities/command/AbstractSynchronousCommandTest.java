package name.richardson.james.bukkit.utilities.command;

import java.util.Map;

import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AbstractSynchronousCommandTest {

	private TestCommand command;
	private Plugin plugin;
	private BukkitScheduler scheduler;

	@Before
	public void setUp() throws Exception {
		plugin = mock(Plugin.class);
		scheduler = mock(BukkitScheduler.class);
		command = new TestCommand(plugin, scheduler);
	}

	@Test
	public void shouldCheckAllPermissionsAgainstPermissible() {
		Permissible permissible = mock(Permissible.class);
		command.getPermissionMap(permissible);
		verify(permissible).hasPermission("a");
		verify(permissible).hasPermission("b");
	}

	@Test
	public void shouldReturnCorrectPermissionMap() {
		Permissible permissible = mock(Permissible.class);
		when(permissible.hasPermission("a")).thenReturn(true);
		when(permissible.hasPermission("b")).thenReturn(false);
		Map<String,Boolean> map = command.getPermissionMap(permissible);
		assertTrue("Permission should be true!", map.get("a"));
		assertFalse("Permission should be false", map.get("b"));
	}

	@Test
	public void whenSchedulingAddContextToQueue() {
		CommandContext context = mock(CommandContext.class);
		command.schedule(context);
		assertSame("Command context should be the same!", context, command.getNextScheduledContext());
	}

	@Test
	public void whenSchedulingRunAsASynchronousTask() {
		CommandContext context = mock(CommandContext.class);
		command.schedule(context);
		verify(scheduler, only()).runTask(plugin, command);
	}


	@CommandPermissions(permissions = {"a","b"})
	private class TestCommand extends AbstractSynchronousCommand {

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
