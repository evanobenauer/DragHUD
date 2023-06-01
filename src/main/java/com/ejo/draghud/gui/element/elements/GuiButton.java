package com.ejo.draghud.gui.element.elements;

import com.ejo.draghud.gui.element.GuiWidget;
import com.ejo.draghud.util.DrawUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.util.LambdaUtil;

public class GuiButton extends GuiWidget {

    private String title;
    private LambdaUtil.actionVoid action;
    private ColorE baseColor;
    private ColorE color;

    public GuiButton(Screen screen, String title, Vector pos, Vector size, ColorE color, LambdaUtil.actionVoid action) {
        super(screen, pos, size,true);
        this.title = title;
        this.action = action;
        this.color = color;
        this.baseColor = color;
    }

    @Override
    protected void drawWidget(PoseStack stack, Vector mousePos) {
        DrawUtil.drawRectangle(stack, getPos(), getSize(), getColor());
        DrawUtil.drawText(stack, getTitle(), getPos().getAdded(new Vector(getSize().getX() / 2 - DrawUtil.getTextWidth(getTitle()) / 2, getSize().getY() / 2 - DrawUtil.getTextHeight() / 2)), ColorE.WHITE);
    }

    @Override
    public void mousePressed(int button, int state, Vector mousePos) {
       if (state == 1) {
           this.baseColor = getColor();
           if (isMouseOver()) {
               if (button == 0) {
                   int[] colVal = {getColor().getRed(), getColor().getGreen(), getColor().getBlue()};
                   for (int i = 0; i < colVal.length; i++) {
                       int col = colVal[i] - 50;
                       if (col > 255) col = 255;
                       if (col < 0) col = 0;
                       colVal[i] = col;
                   }
                   setColor(new ColorE(colVal[0], colVal[1], colVal[2], baseColor.getAlpha()));
               }
           }
       }
       if (state == 0) {
           setColor(baseColor);
           try {
               if (isMouseOver()) getAction().run(mousePos, button);
           } catch (Exception e) {//
           }
       }
    }

    @Override
    public void keyPressed(int key, int scancode, int modifiers) {
        //Null
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setAction(LambdaUtil.actionVoid action) {
        this.action = action;
    }

    public void setColor(ColorE color) {
        this.color = color;
    }


    public String getTitle() {
        return title;
    }

    private LambdaUtil.actionVoid getAction() {
        return action;
    }

    public ColorE getColor() {
        return color;
    }

}
