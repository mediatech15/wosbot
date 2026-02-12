package dev.fidgetcode.bot.serv;

import dev.fidgetcode.bot.ot.DTOProfiles;

public interface IProfileDataChangeListener {
    void onProfileDataChanged(DTOProfiles profile);
}
