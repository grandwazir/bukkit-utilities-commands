/*******************************************************************************
 Copyright (c) 2014 James Richardson.

 RootCommandInvokerTest.java is part of BukkitUtilities.

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.avaje.ebean.validation.AssertFalse;
import com.google.common.base.Joiner;
import com.sun.swing.internal.plaf.synth.resources.synth_sv;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("ALL")
@RunWith(MockitoJUnitRunner.class)
public class RootCommandInvokerTest {

	private RootCommandInvoker invoker;
	@Mock private Plugin plugin;
	private String prefix;
	@Mock private BukkitScheduler scheduler;
	@Mock private Command command;
	@Mock private org.bukkit.command.Command bukkitCommand;
	@Mock private CommandSender sender;
	private PluginDescriptionFile descriptionFile = new PluginDescriptionFile("Test", "1.0.0", null);

	@Before public void setup() {
		prefix = "/test";
		when(command.getName()).thenReturn("test");
		when(plugin.getDescription()).thenReturn(descriptionFile);
		invoker = new RootCommandInvoker(plugin, scheduler, Arrays.asList(command), prefix);
	}

	@Test public void whenProvidedWithValidCommandPassthroughToThatCommand() {
		invoker.onCommand(sender, bukkitCommand, null, new String[]{"test"});
		verify(command).schedule(Mockito.any(CommandContext.class));
	}

	@Test public void whenProvidedWithInvalidCommandPassthroughToHelpCommand() {
		invoker.onCommand(sender, bukkitCommand, null, new String[]{"blah"});
		verify(command, never()).schedule(Mockito.any(CommandContext.class));
	}

	@Test public void whenProvidedWithNoCommandsPassthroughToHelpCommand() {
		invoker.onCommand(sender, bukkitCommand, null, new String[]{""});
		verify(command, never()).schedule(Mockito.any(CommandContext.class));
	}

	@Test public void whenProvidedWithValidCommandGetSuggestionsFromThatCommand() {
		invoker.onTabComplete(sender, bukkitCommand, null, new String[]{"test", "frank"});
		verify(command).getSuggestions(Mockito.eq("frank"));
	}

	@Test public void whenProvidedWithInvalidCommandGetSuggestionsFromHelpCommand() {
		invoker.onTabComplete(sender, bukkitCommand, null, new String[]{"blah", "frank"});
		verify(command, never()).getSuggestions(Mockito.any(String.class));
	}

	@Test public void whenProvidedWithInvalidCommandSendHelpAboutAllCommands() {
		when(command.isAuthorised((Permissible) any())).thenReturn(true);
		List<String> messages = executeHelpCommand(prefix, "blah");
		Assert.assertEquals("Command listing should contain 4 messages: " + messages.toString(), 4, messages.size());
	}


	@Test public void whenDisplayingCommandHelpShowCommandsThatUsersHavePermissionToUse() {
		when(command.isAuthorised((Permissible) any())).thenReturn(true);
		List<String> messages = executeHelpCommand(prefix, "blah");
		Assert.assertEquals("Command listing should contain 4 messages: " + messages.toString(), 4, messages.size());
	}

	@Test public void whenProvidedWithHelpArgumentShowExtendedUsage() {
		List<String> messages = executeHelpCommand(prefix, "help");
		Assert.assertTrue("Messages should include details about arguments: " + messages.toString(), messages.contains("§dProvides additional help on a command."));
	}

	@Test public void whenProvidedWithCommandArgumentShowExtendedHelp() {
		List<String> messages = executeHelpCommand(prefix, "test");
		verify(command).getExtendedUsage();
	}

	@Test public void whenDisplayingCommandHelpHideCommandsThatUsersDoNotHavePermissionToUse() {
		when(command.isAuthorised((Permissible) any())).thenReturn(false);
		List<String> messages = executeHelpCommand(prefix);
		Assert.assertEquals("Command listing should contain 3 messages: " + messages.toString(), 3, messages.size());
	}

	private List<String> executeHelpCommand(String ... arguments) {
		Command command = invoker.getHelpCommand();
		CommandContext context = SimpleCommandContext.create(arguments, sender, command.getPermissions(), 1);
		ArgumentCaptor<String[]> captor = new ArgumentCaptor<>();
		command.schedule(context);
		command.run();
		verify(sender).sendMessage(captor.capture());
		List<String> messages = new ArrayList<String>();
		messages.addAll(Arrays.asList(captor.getValue()));
		return messages;
	}
}
