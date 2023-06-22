package com.ejo.draghud.gui.element.elements.window.windows;

import com.ejo.draghud.DragHUD;
import com.ejo.draghud.gui.element.elements.window.GuiWindow;
import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.setting.Setting;

public class DirectionWindow extends GuiWindow {

    private final SettingWidget<Boolean> label;
    private final SettingWidget<String> mode;

    public DirectionWindow(Screen screen, Vector pos) {
        super(screen, "Direction", pos, new Vector(60, 13));
        this.label = new SettingWidget<>(this, "Label","Show the label",true);
        this.mode = new SettingWidget<>(this,"Mode","Mode of Direction","All","All","Compass","Coordinate");
    }

    @Override
    public void drawWindow(GuiGraphics graphics, Vector mousePos) {
        if (Minecraft.getInstance().player == null) return;

        Direction direction = Minecraft.getInstance().player.getDirection();
        String coordinateDirection = switch (direction) {
            case NORTH -> "[-Z]";
            case SOUTH -> "[+Z]";
            case WEST -> "[-X]";
            case EAST -> "[+X]";
            default -> "Loading...";
        };

        String cardinalDirection = switch (direction) {
            case NORTH -> "North";
            case SOUTH -> "South";
            case WEST -> "West";
            case EAST -> "East";
            default -> "Loading...";
        };

        String label = (this.label.get() ? "Direction " : "");
        String directionString = "";
            if (mode.get().equals("All")) directionString = coordinateDirection + " " + cardinalDirection;
            if (mode.get().equals("Compass")) directionString = cardinalDirection;
            if (mode.get().equals("Coordinate")) directionString = coordinateDirection;

        setSize(new Vector((int) DrawUtil.getTextWidth(label + directionString) + 5, getSize().getY()));

        Vector pos = new Vector(
                getPos().getX() + getSize().getX() / 2 - DrawUtil.getTextWidth(label + directionString) / 2,
                getPos().getY() + 2);
        DrawUtil.drawDualColorText(graphics, label, directionString, pos, DrawUtil.HUD_LABEL, ColorE.WHITE);
    }

}
