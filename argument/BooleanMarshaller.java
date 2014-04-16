/*
 * Copyright (c) 2014 James Richardson.
 *
 * BooleanMarshaller.java is part of BukkitUtilities.
 *
 * bukkit-utilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * bukkit-utilities is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * bukkit-utilities. If not, see <http://www.gnu.org/licenses/>.
 */

package name.richardson.james.bukkit.utilities.command.argument;

import name.richardson.james.bukkit.utilities.command.argument.AbstractMarshaller;
import name.richardson.james.bukkit.utilities.command.argument.Argument;

@SuppressWarnings("PublicMethodNotExposedInInterface")
public class BooleanMarshaller extends AbstractMarshaller {

	private final boolean defaultValue;

	@SuppressWarnings("BooleanParameter")
	public BooleanMarshaller(final Argument argument, boolean defaultValue) {
		super(argument);
		this.defaultValue = defaultValue;
	}

	public boolean isSet() {
		Boolean value = null;
		if (getString() == null) {
			value = defaultValue;
		} else {
			value = Boolean.valueOf(getString());
		}
		return value;
	}



}