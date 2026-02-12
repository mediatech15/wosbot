package dev.fidgetcode.bot.serv.impl;

import dev.fidgetcode.bot.console.enumerable.EnumTpMessageSeverity;
import dev.fidgetcode.bot.console.list.ILogListener;
import dev.fidgetcode.bot.ot.DTOLogMessage;

public class ServLogs {

	private static ServLogs instance;

	private ILogListener iLogListener;

	private ServLogs() {

	}

	public static ServLogs getServices() {
		if (instance == null) {
			instance = new ServLogs();
		}
		return instance;
	}

	public void setLogListener(ILogListener listener) {
		this.iLogListener = listener;
	}

	public void appendLog(EnumTpMessageSeverity severity, String task, String profile, String message) {

		DTOLogMessage logMessage = new DTOLogMessage(severity, message, task, profile);
//		ServDiscord.getServices().sendLog(logMessage);

		if (iLogListener != null) {
			iLogListener.onLogReceived(logMessage);
		}
	}
}
