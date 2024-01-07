package com.ejo.draghud.gui.element.window;

import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;

import static com.ejo.draghud.DragHUD.MC;

public class CoordinatesWindow extends GuiWindow {

    private final SettingWidget<Boolean> label;
    private final SettingWidget<Boolean> showNether;
    private final SettingWidget<String> coordinatesMode;
    private final SettingWidget<String> coordinatesSide;

    public CoordinatesWindow(Screen screen, Vector pos) {
        super(screen, "Coordinates", pos, Vector.NULL);
        this.label = new SettingWidget<>(this, "Label", "Show the label", true);
        this.showNether = new SettingWidget<>(this, "Nether", "Shows the nether coordinates conversion", false);
        this.coordinatesMode = new SettingWidget<>(this,"Mode","Mode of coordinates","Horizontal","Horizontal","Vertical");
        this.coordinatesSide = new SettingWidget<>(this,"Side","Side of coordinates","Left","Left","Right","Center");
    }

    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        if (MC.player == null) return;

        String xLabel = (label.get() ? "X " : "");
        String yLabel = (label.get() ? "Y " : "");
        String zLabel = (label.get() ? "Z " : "");

        boolean shouldConvert = MC.level.dimensionTypeId().equals(BuiltinDimensionTypes.OVERWORLD) || MC.level.dimensionTypeId().equals(BuiltinDimensionTypes.NETHER);
        float conversion = MC.level.dimensionTypeId().equals(BuiltinDimensionTypes.OVERWORLD) ? (float) 1 /8 : 8;

        String x = String.format("%.1f", MC.player.getX()) + (showNether.get() && shouldConvert ? " (" + String.format("%.1f", MC.player.getX() * conversion) + ")" : "");
        String y = String.format("%.1f", MC.player.getY());
        String z = String.format("%.1f", MC.player.getZ()) + (showNether.get() && shouldConvert ? " (" + String.format("%.1f", MC.player.getZ() * conversion) + ")" : "");

        float xLength = (float) DrawUtil.getTextWidth(xLabel + x);
        float yLength = (float) DrawUtil.getTextWidth(yLabel + y);
        float zLength = (float) DrawUtil.getTextWidth(zLabel + z);

        ColorE color = ColorE.WHITE;
        ColorE labelColor = DrawUtil.HUD_LABEL;

        Vector startPos;

        Vector xPos = Vector.NULL;
        Vector yPos = Vector.NULL;
        Vector zPos = Vector.NULL;

        switch (coordinatesMode.get()) {
            case "Horizontal" -> {
                setSize(new Vector(!label.get() ? 172 : 220, 13));
                switch ((coordinatesSide.get())) {
                    case "Left" -> {
                        startPos = getPos().getAdded(2, 2);
                        xPos = startPos;
                        yPos = startPos.getAdded(xLength + 10, 0);
                        zPos = startPos.getAdded(xLength + yLength + 20, 0);
                    }
                    case "Right" -> {
                        startPos = getPos().getAdded(getSize().getX() - 4, 2);
                        xPos = startPos.getAdded(-xLength - yLength - zLength - 24, 0);
                        yPos = startPos.getAdded(-yLength - zLength - 12, 0);
                        zPos = startPos.getAdded(-zLength - 2, 0);
                    }
                    case "Center" -> {
                        startPos = getPos().getAdded(getSize().getX() / 2, 2);
                        xPos = startPos.getAdded(-yLength - xLength, 0);
                        yPos = startPos.getAdded(-(yLength / 2), 0);
                        zPos = startPos.getAdded(yLength, 0);
                    }
                }
            }
            case "Vertical" -> {
                setSize(new Vector(96, 33));
                switch ((coordinatesSide.get())) {
                    case "Left" -> {
                        startPos = getPos().getAdded(2, 2);
                        xPos = startPos;
                        yPos = startPos.getAdded(0, 10);
                        zPos = startPos.getAdded(0, 20);
                    }
                    case "Right" -> {
                        startPos = getPos().getAdded(getSize().getX() - 4, 2);
                        xPos = startPos.getAdded(-xLength, 0);
                        yPos = startPos.getAdded(-yLength, 10);
                        zPos = startPos.getAdded(-zLength, 20);
                    }
                    case "Center" -> {
                        startPos = getPos().getAdded(16 + DrawUtil.getTextWidth(getTitle()) / 2, 2);
                        xPos = startPos.getAdded(-xLength / 2, 0);
                        yPos = startPos.getAdded(-yLength / 2, 10);
                        zPos = startPos.getAdded(-zLength / 2, 20);
                    }
                }
            }
        }

        DrawUtil.drawDualColorText(graphics, xLabel, x, xPos, labelColor, color);
        DrawUtil.drawDualColorText(graphics, yLabel, y, yPos, labelColor, color);
        DrawUtil.drawDualColorText(graphics, zLabel, z, zPos, labelColor, color);
    }

}