package dev.fidgetcode.bot.taskmanager.model;

import java.util.List;

import dev.fidgetcode.bot.ot.DTODailyTaskStatus;
import dev.fidgetcode.bot.taskmanager.ITaskStatusChangeListener;

public interface ITaskStatusModel {

	public List<DTODailyTaskStatus> getDailyTaskStatusList(Long profileId);

	public void addTaskStatusChangeListener(ITaskStatusChangeListener taskManagerActionController);

}
