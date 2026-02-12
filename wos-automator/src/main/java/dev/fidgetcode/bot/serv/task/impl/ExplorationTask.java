package dev.fidgetcode.bot.serv.task.impl;

import java.time.LocalDateTime;

import dev.fidgetcode.bot.console.enumerable.EnumConfigurationKey;
import dev.fidgetcode.bot.console.enumerable.EnumTemplates;
import dev.fidgetcode.bot.console.enumerable.TpDailyTaskEnum;
import dev.fidgetcode.bot.ot.DTOImageSearchResult;
import dev.fidgetcode.bot.ot.DTOPoint;
import dev.fidgetcode.bot.ot.DTOProfiles;
import dev.fidgetcode.bot.serv.task.DelayedTask;
import dev.fidgetcode.bot.serv.task.constants.SearchConfigConstants;

public class ExplorationTask extends DelayedTask {

	public ExplorationTask(DTOProfiles profile, TpDailyTaskEnum tpDailyTask) {
		super(profile, tpDailyTask);
	}

	@Override
	protected void execute() {
		logInfo("Starting exploration task.");
		tapRandomPoint(new DTOPoint(40, 1190), new DTOPoint(100, 1250));
		sleepTask(500);
		DTOImageSearchResult claimResult = templateSearchHelper.searchTemplate(
				EnumTemplates.EXPLORATION_CLAIM, SearchConfigConstants.DEFAULT_SINGLE);
		if (claimResult.isFound()) {
			logInfo("Claiming exploration rewards...");
			tapRandomPoint(new DTOPoint(560, 900), new DTOPoint(670, 940));
			sleepTask(500);
			tapRandomPoint(new DTOPoint(230, 890), new DTOPoint(490, 960));
			sleepTask(500);

			tapRandomPoint(new DTOPoint(230, 890), new DTOPoint(490, 960));
			sleepTask(200);
			tapRandomPoint(new DTOPoint(230, 890), new DTOPoint(490, 960));
			sleepTask(200);
			tapRandomPoint(new DTOPoint(230, 890), new DTOPoint(490, 960));
			sleepTask(200);

			Integer minutes = profile.getConfig(EnumConfigurationKey.INT_EXPLORATION_CHEST_OFFSET, Integer.class);
			LocalDateTime nextSchedule = LocalDateTime.now().plusMinutes(minutes);
			this.reschedule(nextSchedule);
			logInfo("Exploration task completed. Next execution scheduled in " + minutes + " minutes.");

		} else {
			logInfo("No exploration rewards to claim.");
			Integer minutes = profile.getConfig(EnumConfigurationKey.INT_EXPLORATION_CHEST_OFFSET, Integer.class);
			LocalDateTime nextSchedule = LocalDateTime.now().plusMinutes(minutes);
			this.reschedule(nextSchedule);
			logInfo("Exploration task completed. Next execution scheduled in " + minutes + " minutes.");

		}
		tapBackButton();
		sleepTask(500);
	}

}
