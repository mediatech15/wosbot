package dev.fidgetcode.bot.console.controller;

import dev.fidgetcode.bot.console.list.ILogListener;
import dev.fidgetcode.bot.console.view.ConsoleLogLayoutController;
import dev.fidgetcode.bot.ot.DTOLogMessage;
import dev.fidgetcode.bot.serv.impl.ServLogs;

public class ConsoleLogActionController implements ILogListener {

	private ConsoleLogLayoutController layoutController;

	public ConsoleLogActionController(ConsoleLogLayoutController controller) {
		this.layoutController = controller;
		ServLogs.getServices().setLogListener(this);
	}

	@Override
	public void onLogReceived(DTOLogMessage message) {
		layoutController.appendMessage(message);
	}

}
