package com.ejo.draghud.gui.element.elements;

import com.ejo.draghud.event.EventRegistry;
import com.ejo.draghud.gui.element.GuiWidget;
import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import org.util.glowlib.event.EventAction;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.setting.SettingUI;

public class GuiToggle extends GuiWidget {

    private final SettingUI<Boolean> setting;
    private final String title;

    private float toggleFade = 0;

    private final EventAction ANIMATION_TOGGLE_FADE = new EventAction(EventRegistry.EVENT_CALCULATE_ANIMATION, () -> {
        toggleFade = getNextFade(getSetting().get(), toggleFade, 0, 150, 2f);
    });


    private GuiToggle(Screen screen, String title, SettingUI<Boolean> setting, Vector pos, Vector size, ColorE color) {
        super(screen,pos,size,true);
        this.setting = setting;
        this.title = title;
        this.ANIMATION_TOGGLE_FADE.subscribe();
    }

    public GuiToggle(Screen screen, SettingUI<Boolean> setting, Vector pos, Vector size, ColorE color) {
        this(screen,setting.getKey(),setting,pos,size,color);
    }

    public GuiToggle(Screen screen, SettingWidget<Boolean> setting, Vector pos, Vector size, ColorE color) {
        this(screen,setting.getName(),setting,pos,size,color);
    }


    @Override
    protected void drawWidget(PoseStack stack, Vector mousePos) {
        DrawUtil.drawRectangle(stack, getPos(), getSize(), new ColorE(50, 50, 50, 200));
        DrawUtil.drawRectangle(stack, getPos(), getSize(), new ColorE(0, 125, 200, (int) toggleFade));
        DrawUtil.drawText(stack, getTitle(), getPos().getAdded(new Vector(2, getSize().getY() / 2 - DrawUtil.getTextHeight() / 2)), ColorE.WHITE);
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

    public SettingUI<Boolean> getSetting() {
        return setting;
    }

}
