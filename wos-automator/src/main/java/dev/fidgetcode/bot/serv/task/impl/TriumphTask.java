package dev.fidgetcode.bot.serv.task.impl;

import java.time.LocalDateTime;

import dev.fidgetcode.bot.utilities.UtilTime;
import dev.fidgetcode.bot.console.enumerable.EnumConfigurationKey;
import dev.fidgetcode.bot.console.enumerable.EnumTemplates;
import dev.fidgetcode.bot.console.enumerable.TpDailyTaskEnum;
import dev.fidgetcode.bot.ot.DTOImageSearchResult;
import dev.fidgetcode.bot.ot.DTOPoint;
import dev.fidgetcode.bot.ot.DTOProfiles;
import dev.fidgetcode.bot.serv.task.DelayedTask;
import dev.fidgetcode.bot.serv.task.EnumStartLocation;
import dev.fidgetcode.bot.serv.task.constants.SearchConfigConstants;

/**
 * Task responsible for claiming daily and weekly alliance triumph rewards.
 * The task checks if rewards are available and claims them accordingly.
 * If rewards are not available, it reschedules itself based on configuration.
 */
public class TriumphTask extends DelayedTask {

	public TriumphTask(DTOProfiles profile, TpDailyTaskEnum dailyMission) {
		super(profile, dailyMission);
	}

	@Override
	public EnumStartLocation getRequiredStartLocation() {
		return EnumStartLocation.HOME;
	}

	@Override
	protected void execute() {
		logInfo("Starting Alliance Triumph task to claim rewards");

		// Navigate to alliance menu
		logInfo("Tapping alliance button at bottom of screen");
		tapRandomPoint(new DTOPoint(493, 1187), new DTOPoint(561, 1240));
		sleepTask(3000);

		// Search for the Triumph button
		DTOImageSearchResult result = templateSearchHelper.searchTemplate(
				EnumTemplates.ALLIANCE_TRIUMPH_BUTTON, SearchConfigConstants.DEFAULT_SINGLE);
		if (result.isFound()) {
			logInfo("Alliance Triumph button found. Tapping to open the menu.");
			tapPoint(result.getPoint());
			sleepTask(2000);

			logInfo("Checking daily Triumph rewards status");
			// Check if daily rewards have already been claimed
			result = templateSearchHelper.searchTemplate(
					EnumTemplates.ALLIANCE_TRIUMPH_DAILY_CLAIMED, SearchConfigConstants.DEFAULT_SINGLE);

			if (result.isFound()) {
				logInfo("Daily Triumph rewards already claimed - rescheduling for next game reset");
				this.reschedule(UtilTime.getGameReset());
			} else {
				// Check if daily rewards are ready to claim
				logInfo("Daily rewards not claimed yet, checking if they are available");
				result = templateSearchHelper.searchTemplate(
						EnumTemplates.ALLIANCE_TRIUMPH_DAILY, SearchConfigConstants.DEFAULT_SINGLE);

				if (result.isFound()) {
					logInfo("Daily Triumph rewards are available - claiming now");
					tapRandomPoint(result.getPoint(), result.getPoint(), 10, 50);
					sleepTask(1000); // Add delay after claiming to ensure UI updates
					logInfo("Daily rewards claimed successfully");
					reschedule(UtilTime.getGameReset());
				} else {
					// Rewards not ready yet
					int offset = profile.getConfig(EnumConfigurationKey.ALLIANCE_TRIUMPH_OFFSET_INT, Integer.class);
					LocalDateTime proposedSchedule = LocalDateTime.now().plusMinutes(offset);

					// Check if the proposed schedule would be after game reset and adjust if needed
					LocalDateTime nextSchedule = UtilTime.ensureBeforeGameReset(proposedSchedule);

					if (!nextSchedule.equals(proposedSchedule)) {
						logInfo("Next scheduled time would be after game reset. Adjusting to 5 minutes before reset.");
					}

					logInfo("Daily Triumph rewards not available - rescheduling for " + nextSchedule);
					reschedule(nextSchedule);
				}
			}

			// Check for weekly rewards
			logInfo("Checking weekly Triumph rewards status");
			result = templateSearchHelper.searchTemplate(EnumTemplates.ALLIANCE_TRIUMPH_WEEKLY,
					SearchConfigConstants.DEFAULT_SINGLE);

			if (result.isFound()) {
				logInfo("Weekly Triumph rewards are available - claiming now");
				tapPoint(result.getPoint());
				sleepTask(1500); // Increased delay to ensure reward animation completes
				tapBackButton();
				logInfo("Weekly Triumph claimed successfully");
			} else {
				logInfo("Weekly Triumph rewards not available or already claimed");
			}

			// Return to home screen
			logDebug("Returning to home screen");
			tapBackButton();
			sleepTask(300);
			tapBackButton();
		} else {
			int offset = profile.getConfig(EnumConfigurationKey.ALLIANCE_TRIUMPH_OFFSET_INT, Integer.class);
			logError("Alliance Triumph button not found - unable to claim rewards");

			LocalDateTime proposedSchedule = LocalDateTime.now().plusMinutes(offset);
			LocalDateTime nextSchedule = UtilTime.ensureBeforeGameReset(proposedSchedule);

			if (!nextSchedule.equals(proposedSchedule)) {
				logInfo("Next scheduled time would be after game reset. Adjusting to 5 minutes before reset.");
			}

			logInfo("Rescheduling task for " + nextSchedule);
			tapBackButton();
			sleepTask(500);
			reschedule(nextSchedule);
		}

		logInfo("Alliance Triumph task completed");
	}

}
