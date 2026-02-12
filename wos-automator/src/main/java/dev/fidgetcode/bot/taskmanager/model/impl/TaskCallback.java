package dev.fidgetcode.bot.taskmanager.model.impl;

import java.util.List;

import dev.fidgetcode.bot.ot.DTODailyTaskStatus;

@FunctionalInterface
public interface TaskCallback {

	void onTasksLoaded(List<DTODailyTaskStatus> profiles);

}
