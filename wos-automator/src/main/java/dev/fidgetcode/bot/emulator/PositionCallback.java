package dev.fidgetcode.bot.emulator;

@FunctionalInterface
public interface PositionCallback {
	void onPositionUpdate(Thread thread, int position);
}