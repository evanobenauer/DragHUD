package com.ejo.draghud.gui.element.window;

import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

public class TPSWindow extends GuiWindow {

    private final SettingWidget<Boolean> label;

    public TPSWindow(Screen screen, Vector pos) {
        super(screen, "TPS", pos, Vector.NULL);
        this.label = new SettingWidget<>(this, "Label","Show the label",true);
    }

    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        RenderSystem.setShaderColor(1,1,1,1);
        String label = (this.label.get() ? "TPS " : "");
        String text = String.format("%.2f",0.0);

        setSize(new Vector((int) DrawUtil.getTextWidth(label + text) + 6,13));

        DrawUtil.drawDualColorText(graphics,label,text,getPos().getAdded(new Vector(2,2)),DrawUtil.HUD_LABEL,ColorE.WHITE);
    }

}
