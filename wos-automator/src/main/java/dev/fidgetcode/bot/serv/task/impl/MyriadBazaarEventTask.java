package dev.fidgetcode.bot.serv.task.impl;

import dev.fidgetcode.bot.utilities.UtilTime;
import dev.fidgetcode.bot.console.enumerable.TpDailyTaskEnum;
import dev.fidgetcode.bot.ot.DTOImageSearchResult;
import dev.fidgetcode.bot.ot.DTOPoint;
import dev.fidgetcode.bot.ot.DTOProfiles;
import dev.fidgetcode.bot.serv.task.DelayedTask;
import dev.fidgetcode.bot.serv.task.EnumStartLocation;
import dev.fidgetcode.bot.serv.task.constants.SearchConfigConstants;
import dev.fidgetcode.bot.serv.task.helper.TemplateSearchHelper;

import static dev.fidgetcode.bot.console.enumerable.EnumTemplates.DAILY_MISSION_CLAIM_BUTTON;
import static dev.fidgetcode.bot.console.enumerable.EnumTemplates.EVENTS_MYRIAD_BAZAAR_ICON;

/**
 * Task implementation for claiming free rewards on Myriad Bazaar event.
 * This task handles the automation of claiming rewards from the Myriad Bazaar
 * event.
 */
public class MyriadBazaarEventTask extends DelayedTask {

    public MyriadBazaarEventTask(DTOProfiles profile, TpDailyTaskEnum tpDailyTask) {
        super(profile, tpDailyTask);
    }

    @Override
    public EnumStartLocation getRequiredStartLocation() {
        return EnumStartLocation.HOME;
    }

    @Override
    protected void execute() {

        // search the myriad bazaar event icon and click it
        DTOImageSearchResult bazaarIcon = templateSearchHelper.searchTemplate(
                EVENTS_MYRIAD_BAZAAR_ICON, SearchConfigConstants.DEFAULT_SINGLE);

        if (!bazaarIcon.isFound()) {
            logInfo("Myriad Bazaar event probably not active");
            reschedule(UtilTime.getGameReset());
            return;
        }
        logInfo("Myriad Bazaar is active, claiming free rewards");
        // wait for the event window to open
        tapPoint(bazaarIcon.getPoint());
        sleepTask(2000);

        // define area to search for free rewards
        DTOPoint topLeft = new DTOPoint(50, 280);
        DTOPoint bottomRight = new DTOPoint(650, 580);

        // claim all the rewards available using a while loop until no more rewards are
        // availableD
        int failCount = 0;
        DTOImageSearchResult freeReward = templateSearchHelper.searchTemplate(DAILY_MISSION_CLAIM_BUTTON,
                TemplateSearchHelper.SearchConfig.builder()
                        .withMaxAttempts(1)
                        .withThreshold(90)
                        .withDelay(300L)
                        .withCoordinates(topLeft, bottomRight)
                        .build());
        while (true) {
            if (freeReward != null && freeReward.isFound()) {
                logInfo("Claiming free rewards");
                tapPoint(freeReward.getPoint());
                sleepTask(1000);
                failCount = 0;
            } else {
                failCount++;
                if (failCount >= 3) {
                    logInfo("No rewards found after 3 consecutive attempts, exiting loop");
                    break;
                }
                sleepTask(500);
            }
            freeReward = templateSearchHelper.searchTemplate(DAILY_MISSION_CLAIM_BUTTON,
                    TemplateSearchHelper.SearchConfig.builder()
                            .withMaxAttempts(1)
                            .withThreshold(90)
                            .withDelay(300L)
                            .withCoordinates(topLeft, bottomRight)
                            .build());
        }
        logInfo("Finished claiming Myriad Bazaar free rewards");
        reschedule(UtilTime.getGameReset());

    }

}
