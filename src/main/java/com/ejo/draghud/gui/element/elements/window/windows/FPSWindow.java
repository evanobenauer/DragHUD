package com.ejo.draghud.gui.element.elements.window.windows;

import com.ejo.draghud.gui.element.elements.window.GuiWindow;
import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

public class FPSWindow extends GuiWindow {

    private final SettingWidget<Boolean> label;

    public FPSWindow(Screen screen, Vector pos) {
        super(screen, "FPS", pos, Vector.NULL);
        this.label = new SettingWidget<>(this, "Label","Show the label",true);
    }

    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        String label = (this.label.get() ? "FPS " : "");
        String text =  (Minecraft.getInstance().fpsString.substring(0,3)).replace("/","").replace("f","").replace(" ","");

        setSize(new Vector((int) DrawUtil.getTextWidth(label + text) + 6,13));

        DrawUtil.drawDualColorText(graphics,label,text,getPos().getAdded(new Vector(2,2)),DrawUtil.HUD_LABEL,ColorE.WHITE);
    }

}
