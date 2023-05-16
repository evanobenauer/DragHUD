package com.ejo.glowhud.gui.element.elements.window.windows;

import com.ejo.glowhud.gui.element.elements.window.GuiWindow;
import com.ejo.glowhud.util.DrawUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

public class FPSWindow extends GuiWindow {

    private final boolean doLabels = true;
    private final ColorE labelColor = ColorE.GRAY;

    public FPSWindow(Screen screen, Vector pos) {
        super(screen, "FPS", pos, Vector.NULL);
    }

    @Override
    protected void drawWindow(PoseStack stack, Vector mousePos) {
        RenderSystem.setShaderColor(1,1,1,1);
        String label = (doLabels ? "FPS " : "");
        String text =  (Minecraft.getInstance().fpsString.substring(0,3)).replace("/","").replace("f","").replace(" ","");

        setSize(new Vector((int) DrawUtil.getTextWidth(label + text) + 6,13));

        DrawUtil.drawDualColorText(stack,label,text,getPos().getAdded(new Vector(2,2)),labelColor,ColorE.WHITE);
    }

}
