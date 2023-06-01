package com.ejo.draghud.gui.element.elements.window.windows;

import com.ejo.draghud.gui.element.elements.window.GuiWindow;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import org.util.glowlib.math.Vector;

public class EntityDataWindow extends GuiWindow {

    public EntityDataWindow(Screen screen, Vector pos) {
        super(screen, "EntityData", pos, Vector.NULL);
    }


    @Override
    protected void drawWindow(PoseStack stack, Vector mousePos) {
        setSize(new Vector(100,12));
        //TODO: Add this
    }
}