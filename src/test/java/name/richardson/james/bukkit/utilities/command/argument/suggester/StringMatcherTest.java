/*******************************************************************************
 Copyright (c) 2014 James Richardson.

 StringMatcherTest.java is part of BukkitUtilities.

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

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class StringMatcherTest extends TestCase {

	private StringSuggester matcher;

	@Test
	public void matches_WhenExecuted_ReturnNoMatches() {
		Set<String> results = matcher.suggestValue("fr");
		assertTrue("A match should not have been returned!", results.size() == 0);
	}

	@Test
	public void matches_WhenExecuted_ReturnMatches() {
		Set<String> results = matcher.suggestValue("kiC");
		assertTrue("A match should have been returned!", results.contains("kick"));
		assertTrue("Two matches should have been returned!", results.size() == 2);
	}

	@Test
	public void checkToStringOverriden() {
		assertTrue("toString has not been overridden", matcher.toString().contains("StringSuggester"));
	}

	@Before
	public void setUp()
	throws Exception {
		List<String> commandNames = Arrays.asList("ban", "kick", "remove", "kicked");
		this.matcher = new StringSuggester(commandNames);
	}

}