package com.ejo.draghud.gui.element.elements;

import com.ejo.draghud.gui.element.GuiWidget;
import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.Key;
import com.ejo.draghud.util.SettingWidget;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.util.NumberUtil;

public class GuiModeCycle<T> extends GuiWidget {

    private final SettingWidget<T> setting;

    private ColorE baseColor;
    private ColorE colorR;
    private ColorE colorL;

    private int arrayNumber;

    public GuiModeCycle(Screen screen, SettingWidget<T> setting, Vector pos, Vector size, ColorE color) {
        super(screen, pos, size, true);
        this.setting = setting;
        this.baseColor = color;
        this.colorL = color;
        this.colorR = color;
    }

    @Override
    protected void drawWidget(PoseStack stack, Vector mousePos) {
        //Draw Background
        DrawUtil.drawRectangle(stack,getPos(),getSize(),new ColorE(50,50,50,200));

        //Draw Mode Arrows
        DrawUtil.drawRectangle(stack,getPos().getAdded(2,2),new Vector(2,getSize().getY()-4),getColorL());
        DrawUtil.drawRectangle(stack,getPos().getAdded(-2 + getSize().getX() - 2,2),new Vector(2,getSize().getY()-4),getColorR());

        //Draw the slider text
        String text = getSetting().getName() + ": " + getSetting().get();
        DrawUtil.drawText(stack,text,getPos().getAdded(getSize().getX()/2 - DrawUtil.getTextWidth(text)/2,getSize().getY()/2 - DrawUtil.getTextHeight()/2),ColorE.WHITE);
    }

    @Override
    public void mousePressed(int button, int action, Vector mousePos) {
        int RIGHT = 1;
        int LEFT = 0;
        if (isMouseOver()) {
            if (action == 0) {
                if (button == RIGHT) {
                    arrayNumber = getSetting().getModes().indexOf(getSetting().get());
                    arrayNumber += 1;
                    if (arrayNumber != getSetting().getModes().size()) {
                        getSetting().set(getSetting().getModes().get(arrayNumber));
                    } else {
                        getSetting().set(getSetting().getModes().get(0));
                        arrayNumber = 0;
                    }
                }
                if (button == LEFT) {
                    arrayNumber = getSetting().getModes().indexOf(getSetting().get());
                    arrayNumber -= 1;
                    if (arrayNumber != -1) {
                        getSetting().set(getSetting().getModes().get(arrayNumber));
                    } else {
                        getSetting().set(getSetting().getModes().get(getSetting().getModes().size() - 1));
                        arrayNumber = getSetting().getModes().size() - 1;
                    }
                }
            }
            if (action == Key.ACTION_PRESS) {
                int[] colVal = {getColor().getRed(),getColor().getGreen(),getColor().getBlue()};
                for (int i = 0; i < colVal.length; i++) {
                    int col = colVal[i] - 50;
                    col = NumberUtil.getBoundValue(col,0,255).intValue();
                    colVal[i] = col;
                }
                if (button == RIGHT) setColorR(new ColorE(colVal[0],colVal[1],colVal[2],getColor().getAlpha()));
                if (button == LEFT) setColorL(new ColorE(colVal[0],colVal[1],colVal[2],getColor().getAlpha()));
            }
        }
        if (action == Key.ACTION_RELEASE) {
            if (button == RIGHT) setColorR(baseColor);
            if (button == LEFT) setColorL(baseColor);
        }
    }

    @Override
    public void keyPressed(int key, int scancode, int modifiers) {

    }

    public void setColor(ColorE color) {
        this.baseColor = color;
    }

    private void setColorR(ColorE colorR) {
        this.colorR = colorR;
    }

    private void setColorL(ColorE colorL) {
        this.colorL = colorL;
    }

    public ColorE getColor() {
        return baseColor;
    }

    private ColorE getColorR() {
        return colorR;
    }

    private ColorE getColorL() {
        return colorL;
    }

    @Override
    public SettingWidget<T> getSetting() {
        return setting;
    }
}
