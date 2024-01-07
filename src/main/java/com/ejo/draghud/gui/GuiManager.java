package com.ejo.draghud.gui;

import com.ejo.draghud.DragHUD;
import com.ejo.draghud.gui.element.GuiWidget;
import com.ejo.draghud.event.EventRegistry;
import com.ejo.draghud.gui.element.window.GuiWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;
import com.ejo.glowlib.event.EventAction;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.setting.Setting;

import static com.ejo.draghud.DragHUD.MC;

public class GuiManager {

    private GUI gui;

    public GUI getGui() {
        return gui;
    }

    public void instantiateGUI() {
        gui = new GUI(Component.translatable("GUI"));
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
                DragHUD.getSettingManager().saveAll(); //Whenever the GUI closes, save all settings
                if (!element.isDrawn()) continue;
                if (!window.isPinned()) continue;
                element.draw(new GuiGraphics(MC,MC.renderBuffers().bufferSource()), new Vector(-1,-1)); //Draw each element on the HUD with a mousePos of NULL
            }
        }
    });

}