package dev.fidgetcode.bot.almac.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dev.fidgetcode.bot.almac.entity.DailyTask;
import dev.fidgetcode.bot.almac.entity.TpDailyTask;
import dev.fidgetcode.bot.almac.jpa.BotPersistence;
import dev.fidgetcode.bot.console.enumerable.TpDailyTaskEnum;
import dev.fidgetcode.bot.ot.DTODailyTaskStatus;

public class DailyTaskRepository implements IDailyTaskRepository {
	private final BotPersistence persistence = BotPersistence.getInstance();

	private static DailyTaskRepository instance;

	private DailyTaskRepository() {
	}

	public static DailyTaskRepository getRepository() {
		if (instance == null) {
			instance = new DailyTaskRepository();
		}
		return instance;
	}

	@Override
	public boolean addDailyTask(DailyTask dailyTask) {
		return persistence.createEntity(dailyTask);
	}

	@Override
	public boolean saveDailyTask(DailyTask dailyTask) {
		return persistence.updateEntity(dailyTask);
	}

	@Override
	public boolean deleteDailyTask(DailyTask dailyTask) {
		return persistence.deleteEntity(dailyTask);
	}

	@Override
	public DailyTask getDailyTaskById(Long id) {
		return persistence.findEntityById(DailyTask.class, id);
	}

	@Override
	public List<DailyTask> findByProfileId(Long profileId) {
		String query = "SELECT d FROM DailyTask d WHERE d.profile.id = :profileId";

		// Crear el mapa de parámetros
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("profileId", profileId);

		return persistence.getQueryResults(query, DailyTask.class, parameters);
	}

	@Override
	public DailyTask findByProfileIdAndTaskName(Long profileId, TpDailyTaskEnum taskName) {
		String query = """
				SELECT d FROM DailyTask d
				WHERE d.profile.id = :profileId AND d.task.id = :id""";

		// Crear el mapa de parámetros
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("profileId", profileId);
		parameters.put("id", taskName.getId());

		List<DailyTask> results = persistence.getQueryResults(query, DailyTask.class, parameters);

		return results.isEmpty() ? null : results.get(0);
	}

	@Override
	public Map<Integer, DTODailyTaskStatus> findDailyTasksStatusByProfile(Long profileId) {
		String query = """
				SELECT new dev.fidgetcode.bot.ot.DTODailyTaskStatus(
				d.profile.id, d.task.id, d.lastExecution, d.nextSchedule)
				FROM DailyTask d
				WHERE d.profile.id = :profileId""";

		// Crear el mapa de parámetros
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("profileId", profileId);

		List<DTODailyTaskStatus> results = persistence.getQueryResults(query, DTODailyTaskStatus.class, parameters);

		return results.stream().collect(Collectors.toMap(DTODailyTaskStatus::getIdTpDailyTask, dto -> dto));
	}

	@Override
	public TpDailyTask findTpDailyTaskById(Integer id) {
		return persistence.findEntityById(TpDailyTask.class, id);
	}
}
