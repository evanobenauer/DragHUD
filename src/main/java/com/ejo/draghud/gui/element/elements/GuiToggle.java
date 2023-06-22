package com.ejo.draghud.gui.element.elements;

import com.ejo.draghud.event.EventRegistry;
import com.ejo.draghud.gui.element.GuiWidget;
import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import com.ejo.glowlib.event.EventAction;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

public class GuiToggle extends GuiWidget {

    private final SettingWidget<Boolean> setting;
    private final String title;

    private float toggleFade = 0;

    private final EventAction ANIMATION_TOGGLE_FADE = new EventAction(EventRegistry.EVENT_CALCULATE_ANIMATION, () -> {
        toggleFade = getNextFade(getSetting().get(), toggleFade, 0, 150, 2f);
    });


    private GuiToggle(Screen screen, String title, SettingWidget<Boolean> setting, Vector pos, Vector size, ColorE color) {
        super(screen,pos,size,true);
        this.setting = setting;
        this.title = title;
        this.ANIMATION_TOGGLE_FADE.subscribe();
    }

    public GuiToggle(Screen screen, SettingWidget<Boolean> setting, Vector pos, Vector size, ColorE color) {
        this(screen,setting.getName(),setting,pos,size,color);
    }


    @Override
    protected void drawWidget(GuiGraphics graphics, Vector mousePos) {
        DrawUtil.drawRectangle(graphics, getPos(), getSize(), new ColorE(50, 50, 50, 200));
        DrawUtil.drawRectangle(graphics, getPos(), getSize(), new ColorE(0, 125, 200, (int) toggleFade));
        double scale = 1;
        if (DrawUtil.getTextWidth(getTitle()) + 2 > getSize().getX()) scale = getSize().getX()/(DrawUtil.getTextWidth(getTitle()) + 2);
        DrawUtil.drawText(graphics, getTitle(), getPos().getAdded(new Vector(2, 1 + getSize().getY() / 2 - DrawUtil.getTextHeight() / 2)), ColorE.WHITE,true,(float)scale);
    }


    @Override
    public void mousePressed(int button, int state, Vector mousePos) {
        if (state == 1 && isMouseOver()) {
            getSetting().set(!getSetting().get());
        }
    }

    @Override
    public void keyPressed(int key, int scancode, int modifiers) {
        //Null
    }


    public String getTitle() {
        return title;
    }

    public SettingWidget<Boolean> getSetting() {
        return setting;
    }

}
