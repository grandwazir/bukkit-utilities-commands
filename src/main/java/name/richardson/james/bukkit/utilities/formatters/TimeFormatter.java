/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * TimeFormatter.java is part of BukkitUtilities.
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
package name.richardson.james.bukkit.utilities.formatters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeFormatter {

	public final static long SECONDS = 60;
	public final static long MINUTES = 60;
	public final static long HOURS = 24;

	public final static long ONE_SECOND = 1000;
	public final static long ONE_MINUTE = TimeFormatter.ONE_SECOND * 60;
	public final static long ONE_HOUR = TimeFormatter.ONE_MINUTE * 60;
	public final static long ONE_DAY = TimeFormatter.ONE_HOUR * 24;

	/**
	 * converts time (in milliseconds) to human-readable format
	 * "<w> days, <x> hours, <y> minutes and (z) seconds"
	 */
	public static String millisToLongDHMS(long duration) {
		final StringBuffer res = new StringBuffer();
		long temp = 0;
		if (duration >= TimeFormatter.ONE_SECOND) {
			temp = duration / TimeFormatter.ONE_DAY;
			if (temp > 0) {
				duration -= temp * TimeFormatter.ONE_DAY;
				res.append(temp).append(" day").append(temp > 1 ? "s" : "").append(duration >= TimeFormatter.ONE_MINUTE ? ", " : "");
			}

			temp = duration / TimeFormatter.ONE_HOUR;
			if (temp > 0) {
				duration -= temp * TimeFormatter.ONE_HOUR;
				res.append(temp).append(" hour").append(temp > 1 ? "s" : "").append(duration >= TimeFormatter.ONE_MINUTE ? ", " : "");
			}

			temp = duration / TimeFormatter.ONE_MINUTE;
			if (temp > 0) {
				duration -= temp * TimeFormatter.ONE_MINUTE;
				res.append(temp).append(" minute").append(temp > 1 ? "s" : "");
			}

			if (!res.toString().equals("") && (duration >= TimeFormatter.ONE_SECOND)) {
				res.append(" and ");
			}

			temp = duration / TimeFormatter.ONE_SECOND;
			if (temp > 0) {
				res.append(temp).append(" second").append(temp > 1 ? "s" : "");
			}
			return res.toString();
		} else {
			return "0 second";
		}

	}

	public static Long parseTime(final String timeString) {
		long time;

		int weeks = 0;
		int days = 0;
		int hours = 0;
		int minutes = 0;
		int seconds = 0;

		final Pattern p = Pattern.compile("\\d+[a-z]{1}");
		final Matcher m = p.matcher(timeString);
		boolean result = m.find();

		while (result) {
			final String argument = m.group();

			if (argument.endsWith("w")) {
				weeks = Integer.parseInt(argument.substring(0, argument.length() - 1));
			} else
				if (argument.endsWith("d")) {
					days = Integer.parseInt(argument.substring(0, argument.length() - 1));
				} else
					if (argument.endsWith("h")) {
						hours = Integer.parseInt(argument.substring(0, argument.length() - 1));
					} else
						if (argument.endsWith("m")) {
							minutes = Integer.parseInt(argument.substring(0, argument.length() - 1));
						} else
							if (argument.endsWith("s")) {
								seconds = Integer.parseInt(argument.substring(0, argument.length() - 1));
							} else {
								throw new NumberFormatException("Invalid time format");
							}

			result = m.find();
		}

		time = seconds;
		time += minutes * 60;
		time += hours * 3600;
		time += days * 86400;
		time += weeks * 604800;

		// convert to milliseconds
		time = time * 1000;

		return time;

	}

}
