package com.ejo.draghud;

import com.ejo.draghud.event.EventRegistry;
import com.ejo.draghud.gui.GuiManager;
import com.ejo.draghud.util.Key;
import com.ejo.draghud.util.ConsoleUtil;
import com.ejo.glowlib.event.EventAction;
import net.fabricmc.api.ModInitializer;
import com.ejo.glowlib.setting.SettingManager;
import com.ejo.glowlib.time.StopWatch;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;

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

	@Override
	public void onInitialize() {
		getGuiManager().instantiateGUI();

		getSettingManager().loadAll();

		Key.onKey.subscribe();

		getGuiManager().guiOpenAction.subscribe();
		getGuiManager().renderHUD.subscribe();

		//applyAutoFish();
	}

	//AUTO FISH ------------------------- TODO: REMOVE LATER-----------------------------------------------------------------------------------
	private boolean shouldRecast = false;
	private static final StopWatch autoFishTimer = new StopWatch();

	private void applyAutoFish() {
		new EventAction(EventRegistry.EVENT_PACKET, () -> {
			Packet<?> packet = EventRegistry.EVENT_PACKET.getPacket();
			if (packet instanceof ClientboundSoundPacket soundPacket) {
				if (soundPacket.getSound().value().equals(SoundEvents.FISHING_BOBBER_SPLASH)) {
					autoFishTimer.start();
					autoFishTimer.restart();
					shouldRecast = true;
					MC.getConnection().send(new ServerboundUseItemPacket(InteractionHand.MAIN_HAND,0)); //Retrieve
				}
			}

			if (autoFishTimer.hasTimePassedS(1) && shouldRecast) {
				shouldRecast = false;
				MC.getConnection().send(new ServerboundUseItemPacket(InteractionHand.MAIN_HAND, 6)); //Recast
			}

		}).subscribe();
	}

}
