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

public class SettingWindow extends GuiWindow {

    private final ArrayList<GuiWidget> widgetList = new ArrayList<>();

    private final GuiButton closeButton;
    private final GuiWindow parentWindow;

    public SettingWindow(Screen screen, GuiWindow parentWindow, Vector pos) {
        super(screen, parentWindow.getTitle() + "_settings", pos, new Vector(100,20));
        this.parentWindow = parentWindow;
        this.closeButton = new GuiButton(getScreen(),"X",getPos(),getSize(),new ColorE(200,0,0),(args) -> {
            this.setDrawn(false);
            parentWindow.setSettingsOpen(false);
        });

        //TODO: Figure out how to order setting widgets
        for (Setting<?> setting : DragHUD.getSettingManager().getSettingList().values()) {
            if (setting instanceof SettingWidget settingWidget) {
                if (settingWidget.getWindow().getTitle().equals(parentWindow.getTitle())) {
                    if (settingWidget.getModes().size() > 0)
                        widgetList.add(new GuiModeCycle<>(getScreen(),settingWidget,Vector.NULL,Vector.NULL,DrawUtil.HUD_BLUE));
                    else if (settingWidget.getType().equals("boolean"))
                        widgetList.add(new GuiToggle(getScreen(), settingWidget, Vector.NULL, Vector.NULL, DrawUtil.HUD_BLUE));
                    else if (settingWidget.getType().equals("integer"))
                        widgetList.add(new GuiSlider<Integer>(getScreen(), settingWidget, Vector.NULL, Vector.NULL, DrawUtil.HUD_BLUE));
                    else if (settingWidget.getType().equals("double") || settingWidget.getType().equals("float"))
                        widgetList.add(new GuiSlider<Double>(getScreen(), settingWidget, Vector.NULL, Vector.NULL, DrawUtil.HUD_BLUE));
                    else if (settingWidget.getType().equals("string"))
                        widgetList.add(new GuiTextField(getScreen(), settingWidget, Vector.NULL, Vector.NULL));
                }
            }
        }
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

    public GuiWindow getParentWindow() {
        return parentWindow;
    }
}
