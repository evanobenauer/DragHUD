package com.ejo.draghud.gui.element.elements.window.windows;

import com.ejo.draghud.gui.element.elements.window.GuiWindow;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import org.util.glowlib.math.Vector;

public class EntityListWindow extends GuiWindow {

    public EntityListWindow(Screen screen, Vector pos) {
        super(screen, "EntityList", pos, Vector.NULL);
    }


    @Override
    protected void drawWindow(PoseStack stack, Vector mousePos) {
        //TODO Add this
    }
}