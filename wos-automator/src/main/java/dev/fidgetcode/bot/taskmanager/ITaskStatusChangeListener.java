package dev.fidgetcode.bot.taskmanager;

import dev.fidgetcode.bot.ot.DTOTaskState;

public interface ITaskStatusChangeListener {

	public void onTaskStatusChange(Long profileId, int taskNameId, DTOTaskState taskState);

}