package com.ejo.draghud.gui.element.window;

import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

public class SpeedWindow extends GuiWindow {

    private final SettingWidget<Boolean> label;

    public SettingWidget<String> mode;

    public SpeedWindow(Screen screen, Vector pos) {
        super(screen, "Speed", pos, Vector.NULL);
        this.label = new SettingWidget<>(this, "Label","Show the label",true);
        this.mode = new SettingWidget<>(this,"Mode","Detects whether speed is in XY or XYZ","2D","2D","3D");
    }

    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        String label = (this.label.get() ? "Speed " : "");
        String currentSpeed = String.format("%.2f", getEntitySpeed(Minecraft.getInstance().player, mode.get().equals("3D"))) + "m/s";

        setSize(new Vector((int) DrawUtil.getTextWidth(label + currentSpeed) + 6,13));

        DrawUtil.drawDualColorText(graphics,label,currentSpeed,getPos().getAdded(new Vector(2,2)),DrawUtil.HUD_LABEL,ColorE.WHITE);
    }

    private double getEntitySpeed(Entity entity, boolean XYZ) {
        if (entity != null) {
            if (!XYZ) {
                double distTraveledLastTickX = entity.getX() - entity.xo;
                double distTraveledLastTickZ = entity.getZ() - entity.zo;
                double speed = Mth.sqrt((float) (distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ));
                return speed * 20;
            } else {
                double distTraveledLastTickX = entity.getX() - entity.xo;
                double distTraveledLastTickY = entity.getY() - entity.yo;
                double distTraveledLastTickZ = entity.getZ() - entity.zo;
                double speed = Mth.sqrt((float)(distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ + distTraveledLastTickY * distTraveledLastTickY));
                return speed * 20;
            }
        }
        return 0;
    }
}
