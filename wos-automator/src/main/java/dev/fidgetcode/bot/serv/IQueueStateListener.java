package dev.fidgetcode.bot.serv;

import dev.fidgetcode.bot.ot.DTOQueueState;

public interface IQueueStateListener {

	public void onQueueStateChange(DTOQueueState queueState);

}
