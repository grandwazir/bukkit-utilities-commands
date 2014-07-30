package name.richardson.james.bukkit.utilities.command;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class AbstractSynchronousCommand extends AbstractCommand {

	private final Queue<CommandContext> contexts;

	protected AbstractSynchronousCommand(Plugin plugin, BukkitScheduler scheduler) {
		super(plugin, scheduler);
		contexts = new LinkedList<>();
	}

	@Override
	public final void schedule(CommandContext context) {
		contexts.add(context);
		getScheduler().runTask(getPlugin(), this);
	}

	@Override
	protected final CommandContext getNextScheduledContext() {
		return contexts.remove();
	}

}

