package name.richardson.james.bukkit.utilities.command;

import java.util.*;

import name.richardson.james.bukkit.utilities.command.argument.*;
import name.richardson.james.bukkit.utilities.command.argument.suggester.StringSuggester;
import name.richardson.james.bukkit.utilities.command.argument.suggester.Suggester;
import name.richardson.james.bukkit.utilities.command.localisation.Messages;
import name.richardson.james.bukkit.utilities.command.localisation.MessagesFactory;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitScheduler;

public class RootCommandInvoker implements CommandInvoker {

	private static final Messages MESSAGES = MessagesFactory.getColouredMessages();
	private final TreeMap<String, name.richardson.james.bukkit.utilities.command.Command> commands;
	private final PluginDescriptionFile description;
	private final CommandInvoker invoker;
	private final Plugin plugin;
	private final String prefix;
	private final BukkitScheduler scheduler;

	public RootCommandInvoker(Plugin plugin, BukkitScheduler scheduler, Collection<name.richardson.james.bukkit.utilities.command.Command> commands, String prefix) {
		this.plugin = plugin;
		this.scheduler = scheduler;
		this.prefix = prefix;
		description = this.plugin.getDescription();
		this.commands = new TreeMap<String, name.richardson.james.bukkit.utilities.command.Command>(String.CASE_INSENSITIVE_ORDER);
		for (name.richardson.james.bukkit.utilities.command.Command command : commands) {
			this.commands.put(command.getName(), command);
		}
		name.richardson.james.bukkit.utilities.command.Command helpCommand = new HelpCommand();
		invoker = new FallthroughCommandInvoker(plugin, scheduler, helpCommand);
		invoker.addCommands(commands);
	}

	@Override
	public final void addCommand(name.richardson.james.bukkit.utilities.command.Command command) {
		invoker.addCommand(command);
	}

	@Override
	public final void addCommands(Collection<name.richardson.james.bukkit.utilities.command.Command> commands) {
		invoker.addCommands(commands);
	}

	@Override
	public final Map<String, name.richardson.james.bukkit.utilities.command.Command> getCommands() {
		return invoker.getCommands();
	}

	@Override
	public final boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
		return invoker.onCommand(sender, command, s, strings);
	}

	@Override
	public final List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
		return invoker.onTabComplete(sender, command, s, strings);
	}

	private class HelpCommand extends AbstractSynchronousCommand {

		private final Argument commandName;

		public HelpCommand() {
			super(plugin, scheduler);
			ArgumentMetadata metadata = new SimpleArgumentMetadata(MESSAGES.helpCommandArgumentId(), MESSAGES.helpCommandArgumentName(), MESSAGES.helpCommandArgumentDesc());
			Suggester suggester = new StringSuggester(commands.keySet());
			Argument unused = new DummyArgument();
			commandName = new PositionalArgument(metadata, suggester, 1);
			addArgument(unused);
			addArgument(commandName);
		}

		@Override
		public final String getDescription() {
			return MESSAGES.helpCommandDescription();
		}

		@Override
		public final String getName() {
			return MESSAGES.helpCommandName();
		}

		@Override
		public final Set<String> getPermissions() {
			return Collections.emptySet();
		}

		@Override
		protected final void execute() {
			String commandName = this.commandName.getString();
			name.richardson.james.bukkit.utilities.command.Command selectedCommand = (commandName == null) ? null : commands.get(commandName);
			if (commandName != null && commandName.equalsIgnoreCase("help")) {
				selectedCommand = this;
			}
			if (selectedCommand == null) {
				addMessage(MESSAGES.pluginName(description.getName(), description.getVersion()));
				addMessage(MESSAGES.pluginDescription(description.getDescription()));
				addMessage(MESSAGES.helpCommandUsage(prefix, getName(), this.commandName.getUsage()));
				for (name.richardson.james.bukkit.utilities.command.Command command : commands.values()) {
					CommandSender commandSender = getContext().getCommandSender();
					if (!command.isAuthorised(commandSender)) continue;
					addMessage(MESSAGES.helpCommandListItem(prefix, command.getName(), command.getUsage()));
				}
			} else {
				addMessage(MESSAGES.helpCommandExtendedDescription(selectedCommand.getDescription()));
				addMessage(MESSAGES.helpCommandListItem(prefix, selectedCommand.getName(), selectedCommand.getUsage()));
				for (String message : selectedCommand.getExtendedUsage()) {
					addMessage(message);
				}
			}
		}

		private Suggester createSuggester(Iterable<name.richardson.james.bukkit.utilities.command.Command> commands) {
			Set<String> names = new HashSet<String>();
			for (name.richardson.james.bukkit.utilities.command.Command command : commands) {
				names.add(command.getName());
			}
			return new StringSuggester(names);
		}

	}
}
