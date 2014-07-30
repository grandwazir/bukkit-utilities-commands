package name.richardson.james.bukkit.utilities.command;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class HelpCommandTest {

	private HelpCommand command;
	private Command nestedCommand;
	private CommandSender sender;

	@Test
	public void checkCommandListFormattedCorrectly() {
		when(nestedCommand.isAuthorised((Permissible) any())).thenReturn(true);
		ArgumentCaptor<String> captor = setupContextAndArgumentCaptor("");
		Assert.assertEquals("Plugin name and version not formatted correctly!", captor.getAllValues().get(0), "§d§bTestPlugin§d (§b1.0.0§d)");
		Assert.assertEquals("Plugin description not formatted correctly!", captor.getAllValues().get(1), "§dnull.");
		Assert.assertEquals("Help usage command is not formatted correctly!", captor.getAllValues().get(2), "§aType §b/test§a §bhelp§a §b[command]§a for help using a command.");
		Assert.assertEquals("Help list item is not formatted correctly!", captor.getAllValues().get(3), "§e/test abc null");
	}

	@Before
	public void setup() throws Exception {
		Plugin plugin = mock(Plugin.class);
		BukkitScheduler scheduler = mock(BukkitScheduler.class);
		Collection<Command> commands = new ArrayList<Command>();
		nestedCommand = mock(Command.class);
		when(nestedCommand.getName()).thenReturn("abc");
		when(nestedCommand.getDescription()).thenReturn("A mocked command");
		when(nestedCommand.getUsage()).thenReturn("abc <name>");
		commands.add(nestedCommand);
		sender = mock(CommandSender.class);
		PluginDescriptionFile descriptionFile = new PluginDescriptionFile("TestPlugin", "1.0.0", null);
		command = new HelpCommand(plugin, scheduler, commands, descriptionFile, "test");
	}

	@Test
	public void whenSendingCommandListOmitRestrictedCommands() {
		when(nestedCommand.isAuthorised((Permissible) any())).thenReturn(false);
		ArgumentCaptor<String> captor = setupContextAndArgumentCaptor("");
		Assert.assertEquals("Command list should only contain 3 entries since one command should be omitted.", captor.getAllValues().size(), 3);
	}

	@Test
	public void whenRequestingCommandInfoReturnExtendedHelp() {
		ArgumentCaptor<String> captor = setupContextAndArgumentCaptor("help", "help");
		Assert.assertEquals("Command description not formatted correctly!", "§dProvides additional help on a command.", captor.getAllValues().get(0));
		Assert.assertEquals("Command description not formatted correctly!", "§e/test help [command]" , captor.getAllValues().get(1));
		Assert.assertEquals("Command description not formatted correctly!", "§e- command §a(the id of the command you want help with)", captor.getAllValues().get(2));

	}

	private ArgumentCaptor<String> setupContextAndArgumentCaptor(String... arguments) {
		CommandContext context = new PassthroughCommandContext(arguments, sender);
		command.schedule(context);
		command.run();
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(sender, atLeastOnce()).sendMessage(captor.capture());
		return captor;
	}
}
