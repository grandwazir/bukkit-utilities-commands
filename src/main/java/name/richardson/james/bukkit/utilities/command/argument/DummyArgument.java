/*******************************************************************************
 Copyright (c) 2014 James Richardson.

 DummyArgument.java is part of BukkitUtilities.

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

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class DummyArgument implements Argument {

	@Override
	public final String getId() {
		return "";
	}

	@Override
	public final String getName() {
		return "";
	}

	@Override
	public final String getError() {
		return "";
	}

	@Override
	public final String getDescription() {
		return "";
	}

	@Override
	public final String getString() {
		return "";
	}

	@Override
	public final Collection<String> getStrings() {
		return Collections.emptySet();
	}

	@Override
	public final boolean isLastArgument(String arguments) {
		return false;
	}

	@Override
	public final String getUsage() {
		return "";
	}

	@Override
	public final void parseValue(String argument) throws InvalidArgumentException {

	}

	@Override
	public final Set<String> suggestValue(String argument) {
		return Collections.emptySet();
	}
}
