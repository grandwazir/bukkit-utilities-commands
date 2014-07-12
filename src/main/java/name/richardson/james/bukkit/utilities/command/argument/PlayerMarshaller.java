package name.richardson.james.bukkit.utilities.command.argument;

import java.util.Set;

import org.bukkit.entity.Player;

public interface PlayerMarshaller {

	Player getPlayer();
	Set<Player> getPlayers();

}
