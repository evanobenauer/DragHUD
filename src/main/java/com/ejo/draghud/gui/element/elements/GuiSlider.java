package com.ejo.draghud.gui.element.elements;

import com.ejo.draghud.gui.element.GuiWidget;
import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.Key;
import com.ejo.draghud.util.SettingWidget;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import com.ejo.glowlib.math.MathE;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.util.NumberUtil;

public class GuiSlider<T extends Number> extends GuiWidget {

    private final SettingWidget<T> setting;
    private final String title;

    private ColorE color;

    private boolean sliding;

    private GuiSlider(Screen screen, String title, SettingWidget<T> setting, Vector pos, Vector size, ColorE color) {
        super(screen, pos, size, true);
        this.setting = setting;
        this.title = title;

        this.color = color;

        this.sliding = false;
    }

    public GuiSlider(Screen screen, SettingWidget<T> setting, Vector pos, Vector size, ColorE color) {
        this(screen,setting.getName(),setting,pos,size,color);
    }


    @Override
    protected void drawWidget(GuiGraphics graphics, Vector mousePos) {
        //Updates the value of the setting based off of the current width of the slider
        if (isSliding()) {
            double settingRange = getSetting().getMax() - getSetting().getMin();

            double sliderWidth = mousePos.getX() - getPos().getX();
            double sliderPercent = NumberUtil.getBoundValue(sliderWidth, 0, getSize().getX()).doubleValue() / getSize().getX();

            double calculatedValue = getSetting().getMin() + sliderPercent * settingRange;
            double val = MathE.roundDouble((((Math.round(calculatedValue / getSetting().getStep())) * getSetting().getStep())), 2); //Rounds the slider based off of the step val

            T value = getSetting().getType().equals("integer") ? (T) (Integer) (int) val : (T) (Double) val;
            getSetting().set(value);
        }

        //Draw the background
        DrawUtil.drawRectangle(graphics, getPos(), getSize(), new ColorE(50, 50, 50, 200));

        //Draw the slider fill
        double valueRange = getSetting().getMax() - getSetting().getMin();
        double sliderWidth = getSize().getX() / valueRange * (getSetting().get().doubleValue() - getSetting().getMin());
        int border = (int)getSize().getX()/40;
        DrawUtil.drawRectangle(graphics, getPos().getAdded(new Vector(border,border)), new Vector(sliderWidth -border, getSize().getY() - border*2), new ColorE(0, 125, 200, 150));

        //Draw the slider node
        int nodeWidth = (int) getSize().getY() / 4;
        double nodeX = sliderWidth - nodeWidth / 2f;
        if (nodeX + nodeWidth > getSize().getX()) nodeX = getSize().getX() - nodeWidth;
        if (nodeX < 0) nodeX = 0;
        DrawUtil.drawRectangle(graphics,getPos().getAdded(new Vector(nodeX,0)),new Vector(nodeWidth,getSize().getY()),new ColorE(0,125,200,255));

        //Draw the slider text
        String title;
        if (getSetting().getType().equals("integer")) {
            title = getTitle() + ": " + getSetting().get().intValue();
        } else {
            title = getTitle() + ": " + String.format("%.2f", getSetting().get());
        }
        double scale = 1;
        if (DrawUtil.getTextWidth(title) + border + 1 > getSize().getX()) scale = getSize().getX()/(DrawUtil.getTextWidth(title) + border + 1);

        DrawUtil.drawText(graphics, title, getPos().getAdded(new Vector(border + 1, 1 + getSize().getY() / 2 - DrawUtil.getTextHeight() / 2)), ColorE.WHITE,true,(float)scale);
    }


    @Override
    public void mousePressed(int button, int state, Vector mousePos) {
        if (state == Key.ACTION_PRESS) {
            if (isMouseOver() && button == 0) {
                setSliding(true);
            }
        }

        if (state == Key.ACTION_RELEASE) {
            if (button == 0) {
                if (isSliding()) setSliding(false);
            }
        }
    }

    @Override
    public void keyPressed(int key, int scancode, int modifiers) {
    }


    public void setSliding(boolean sliding) {
        this.sliding = sliding;
    }

    public void setColor(ColorE color) {
        this.color = color;
    }


    public String getTitle() {
        return title;
    }

    public boolean isSliding() {
        return sliding;
    }

    public ColorE getColor() {
        return color;
    }


    public SettingWidget<T> getSetting() {
        return setting;
    }
}
