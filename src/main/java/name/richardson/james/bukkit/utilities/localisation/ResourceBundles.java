/*******************************************************************************
 * Copyright (c) 2013 James Richardson
 * 
 * ResourceBundles.java is part of BukkitUtilities.
 * 
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * BukkitUtilities is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.utilities.localisation;

public enum ResourceBundles {

	MESSAGES("messages"), PERMISSIONS("permissions"), UTILITIES("utilities");

	private String bundleName;

	ResourceBundles(final String bundleName) {
		this.bundleName = bundleName;
	}

	public String getBundleName() {
		return this.bundleName;
	}

	@Override
	public String toString() {
		return this.bundleName;
	}

}
