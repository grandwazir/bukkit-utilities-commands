/*******************************************************************************
 Copyright (c) 2014 James Richardson.

 SimpleArgumentInvoker.java is part of BukkitUtilities.

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;

public class SimpleArgumentInvoker implements ArgumentInvoker {

	private final Collection<Argument> arguments = new ArrayList<Argument>();

	@Override
	public void addArgument(Argument argument) {
		Validate.notNull(argument);
		arguments.add(argument);
	}

	@Override
	public void parseArguments(String arguments) throws InvalidArgumentException {
		for (Argument argument : this.arguments) {
			argument.parseValue(arguments);
		}
	}

	@Override
	public Set<String> suggestArguments(String arguments) {
		Set<String> suggestions = new HashSet<String>();
		for (Argument argument : this.arguments) {
			if (argument.isLastArgument(arguments)) {
				suggestions.addAll(argument.suggestValue(arguments));
				break;
			}
		}
		return suggestions;
	}

	@Override
	public void removeArgument(Argument argument) {
		Validate.notNull(argument);
		arguments.remove(argument);
	}

	@Override
	public Collection<String> getExtendedUsage() {
		Collection<String> messages = new ArrayList<String>();
		for (Argument argument : this.arguments) {
			StringBuilder builder = new StringBuilder();
			builder.append(ChatColor.YELLOW);
			builder.append("- ");
			builder.append(argument.getName());
			builder.append(" ");
			builder.append(ChatColor.GREEN);
			builder.append("(");
			builder.append(argument.getDescription());
			builder.append(")");
			messages.add(builder.toString());
		}
		return messages;
	}

	@Override
	public String getUsage() {
		StringBuilder builder = new StringBuilder();
		for (Argument argument : this.arguments) {
			builder.append(argument.getUsage());
			builder.append(" ");
		}
		return builder.toString().trim();
	}
}