package com.ejo.draghud.gui.element.elements.window.windows;

import com.ejo.draghud.gui.element.elements.window.GuiWindow;
import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import com.ejo.draghud.util.Util;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

public class CoordinatesWindow extends GuiWindow {

    private final SettingWidget<Boolean> label;
    private final SettingWidget<String> coordinatesMode;
    private final SettingWidget<String> coordinatesSide;

    public CoordinatesWindow(Screen screen, Vector pos) {
        super(screen, "Coordinates", pos, Vector.NULL);
        this.label = new SettingWidget<>(this, "Label", "Show the label", true);
        this.coordinatesMode = new SettingWidget<>(this,"Mode","Mode of coordinates","Horizontal","Horizontal","Vertical");
        this.coordinatesSide = new SettingWidget<>(this,"Side","Side of coordinates","Left","Left","Right","Center");
    }

    //TODO: Make Cleaner
    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        if (Util.MC.player != null) {
            String xlabel = (label.get() ? "X " : "");
            String ylabel = (label.get() ? "Y " : "");
            String zlabel = (label.get() ? "Z " : "");
            String x = String.format("%.1f", Util.MC.player.getX());
            String y = String.format("%.1f", Util.MC.player.getY());
            String z = String.format("%.1f", Util.MC.player.getZ());

            float xl = (float) DrawUtil.getTextWidth(xlabel + x);
            float yl = (float) DrawUtil.getTextWidth(ylabel + y);
            float zl = (float) DrawUtil.getTextWidth(zlabel + z);

            ColorE c = ColorE.WHITE;
            ColorE c2 = DrawUtil.HUD_LABEL;

            Vector posV = Vector.NULL;

            if (coordinatesMode.get().equals("Horizontal")) {
                if (!label.get())
                    setSize(new Vector(172,13));
                else
                    setSize(new Vector(220,13));

                if (coordinatesSide.get().equals("Left")) {
                    posV = getPos().getAdded(2,2);

                    DrawUtil.drawDualColorText(graphics, xlabel, x, posV, c2, c);
                    DrawUtil.drawDualColorText(graphics, ylabel, y, posV.getAdded(xl + 10,0), c2, c);
                    DrawUtil.drawDualColorText(graphics, zlabel, z, posV.getAdded(xl + yl + 20,0), c2, c);

                }
                if (coordinatesSide.get().equals("Right")) {
                    posV = getPos().getAdded(getSize().getX() - 4, 2);

                    DrawUtil.drawDualColorText(graphics, xlabel, x, posV.getAdded( - xl - yl - zl - 24,0), c2, c);
                    DrawUtil.drawDualColorText(graphics, ylabel, y, posV.getAdded( - yl - zl - 12,0), c2, c);
                    DrawUtil.drawDualColorText(graphics, zlabel, z, posV.getAdded( - zl - 2,0), c2, c);

                }
                if (coordinatesSide.get().equals("Center")) {
                    posV = getPos().getAdded(getSize().getX()/2,2);

                    DrawUtil.drawDualColorText(graphics, xlabel, x, posV.getAdded( - yl - xl,0), c2, c);
                    DrawUtil.drawDualColorText(graphics, ylabel, y, posV.getAdded( - (yl / 2),0), c2, c);
                    DrawUtil.drawDualColorText(graphics, zlabel, z, posV.getAdded(yl,0), c2, c);
                }
            }
            if (coordinatesMode.get().equals("Vertical")) {
                setSize(new Vector(96,33));
                if (coordinatesSide.get().equals("Left")) {
                    posV = getPos().getAdded(2,2);

                    DrawUtil.drawDualColorText(graphics, xlabel, x, posV, c2, c);
                    DrawUtil.drawDualColorText(graphics, ylabel, y, posV.getAdded(0,10), c2, c);
                    DrawUtil.drawDualColorText(graphics, zlabel, z, posV.getAdded(0,20), c2, c);
                }
                if (coordinatesSide.get().equals("Right")) {
                    posV = getPos().getAdded(getSize().getX() - 4, 2);

                    DrawUtil.drawDualColorText(graphics, xlabel, x, posV.getAdded(- xl,0), c2, c);
                    DrawUtil.drawDualColorText(graphics, ylabel, y, posV.getAdded(-yl,10), c2, c);
                    DrawUtil.drawDualColorText(graphics, zlabel, z, posV.getAdded(-zl,20), c2, c);
                }
                if (coordinatesSide.get().equals("Center")) {
                    posV = getPos().getAdded(16 + DrawUtil.getTextWidth(getTitle())/2,2);

                    DrawUtil.drawDualColorText(graphics, xlabel, x, posV.getAdded( - xl / 2,0), c2, c);
                    DrawUtil.drawDualColorText(graphics, ylabel, y, posV.getAdded(-yl / 2,10), c2, c);
                    DrawUtil.drawDualColorText(graphics, zlabel, z, posV.getAdded(-zl/2,20), c2, c);
                }
            }
        }
    }

    /* BACKUP
    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        if (Util.MC.player != null) {
            String xlabel = (label.get() ? "X " : "");
            String ylabel = (label.get() ? "Y " : "");
            String zlabel = (label.get() ? "Z " : "");
            String x = String.format("%.1f", Util.MC.player.getX());
            String y = String.format("%.1f", Util.MC.player.getY());
            String z = String.format("%.1f", Util.MC.player.getZ());

            float xl = (float) DrawUtil.getTextWidth(xlabel + x);
            float yl = (float) DrawUtil.getTextWidth(ylabel + y);
            float zl = (float) DrawUtil.getTextWidth(zlabel + z);

            ColorE c = ColorE.WHITE;
            ColorE c2 = DrawUtil.HUD_LABEL;

            if (coordinatesMode.get().equals("Horizontal")) {
                if (!label.get())
                    setSize(new Vector(172,13));
                else
                    setSize(new Vector(220,13));

                if (coordinatesSide.get().equals("Left")) {
                    Vector posV = getPos().getAdded(2,2);

                    DrawUtil.drawDualColorText(graphics, xlabel, x, posV, c2, c);
                    DrawUtil.drawDualColorText(graphics, ylabel, y, posV.getAdded(xl + 10,0), c2, c);
                    DrawUtil.drawDualColorText(graphics, zlabel, z, posV.getAdded(xl + yl + 20,0), c2, c);

                }
                if (coordinatesSide.get().equals("Right")) {
                    Vector posV = getPos().getAdded(getSize().getX() - 4, 2);

                    DrawUtil.drawDualColorText(graphics, xlabel, x, posV.getAdded( - xl - yl - zl - 24,0), c2, c);
                    DrawUtil.drawDualColorText(graphics, ylabel, y, posV.getAdded( - yl - zl - 12,0), c2, c);
                    DrawUtil.drawDualColorText(graphics, zlabel, z, posV.getAdded( - zl - 2,0), c2, c);

                }
                if (coordinatesSide.get().equals("Center")) {
                    Vector posV = getPos().getAdded(getSize().getX()/2,2);

                    DrawUtil.drawDualColorText(graphics, xlabel, x, posV.getAdded( - yl - xl,0), c2, c);
                    DrawUtil.drawDualColorText(graphics, ylabel, y, posV.getAdded( - (yl / 2),0), c2, c);
                    DrawUtil.drawDualColorText(graphics, zlabel, z, posV.getAdded(yl,0), c2, c);
                }
            }
            if (coordinatesMode.get().equals("Vertical")) {
                setSize(new Vector(96,33));
                if (coordinatesSide.get().equals("Left")) {
                    Vector posV = getPos().getAdded(2,2);

                    DrawUtil.drawDualColorText(graphics, xlabel, x, posV, c2, c);
                    DrawUtil.drawDualColorText(graphics, ylabel, y, posV.getAdded(0,10), c2, c);
                    DrawUtil.drawDualColorText(graphics, zlabel, z, posV.getAdded(0,20), c2, c);
                }
                if (coordinatesSide.get().equals("Right")) {
                    Vector posV = getPos().getAdded(getSize().getX() - 4, 2);

                    DrawUtil.drawDualColorText(graphics, xlabel, x, posV.getAdded(- xl,0), c2, c);
                    DrawUtil.drawDualColorText(graphics, ylabel, y, posV.getAdded(-yl,10), c2, c);
                    DrawUtil.drawDualColorText(graphics, zlabel, z, posV.getAdded(-zl,20), c2, c);
                }
                if (coordinatesSide.get().equals("Center")) {
                    Vector posV = getPos().getAdded(16 + DrawUtil.getTextWidth(getTitle())/2,2);

                    DrawUtil.drawDualColorText(graphics, xlabel, x, posV.getAdded( - xl / 2,0), c2, c);
                    DrawUtil.drawDualColorText(graphics, ylabel, y, posV.getAdded(-yl / 2,10), c2, c);
                    DrawUtil.drawDualColorText(graphics, zlabel, z, posV.getAdded(-zl/2,20), c2, c);
                }
            }
        }
    }

     */

}