/*******************************************************************************
 Copyright (c) 2014 James Richardson.

 StringSuggester.java is part of BukkitUtilities.

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
package name.richardson.james.bukkit.utilities.command.argument.suggester;

import java.util.*;

public final class StringSuggester implements Suggester {

	private Collection<String> strings = new HashSet<String>();

	public StringSuggester(Collection<String> strings) {
		this.strings.addAll(strings);
	}

	@Override
	public Set<String> suggestValue(String argument) {
		TreeSet<String> results = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		argument = argument.toLowerCase(Locale.ENGLISH);
		for (String string : this.strings) {
			if (results.size() == Suggester.MAX_MATCHES)
				break;
			if (!string.toLowerCase(Locale.ENGLISH).startsWith(argument))
				continue;
			results.add(string);
		}
		return results;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("StringSuggester{");
		sb.append("strings=").append(strings);
		sb.append('}');
		return sb.toString();
	}
}
