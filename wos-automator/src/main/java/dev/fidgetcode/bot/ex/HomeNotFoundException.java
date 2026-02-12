package dev.fidgetcode.bot.ex;

public class HomeNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5590610329283682736L;

	public HomeNotFoundException(String message) {
		super(message);
	}
}
