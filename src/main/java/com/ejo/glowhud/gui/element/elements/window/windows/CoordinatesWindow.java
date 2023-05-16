package com.ejo.glowhud.gui.element.elements.window.windows;

import com.ejo.glowhud.gui.element.elements.window.GuiWindow;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

public class CoordinatesWindow extends GuiWindow {

    public CoordinatesWindow(Screen screen, Vector pos) {
        super(screen, "Coordinates", pos, Vector.NULL);
    }

    @Override
    protected void drawWindow(PoseStack stack, Vector mousePos) {
        //TODO: ADD
    }

}