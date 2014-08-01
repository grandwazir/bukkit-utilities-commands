/*******************************************************************************
 Copyright (c) 2014 James Richardson.

 AbstractAsynchronousCommandTest.java is part of BukkitUtilities.

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
