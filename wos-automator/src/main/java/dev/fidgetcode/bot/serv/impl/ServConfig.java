package dev.fidgetcode.bot.serv.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.fidgetcode.bot.almac.entity.Config;
import dev.fidgetcode.bot.almac.entity.Profile;
import dev.fidgetcode.bot.almac.entity.TpConfig;
import dev.fidgetcode.bot.almac.repo.ConfigRepository;
import dev.fidgetcode.bot.almac.repo.IConfigRepository;
import dev.fidgetcode.bot.almac.repo.IProfileRepository;
import dev.fidgetcode.bot.almac.repo.ProfileRepository;
import dev.fidgetcode.bot.console.enumerable.EnumConfigurationKey;
import dev.fidgetcode.bot.console.enumerable.TpConfigEnum;
import dev.fidgetcode.bot.ot.DTOProfiles;

public class ServConfig {

	private static ServConfig instance;
	private static final Logger logger = LoggerFactory.getLogger(ServConfig.class);

	private IConfigRepository iConfigRepository = ConfigRepository.getRepository();
	private IProfileRepository iProfileRepository = ProfileRepository.getRepository();

	private ServConfig() {

	}

	public static ServConfig getServices() {
		if (instance == null) {
			instance = new ServConfig();
		}
		return instance;
	}

	public HashMap<String, String> getGlobalConfig() {
		List<Config> configs = iConfigRepository.getGlobalConfigs();

		if (configs == null || configs.isEmpty()) {
			return null;
		}

		HashMap<String, String> globalConfig = new HashMap<>();
		for (Config config : configs) {
			globalConfig.put(config.getKey(), config.getValue());
		}
		return globalConfig;
	}

	/**
	 * Updates a specific profile configuration value both in memory and in the database
	 * If the configuration doesn't exist, it will be created automatically
	 *
	 * @param profile The profile to update
	 * @param key The configuration key to update
	 * @param value The new value to set
	 * @return true if the update was successful, false otherwise
	 */
	public boolean updateProfileConfig(DTOProfiles profile, EnumConfigurationKey key, String value) {
		try {
			// Update the profile configuration in memory
			profile.setConfig(key, value);

			// Persist to database - get all configs for this profile and find the specific one
			List<Config> profileConfigs = iConfigRepository.getProfileConfigs(profile.getId());
			Config config = profileConfigs.stream()
					.filter(c -> c.getKey().equalsIgnoreCase(key.name()))
					.findFirst()
					.orElse(null);

			if (config != null) {
				// Configuration exists, update it
				config.setValue(value);
				boolean saved = iConfigRepository.saveConfig(config);

				if (saved) {
					logger.info("Configuration {} updated to: {}", key.name(), value);
					// Notify UI that profile data has changed
					ServProfiles.getServices().notifyProfileDataChange(profile);
				} else {
					logger.warn("Failed to persist configuration {}", key.name());
				}

				return saved;
			} else {
				// Configuration doesn't exist, create it
				logger.info("Configuration {} not found, creating new entry", key.name());

				// Get the TpConfig for profile configurations
				TpConfig tpConfig = iConfigRepository.getTpConfig(TpConfigEnum.PROFILE_CONFIG);
				if (tpConfig == null) {
					logger.error("Could not find PROFILE_CONFIG type in database");
					return false;
				}

				// Get the Profile entity from the database
				Profile profileEntity = iProfileRepository.getProfileById(profile.getId());
				if (profileEntity == null) {
					logger.error("Could not find profile with ID {} in database", profile.getId());
					return false;
				}

				// Create new configuration
				Config newConfig = new Config(profileEntity, tpConfig, key.name(), value);
				boolean created = iConfigRepository.addConfig(newConfig);

				if (created) {
					logger.info("Configuration {} created with value: {}", key.name(), value);
					// Notify UI that profile data has changed
					ServProfiles.getServices().notifyProfileDataChange(profile);
				} else {
					logger.warn("Failed to create configuration {}", key.name());
				}

				return created;
			}

		} catch (Exception e) {
			logger.error("Error updating configuration {}: {}", key.name(), e.getMessage(), e);
			return false;
		}
	}

}
