package com.ejo.glowhud.gui.element.elements.window.windows;

import com.ejo.glowhud.gui.element.elements.window.GuiWindow;
import com.ejo.glowhud.util.DrawUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

public class TPSWindow extends GuiWindow {

    private final boolean doLabels = true;
    private final ColorE labelColor = ColorE.GRAY;

    public TPSWindow(Screen screen, Vector pos) {
        super(screen, "TPS", pos, Vector.NULL);
    }

    @Override
    protected void drawWindow(PoseStack stack, Vector mousePos) {
        RenderSystem.setShaderColor(1,1,1,1);
        String label = (doLabels ? "TPS " : "");
        String text = String.format("%.2f",0.0);

        setSize(new Vector((int) DrawUtil.getTextWidth(label + text) + 6,13));

        DrawUtil.drawDualColorText(stack,label,text,getPos().getAdded(new Vector(2,2)),labelColor,ColorE.WHITE);
    }

}
