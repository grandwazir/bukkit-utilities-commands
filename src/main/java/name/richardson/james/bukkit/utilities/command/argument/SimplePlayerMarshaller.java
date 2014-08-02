/*******************************************************************************
 Copyright (c) 2014 James Richardson.

 SimplePlayerMarshaller.java is part of BukkitUtilities.

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

package name.richardson.james.bukkit.utilities.command.argument;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Server;
import org.bukkit.entity.Player;

public class SimplePlayerMarshaller extends AbstractMarshaller implements PlayerMarshaller {

	private final Server server;

	public SimplePlayerMarshaller(Argument argument, Server server) {
		super(argument);
		this.server = server;
	}

	@Override
	public Player getPlayer() {
		Player player = null;
		if (getString() != null) {
			player = server.getPlayerExact(getString());
		}
		return player;
	}

	@Override
	public Set<Player> getPlayers() {
		Set<Player> players = new HashSet<Player>();
		for (String playerName : getStrings()) {
			Player player = server.getPlayerExact(playerName);
			if (player != null)
				players.add(player);
		}
		return players;
	}
}