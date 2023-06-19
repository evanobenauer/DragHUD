package com.ejo.draghud.gui.element.elements.window.windows;

import com.ejo.draghud.gui.element.elements.window.GuiWindow;
import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

public class SpeedWindow extends GuiWindow {

    private final boolean doLabels = true;
    private final ColorE labelColor = ColorE.GRAY;

    public SettingWidget<String> mode;

    public SpeedWindow(Screen screen, Vector pos) {
        super(screen, "Speed", pos, Vector.NULL);
        this.mode = new SettingWidget<>(this,"Mode","Desc","2D","2D","3D");
    }

    @Override
    protected void drawWindow(PoseStack stack, Vector mousePos) {
        RenderSystem.setShaderColor(1,1,1,1);
        String label = (doLabels ? "Speed " : "");
        String currentSpeed = String.format("%.2f", getEntitySpeed(Minecraft.getInstance().player, mode.get().equals("3D"))) + "m/s";

        setSize(new Vector((int) DrawUtil.getTextWidth(label + currentSpeed) + 6,13));

        DrawUtil.drawDualColorText(stack,label,currentSpeed,getPos().getAdded(new Vector(2,2)),labelColor,ColorE.WHITE);
    }

    private double getEntitySpeed(Entity entity, boolean XYZ) {
        if (entity != null) {
            if (!XYZ) {
                double distTraveledLastTickX = entity.getX() - entity.xo;
                double distTraveledLastTickZ = entity.getZ() - entity.zo;
                double sped = Mth.sqrt((float) (distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ));
                return sped * 20;
            } else {
                double distTraveledLastTickX = entity.getX() - entity.xo;
                double distTraveledLastTickY = entity.getY() - entity.yo;
                double distTraveledLastTickZ = entity.getZ() - entity.zo;
                double sped = Mth.sqrt((float)(distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ + distTraveledLastTickY * distTraveledLastTickY));
                return sped * 20;
            }
        }
        return 0;
    }
}
