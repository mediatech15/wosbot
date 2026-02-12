package dev.fidgetcode.bot.console.list;

import dev.fidgetcode.bot.ot.DTOLogMessage;

public interface ILogListener {

	void onLogReceived(DTOLogMessage message);

}
