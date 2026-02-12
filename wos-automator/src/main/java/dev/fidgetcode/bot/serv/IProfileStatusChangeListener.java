package dev.fidgetcode.bot.serv;

import dev.fidgetcode.bot.ot.DTOProfileStatus;

public interface IProfileStatusChangeListener {

	public void onProfileStatusChange(DTOProfileStatus status);
}
