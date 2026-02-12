package dev.fidgetcode.bot.serv;

import dev.fidgetcode.bot.ot.DTOBotState;

public interface IBotStateListener {

	public void onBotStateChange(DTOBotState botState);

}
