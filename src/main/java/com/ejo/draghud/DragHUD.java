package com.ejo.draghud;

import com.ejo.draghud.gui.GuiManager;
import com.ejo.draghud.util.Key;
import com.ejo.draghud.util.ConsoleUtil;
import net.fabricmc.api.ModInitializer;
import com.ejo.glowlib.setting.SettingManager;
import com.ejo.glowlib.time.StopWatch;
import net.minecraft.client.Minecraft;

public class DragHUD implements ModInitializer {

	public static Minecraft MC = Minecraft.getInstance();

	private static final SettingManager settingManager = new SettingManager("DragHUD", "settings");
	private static final GuiManager guiManager = new GuiManager();

	public static SettingManager getSettingManager() {
		return settingManager;
	}

	public static GuiManager getGuiManager() {
		return guiManager;
	}

	private static final StopWatch autoSaveTimer = new StopWatch();

	@Override
	public void onInitialize() {
		getGuiManager().instantiateGUI();

		getSettingManager().loadAll();

		startAutoSaveThread();

		Key.onKey.subscribe();
		getGuiManager().guiOpenAction.subscribe();
		getGuiManager().renderHUD.subscribe();
	}

	private void startAutoSaveThread() {
		int minutes = 5;
		Thread autoSaveThread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				autoSaveTimer.start();
				if (autoSaveTimer.hasTimePassedS(minutes * 60)) { //Save every 5 minutes
					getSettingManager().saveAll();
					System.out.println(ConsoleUtil.prefix + "AUTO-SAVE: Settings Saved");
					autoSaveTimer.restart();
				}
			}
		});
		autoSaveThread.setName("Auto Save Thread");
		autoSaveThread.setDaemon(true);
		autoSaveThread.start();
	}

}
