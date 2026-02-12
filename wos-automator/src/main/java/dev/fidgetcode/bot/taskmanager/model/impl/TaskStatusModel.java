package dev.fidgetcode.bot.taskmanager.model.impl;

import java.util.List;

import dev.fidgetcode.bot.ot.DTODailyTaskStatus;
import dev.fidgetcode.bot.serv.impl.ServTaskManager;
import dev.fidgetcode.bot.taskmanager.ITaskStatusChangeListener;
import dev.fidgetcode.bot.taskmanager.model.ITaskStatusModel;

public class TaskStatusModel implements ITaskStatusModel {

	private ServTaskManager servTaskManager = ServTaskManager.getInstance();

	@Override
	public List<DTODailyTaskStatus> getDailyTaskStatusList(Long profileId) {
		return servTaskManager.getDailyTaskStatusPersistence(profileId);

	}

	@Override
	public void addTaskStatusChangeListener(ITaskStatusChangeListener taskManagerActionController) {
		ServTaskManager.getInstance().addTaskStatusChangeListener(taskManagerActionController);

	}

}
