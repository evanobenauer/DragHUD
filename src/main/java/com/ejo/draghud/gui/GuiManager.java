package com.ejo.draghud.gui;

import com.ejo.draghud.DragHUD;
import com.ejo.draghud.gui.element.GuiWidget;
import com.ejo.draghud.event.EventRegistry;
import com.ejo.draghud.gui.element.elements.window.GuiWindow;
import com.ejo.draghud.util.Util;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;
import com.ejo.glowlib.event.EventAction;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.setting.Setting;

public class GuiManager {

    private GUI gui;

    public GUI getGui() {
        return gui;
    }

    public GUI instantiateGUI() {
        return gui = new GUI(Component.translatable("GUI"));
    }


    public Setting<Integer> guiOpenKey = new Setting<>(DragHUD.getSettingManager(), "guiOpenKey", GLFW.GLFW_KEY_RIGHT_SHIFT);

    public EventAction guiOpenAction = new EventAction(EventRegistry.EVENT_KEY_PRESS, () -> {
        int key = EventRegistry.EVENT_KEY_PRESS.getKey();
        int action = EventRegistry.EVENT_KEY_PRESS.getState();
        if (key == guiOpenKey.get() && action == GLFW.GLFW_PRESS) {
            if (Minecraft.getInstance().screen == null) {
                Minecraft.getInstance().setScreen(getGui());
            } else if (Minecraft.getInstance().screen == gui) {
                Minecraft.getInstance().setScreen(null);
            }
        }
    });

    public EventAction renderHUD = new EventAction(EventRegistry.EVENT_RENDER_HUD, () -> {
        if (Minecraft.getInstance().screen == null) {
            for (GuiWidget element : DragHUD.getGuiManager().getGui().getGuiElementList()) {
                if (!(element instanceof GuiWindow window)) continue;
                window.setSettingsOpen(false); //Whenever the GUI closes, close all setting windows
                if (!element.isDrawn()) continue;
                if (!window.isPinned()) continue;
                element.draw(new GuiGraphics(Util.MC,Util.MC.renderBuffers().bufferSource()), new Vector(-1,-1)); //Draw each element on the HUD with a mousePos of NULL
            }
        }
    });

}