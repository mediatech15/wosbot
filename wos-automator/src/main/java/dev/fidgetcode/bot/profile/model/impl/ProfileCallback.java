package dev.fidgetcode.bot.profile.model.impl;

import java.util.List;

import dev.fidgetcode.bot.ot.DTOProfiles;

@FunctionalInterface
public interface ProfileCallback {
	void onProfilesLoaded(List<DTOProfiles> profiles);
}