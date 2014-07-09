package name.richardson.james.bukkit.utilities.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class AbstractAsyncronousCommand extends AbstractCommand {

	private Queue<CommandContext> contexts = new ConcurrentLinkedQueue<CommandContext>();

	public AbstractAsyncronousCommand(Plugin plugin, BukkitScheduler scheduler) {
		super(plugin, scheduler);
	}

	@Override public Map<String, Boolean> getPermissionMap(Permissible permissible) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		if (getAnnotation() != null) {
			Callable<Map<String, Boolean>> task = new PermissionTask(permissible, getAnnotation().permissions());
			final Future<Map<String, Boolean>> future = getScheduler().callSyncMethod(null, task);
			try {
				result = future.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return result;
	}


	@Override public void schedule(final CommandContext context) {
		contexts.add(context);
		this.getScheduler().runTaskAsynchronously(getPlugin(), this);
	}

	@Override protected CommandContext getContext() {
		return contexts.remove();
	}

	private static class PermissionTask implements Callable<Map<String, Boolean>> {

		private final Permissible permissible;
		private final String[] permissions;
		private boolean done = false;
		private final Map<String, Boolean> map;

		private PermissionTask(final Permissible permissible, String[] permissions) {
			this.permissible = permissible;
			this.permissions = permissions;
			map = new HashMap<String, Boolean>(permissions.length);
		}

		@Override public Map<String, Boolean> call()
		throws Exception {
			for (String permission : permissions) {
				boolean result = permissible.hasPermission(permission);
				map.put(permission, result);
			}
			done = true;
			return map;
		}

	}
}
