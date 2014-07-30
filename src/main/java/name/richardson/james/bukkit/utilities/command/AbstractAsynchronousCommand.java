package name.richardson.james.bukkit.utilities.command;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class AbstractAsynchronousCommand extends AbstractCommand {

	private final Queue<CommandContext> contexts;

	protected AbstractAsynchronousCommand(Plugin plugin, BukkitScheduler scheduler) {
		super(plugin, scheduler);
		contexts = new ConcurrentLinkedQueue<>();
	}

	@Override
	public final void schedule(CommandContext context) {
		contexts.add(context);
		getScheduler().runTaskAsynchronously(getPlugin(), this);
	}

	@Override
	protected final CommandContext getNextScheduledContext() {
		return contexts.remove();
	}

}
