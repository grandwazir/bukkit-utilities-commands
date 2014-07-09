package name.richardson.james.bukkit.utilities.command;

import java.util.*;

import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class AbstractSynchronousCommand extends AbstractCommand {

	private Queue<CommandContext> contexts = new LinkedList<CommandContext>();

	public AbstractSynchronousCommand(Plugin plugin, BukkitScheduler scheduler) {
		super(plugin, scheduler);
	}

	@Override public Map<String, Boolean> getPermissionMap(final Permissible permissible) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		if (getAnnotation() != null) {
			for (String permission : getAnnotation().permissions()) {
				boolean result = permissible.hasPermission(permission);
				map.put(permission, result);
			}
		}
		return map;
	}

	@Override public void schedule(final CommandContext context) {
		contexts.add(context);
		this.getScheduler().runTask(getPlugin(), this);
	}

	@Override protected CommandContext getContext() {
		return contexts.remove();
	}

}
