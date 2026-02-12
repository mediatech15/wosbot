package dev.fidgetcode.bot.almac.repo;

import java.util.List;

import dev.fidgetcode.bot.almac.entity.Config;
import dev.fidgetcode.bot.almac.entity.Profile;
import dev.fidgetcode.bot.ot.DTOProfiles;

public interface IProfileRepository {

	List<DTOProfiles> getProfiles();

	/**
	 * Gets a profile by its ID including its associated configuration.
	 * @param id profile identifier
	 * @return DTOProfiles with its list of configurations, or null if it does not exist
	 */
	DTOProfiles getProfileWithConfigsById(Long id);

	boolean addProfile(Profile profile);

	boolean saveProfile(Profile profile);

	boolean deleteProfile(Profile profile);

	Profile getProfileById(Long id);

	List<Config> getProfileConfigs(Long profileId);

	boolean deleteConfigs(List<Config> configs);

	boolean saveConfigs(List<Config> configs);
}
