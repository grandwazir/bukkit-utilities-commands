package name.richardson.james.bukkit.utilities.command.localisation;

import com.vityuk.ginger.Localization;
import com.vityuk.ginger.LocalizationBuilder;

@SuppressWarnings({"FinalClass", "UtilityClass"})
public final class Localisation {

	private static final String RESOURCE_LOCATION = "classpath:localisation/bukkit-utilities-core.properties";
	private static final Localization LOCALIZATION = new LocalizationBuilder().withResourceLocation(RESOURCE_LOCATION).build();
	private static final LocalisedMessages LOCALISED_MESSAGES = LOCALIZATION.getLocalizable(LocalisedMessages.class);

	private Localisation() {}

	public static LocalisedMessages getLocalisedMessages() {
		return LOCALISED_MESSAGES;
	}

}
