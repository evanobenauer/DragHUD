package com.ejo.glowhud;

import com.ejo.glowhud.gui.GuiManager;
import com.ejo.glowhud.util.Util;
import net.fabricmc.api.ModInitializer;
import org.util.glowlib.setting.SettingManager;
import org.util.glowlib.time.StopWatch;

public class GlowHUD implements ModInitializer {

	private static final SettingManager settingManager = new SettingManager("GlowHUD", "settings");
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

		getGuiManager().guiOpenAction.subscribe();
		getGuiManager().renderHUD.subscribe();
	}

	private void startAutoSaveThread() {
		int minutes = 5;
		Thread autoSaveThread = new Thread(() -> {
			while (true) {
				autoSaveTimer.start();
				if (autoSaveTimer.hasTimePassedS(minutes * 60)) { //Save every 5 minutes
					getSettingManager().saveAll();
					System.out.println(Util.prefix + "AUTO-SAVE: Settings Saved");
					autoSaveTimer.restart();
				}
			}
		});
		autoSaveThread.setName("Auto Save Thread");
		autoSaveThread.setDaemon(true);
		autoSaveThread.start();
	}

}
