/*
 * Copyright (c) 2014 James Richardson.
 *
 * SimpleArgumentInvokerTest.java is part of BukkitUtilities.
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SimpleArgumentInvokerTest {

	private Argument firstArgument;
	private Argument lastArgument;
	private ArgumentInvoker invoker;

	@Test
	public void shouldAddRemoveAndPassthroughToParseArgumentsCorrectly() {
		invoker.addArgument(firstArgument);
		invoker.addArgument(lastArgument);
		invoker.removeArgument(lastArgument);
		invoker.parseArguments("");
		verify(firstArgument).parseValue("");
		verify(lastArgument, never()).parseValue("");
	}

	@Test
	public void shouldAddRemoveAndPassthroughToSuggestArgumentsCorrectly() {
		invoker.addArgument(firstArgument);
		invoker.addArgument(lastArgument);
		Set<String> values = new HashSet<String>();
		values.addAll(Arrays.asList("one", "two", "three"));
		when(lastArgument.suggestValue("frank")).thenReturn(values);
		when(lastArgument.isLastArgument(anyString())).thenReturn(true);
		assertTrue("Returned collection should contain suggested arguments.", invoker.suggestArguments("frank").containsAll(values));
		verify(lastArgument).suggestValue("frank");
		verify(firstArgument, never()).suggestValue("");
	}



	@Before
	public void setup() {
		firstArgument = mock(Argument.class);
		lastArgument = mock(Argument.class);
		invoker = new SimpleArgumentInvoker();
	}



}
