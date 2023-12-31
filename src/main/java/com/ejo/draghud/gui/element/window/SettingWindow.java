package com.ejo.draghud.gui.element.window;

import com.ejo.draghud.DragHUD;
import com.ejo.draghud.gui.element.*;
import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.setting.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class SettingWindow extends GuiWindow {

    private ArrayList<GuiWidget> widgetList = new ArrayList<>();

    private final GuiButton closeButton;
    private final GuiWindow parentWindow;

    public SettingWindow(Screen screen, GuiWindow parentWindow, Vector pos) {
        super(screen, parentWindow.getTitle() + "_settings", pos, new Vector(100, 20));
        this.parentWindow = parentWindow;
        this.closeButton = new GuiButton(getScreen(), "X", getPos(), getSize(), new ColorE(200, 0, 0), (args) -> {
            this.setDrawn(false);
            parentWindow.setSettingsOpen(false);
        });

        for (Setting<?> setting : DragHUD.getSettingManager().getSettingList().values()) {
            if (setting instanceof SettingWidget settingWidget && settingWidget.getWindow().getTitle().equals(parentWindow.getTitle())) {

                if (settingWidget.getModes().size() > 0) {
                    widgetList.add(new GuiModeCycle<>(getScreen(), settingWidget, Vector.NULL, Vector.NULL, DrawUtil.HUD_BLUE));
                    continue;
                }
                switch (settingWidget.getType()) {
                    case "boolean" -> widgetList.add(new GuiToggle(getScreen(), settingWidget, Vector.NULL, Vector.NULL, DrawUtil.HUD_BLUE));
                    case "integer" -> widgetList.add(new GuiSlider<Integer>(getScreen(), settingWidget, Vector.NULL, Vector.NULL, DrawUtil.HUD_BLUE));
                    case "double", "float" -> widgetList.add(new GuiSlider<Double>(getScreen(), settingWidget, Vector.NULL, Vector.NULL, DrawUtil.HUD_BLUE));
                    case "string" -> widgetList.add(new GuiTextField(getScreen(), settingWidget, Vector.NULL, Vector.NULL));
                }
            }
        }

        GuiWidget[] widgets = widgetList.toArray(new GuiWidget[0]);
        Arrays.sort(widgets, new WidgetComparator());
        this.widgetList = new ArrayList<>(Arrays.asList(widgets));
    }


    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        //Draw Header
        DrawUtil.drawRectangle(graphics,getPos(),getSize(), new ColorE(0,125,200,255));

        //Close "X" button
        this.closeButton.setPos(getPos().getAdded(new Vector(4,4)));
        this.closeButton.setSize(new Vector(getSize().getY() - 8, getSize().getY() - 8));
        this.closeButton.draw(graphics,mousePos);
        if (this.closeButton.isMouseOver()) updateMouseOver(new Vector(-1,-1)); //Stops the window from being draggable if over X button

        //Draw Title
        DrawUtil.drawText(
                graphics,
                getParentWindow().getTitle(),
                getPos().getAdded(new Vector(closeButton.getSize().getX() + 4 + 2,1 + getSize().getY()/2 - DrawUtil.getTextHeight()/2)),
                ColorE.WHITE);

        //Draw Widgets
        int yOffset = 0;
        for (GuiWidget widget : widgetList) {
            widget.setPos(getPos().getAdded(new Vector(2,getSize().getY())));
            widget.setPos(widget.getPos().getAdded(new Vector(0,yOffset)));
            widget.setSize(getSize().getAdded(new Vector(-4,-4)));
            widget.draw(graphics,mousePos);
            yOffset += getSize().getY()-4;
        }
    }

    @Override
    public void mousePressed(int button, int state, Vector mousePos) {
        super.mousePressed(button, state, mousePos);
        this.closeButton.mousePressed(button,state,mousePos);
        for (GuiWidget widget : widgetList) {
            widget.mousePressed(button,state,mousePos);
        }
    }

    @Override
    public void keyPressed(int key, int scancode, int modifiers) {
        super.keyPressed(key, scancode, modifiers);
        this.closeButton.keyPressed(key,scancode,modifiers);
        for (GuiWidget widget : widgetList) {
            widget.keyPressed(key,scancode,modifiers);
        }
    }

    private static class WidgetComparator implements Comparator<GuiWidget> {

        @Override
        public int compare(GuiWidget widget1, GuiWidget widget2) {
            int typeWeight = getTypeWeight(widget1) - getTypeWeight(widget2);
            int letterWeight = getIndexFromLetter(widget1.getSetting().getName().charAt(0)) - getIndexFromLetter(widget2.getSetting().getName().charAt(0));

            return typeWeight + letterWeight;
        }

        private int getIndexFromLetter(char letter) {
            return switch (letter) {
                case 'a', 'A' -> 0;
                case 'b', 'B' -> 1;
                case 'c', 'C' -> 2;
                case 'd', 'D' -> 3;
                case 'e', 'E' -> 4;
                case 'f', 'F' -> 5;
                case 'g', 'G' -> 6;
                case 'h', 'H' -> 7;
                case 'i', 'I' -> 8;
                case 'j', 'J' -> 9;
                case 'k', 'K' -> 10;
                case 'l', 'L' -> 11;
                case 'm', 'M' -> 12;
                case 'n', 'N' -> 13;
                case 'o', 'O' -> 14;
                case 'p', 'P' -> 15;
                case 'q', 'Q' -> 16;
                case 'r', 'R' -> 17;
                case 's', 'S' -> 18;
                case 't', 'T' -> 19;
                case 'u', 'U' -> 20;
                case 'v', 'V' -> 21;
                case 'w', 'W' -> 22;
                case 'x', 'X' -> 23;
                case 'y', 'Y' -> 24;
                case 'z', 'Z' -> 25;
                default -> -1;
            };
        }

        private int getTypeWeight(GuiWidget widget) {
            //Lower number = higher in list
            int weightMul = 1000;
            if (widget.getSetting().getModes().size() > 0) return weightMul;
            return weightMul * switch (widget.getSetting().getType()) {
                case "boolean" -> 4;
                case "integer", "float", "double" -> 2;
                case "string" -> 3;
                default -> -1;
            };
        }
    }

    public GuiWindow getParentWindow() {
        return parentWindow;
    }

}
