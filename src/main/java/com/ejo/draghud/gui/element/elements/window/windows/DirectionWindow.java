package com.ejo.draghud.gui.element.elements.window.windows;

import com.ejo.draghud.gui.element.elements.window.GuiWindow;
import com.ejo.draghud.util.DrawUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

public class DirectionWindow extends GuiWindow {

    public DirectionWindow(Screen screen, Vector pos) {
        super(screen, "Direction", pos, new Vector(60, 13));
    }

    @Override
    public void drawWindow(PoseStack stack, Vector mousePos) {
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

        String label = "";//(labels ? "Direction " : "");
        String directionString = "";
            /*
            if (mode.equals("All")) direction = coordinateDirection + " " + cardinalDirection;
            if (mode.equals("Compass")) direction = cardinalDirection;
            if (mode.equals("Coordinate")) direction = coordinateDirection;
             */
        directionString = coordinateDirection + " " + cardinalDirection;

        setSize(new Vector((int) DrawUtil.getTextWidth(label + directionString) + 5, getSize().getY()));

        Vector pos = new Vector(
                (getPos().getX() + getSize().getX() / 2) - DrawUtil.getTextWidth(label + directionString) / 2,
                getPos().getY() + 2);
        DrawUtil.drawDualColorText(stack, label, directionString, pos, ColorE.GRAY, ColorE.WHITE);
    }

}
