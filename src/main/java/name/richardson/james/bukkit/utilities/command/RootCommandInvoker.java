package name.richardson.james.bukkit.utilities.command;

import java.util.*;

import name.richardson.james.bukkit.utilities.command.argument.*;
import name.richardson.james.bukkit.utilities.command.argument.suggester.StringSuggester;
import name.richardson.james.bukkit.utilities.command.argument.suggester.Suggester;
import name.richardson.james.bukkit.utilities.command.localisation.Messages;
import name.richardson.james.bukkit.utilities.command.localisation.MessagesFactory;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitScheduler;

public class RootCommandInvoker extends AbstractCommandInvoker {

	private static final Messages MESSAGES = MessagesFactory.getColouredMessages();
	private final PluginDescriptionFile description;
	private final Command helpCommand;
	private final String prefix;

	public RootCommandInvoker(Plugin plugin, BukkitScheduler scheduler, Collection<Command> commands, String prefix) {
		super(plugin, scheduler);
		this.prefix = prefix;
		description = getPlugin().getDescription();
		addCommands(commands);
		helpCommand = new HelpCommand();
	}

	@Override
	public final boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		Command command = (getCommand(args) == null) ? helpCommand : getCommand(args);
		CommandContext context = SimpleCommandContext.create(args, sender, command.getPermissions(), 1);
		command.schedule(context);
		return true;
	}

	@Override
	public final List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String alias, String[] args) {
		Command command = (getCommand(args) == null) ? helpCommand : getCommand(args);
		CommandContext context = SimpleCommandContext.create(args, sender, command.getPermissions(), 1);
		Set<String> suggestions = command.getSuggestions(context.getArguments());
		return new ArrayList<>(suggestions);
	}

	protected final Command getHelpCommand() {
		return helpCommand;
	}

	private class HelpCommand extends AbstractSynchronousCommand {

		private final Argument commandName;

		public HelpCommand() {
			super(RootCommandInvoker.this.getPlugin(), RootCommandInvoker.this.getScheduler());
			ArgumentMetadata metadata = new SimpleArgumentMetadata(MESSAGES.helpCommandArgumentId(), MESSAGES.helpCommandArgumentName(), MESSAGES.helpCommandArgumentDesc());
			Suggester suggester = createSuggester(getCommands().values());
			commandName = new PositionalArgument(metadata, suggester, 0);
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
			Command selectedCommand = getCommand();
			if (selectedCommand != null) {
				addMessage(MESSAGES.helpCommandExtendedDescription(selectedCommand.getDescription()));
				addMessage(MESSAGES.helpCommandListItem(prefix, selectedCommand.getName(), selectedCommand.getUsage()));
				for (String message : selectedCommand.getExtendedUsage()) {
					addMessage(message);
				}
			} else {
				addMessage(MESSAGES.pluginName(description.getName(), description.getVersion()));
				addMessage(MESSAGES.pluginDescription(description.getDescription()));
				addMessage(MESSAGES.helpCommandUsage(prefix, getName(), commandName.getUsage()));
				for (Command command : getCommands().values()) {
					CommandSender commandSender = getContext().getCommandSender();
					if (!command.isAuthorised(commandSender)) continue;
					addMessage(MESSAGES.helpCommandListItem(prefix, command.getName(), command.getUsage()));
				}
			}
		}

		private Command getCommand() {
			String commandName = this.commandName.getString();
			Command command = (commandName == null) ? null : getCommands().get(commandName);
			command = (command == null && commandName != null && commandName.equalsIgnoreCase(getName())) ? this : command;
			return command;
		}

		private Command getCommand(String commandName) {
			Command selectedCommand = (commandName == null) ? null : getCommands().get(commandName);
			if (commandName.equalsIgnoreCase(getName())) selectedCommand = this;
			return selectedCommand;
		}

		private Suggester createSuggester(Iterable<Command> commands) {
			Set<String> names = new HashSet<String>();
			for (Command command : commands) {
				names.add(command.getName());
			}
			return new StringSuggester(names);
		}

	}
}
