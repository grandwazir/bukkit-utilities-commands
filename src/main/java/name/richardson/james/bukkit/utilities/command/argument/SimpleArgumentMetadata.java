/*******************************************************************************
 Copyright (c) 2014 James Richardson.

 SimpleArgumentMetadata.java is part of BukkitUtilities.

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

public final class SimpleArgumentMetadata implements ArgumentMetadata {

	private final String desc;
	private final String id;
	private final String name;
	private String error;

	public SimpleArgumentMetadata(String id, String name, String desc, String error) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.error = error;
	}

	public SimpleArgumentMetadata(String id, String name, String desc) {
		this.id = id;
		this.name = name;
		this.desc = desc;
	}

	@Override
	public String getDescription() {
		return desc;
	}

	@Override
	public String getError() {
		return error;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}
}
