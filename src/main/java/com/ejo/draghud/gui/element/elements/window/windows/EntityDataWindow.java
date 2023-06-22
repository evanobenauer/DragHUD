package com.ejo.draghud.gui.element.elements.window.windows;

import com.ejo.draghud.gui.element.elements.window.GuiWindow;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import com.ejo.glowlib.math.Vector;

public class EntityDataWindow extends GuiWindow {

    public EntityDataWindow(Screen screen, Vector pos) {
        super(screen, "EntityData", pos, Vector.NULL);
    }


    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        setSize(new Vector(100,12));
        //TODO: Add this
    }
}