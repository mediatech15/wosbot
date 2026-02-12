package dev.fidgetcode.bot.almac.repo;

import java.util.List;
import java.util.Map;

import dev.fidgetcode.bot.almac.entity.DailyTask;
import dev.fidgetcode.bot.almac.entity.TpDailyTask;
import dev.fidgetcode.bot.console.enumerable.TpDailyTaskEnum;
import dev.fidgetcode.bot.ot.DTODailyTaskStatus;

public interface IDailyTaskRepository {

	boolean addDailyTask(DailyTask dailyTask);

	boolean saveDailyTask(DailyTask dailyTask);

	boolean deleteDailyTask(DailyTask dailyTask);

	DailyTask getDailyTaskById(Long id);

	List<DailyTask> findByProfileId(Long profileId);

	DailyTask findByProfileIdAndTaskName(Long profileId, TpDailyTaskEnum taskName);

	Map<Integer, DTODailyTaskStatus> findDailyTasksStatusByProfile(Long profileId);

	TpDailyTask findTpDailyTaskById(Integer id);
}
