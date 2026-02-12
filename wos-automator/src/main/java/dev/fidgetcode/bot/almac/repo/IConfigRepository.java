
package dev.fidgetcode.bot.almac.repo;

import java.util.List;

import dev.fidgetcode.bot.almac.entity.Config;
import dev.fidgetcode.bot.almac.entity.TpConfig;
import dev.fidgetcode.bot.console.enumerable.TpConfigEnum;

public interface IConfigRepository {

	boolean addConfig(Config config);

	boolean saveConfig(Config config);

	boolean deleteConfig(Config config);

	Config getConfigById(Long id);

	List<Config> getProfileConfigs(Long profileId);

	List<Config> getGlobalConfigs();

	TpConfig getTpConfig(TpConfigEnum tpConfig);
}
