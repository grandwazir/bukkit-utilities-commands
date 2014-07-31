package name.richardson.james.bukkit.utilities.command.localisation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessagesTest {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("bukkit-utilities-commands");

	protected static final String methodNameToBundleKey(String name) {
		String key = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
		key = key.replaceAll("_", ".");
		return key;
	}

	@Test public void checkAllMessagesHaveBeenTranslated() throws Exception {
		Collection<String> untranslatedKeys = new ArrayList<>();
		Iterable<Method> interfaceMethods = new HashSet<>(Arrays.asList(Messages.class.getMethods()));
		for (Method method : interfaceMethods) {
			String key = methodNameToBundleKey(method.getName());
			if (!RESOURCE_BUNDLE.containsKey(key)) untranslatedKeys.add(key);
		}
		if (!untranslatedKeys.isEmpty()) {
			String keys = Joiner.on(", ").skipNulls().join(untranslatedKeys);
			Assert.fail("Not all messages have been translated! The following keys need to be added: " + keys + ".");
		}
	}

}
