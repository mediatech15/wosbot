package dev.fidgetcode.bot.almac.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.fidgetcode.bot.almac.entity.Config;
import dev.fidgetcode.bot.almac.entity.TpConfig;
import dev.fidgetcode.bot.almac.jpa.BotPersistence;
import dev.fidgetcode.bot.console.enumerable.TpConfigEnum;

public class ConfigRepository implements IConfigRepository {
	private final BotPersistence persistence = BotPersistence.getInstance();

	private static ConfigRepository instance;

	public static ConfigRepository getRepository() {
		if (instance == null) {
			instance = new ConfigRepository();
		}
		return instance;
	}

	@Override
	public boolean addConfig(Config config) {
		return persistence.createEntity(config);
	}

	@Override
	public boolean saveConfig(Config config) {
		return persistence.updateEntity(config);
	}

	@Override
	public boolean deleteConfig(Config config) {
		return persistence.deleteEntity(config);
	}

	@Override
	public Config getConfigById(Long id) {
		return persistence.findEntityById(Config.class, id);
	}

	@Override
	public List<Config> getProfileConfigs(Long profileId) {
		String query = "SELECT c FROM Config c WHERE c.profile.id = :profileId";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("profileId", profileId);
		return persistence.getQueryResults(query, Config.class, parameters);
	}

	@Override
	public List<Config> getGlobalConfigs() {
		String query = "SELECT c FROM Config c WHERE c.profile IS NULL";
		return persistence.getQueryResults(query, Config.class, null);
	}

	@Override
	public TpConfig getTpConfig(TpConfigEnum tpConfigEnum) {
		return persistence.findEntityById(TpConfig.class, tpConfigEnum.getId());
	}

}
