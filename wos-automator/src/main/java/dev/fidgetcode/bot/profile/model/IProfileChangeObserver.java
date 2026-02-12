package dev.fidgetcode.bot.profile.model;

import dev.fidgetcode.bot.console.enumerable.EnumConfigurationKey;

public interface IProfileChangeObserver {

	public void notifyProfileChange(EnumConfigurationKey key, Object value);

}
