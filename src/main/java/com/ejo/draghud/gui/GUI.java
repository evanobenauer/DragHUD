package com.ejo.draghud.gui;

import com.ejo.draghud.DragHUD;
import com.ejo.draghud.event.EventRegistry;
import com.ejo.draghud.gui.element.elements.*;
import com.ejo.draghud.gui.element.GuiWidget;
import com.ejo.draghud.gui.element.elements.window.windows.*;
import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.Util;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.time.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

public class GUI extends Screen {

    private final ArrayList<GuiWidget> guiElementList = new ArrayList<>();

    // --------------------------------------------------------
    private final GuiButton saveButton = new GuiButton(this,"Save",new Vector(width-30,height-20),new Vector(30,20),new ColorE(0,100,175,200),(params) -> {
        DragHUD.getSettingManager().saveAll();
        Util.sendMessage("Settings Saved");
    });


    private final ArmorWindow armor = new ArmorWindow(this,new Vector(0,0));

    private final CoordinatesWindow coordinates = new CoordinatesWindow(this,new Vector(10,0));

    private final DirectionWindow direction = new DirectionWindow(this,new Vector(20,0));

    private final EntityListWindow entityList = new EntityListWindow(this,new Vector(70,0));

    private final EntityDataWindow entityData = new EntityDataWindow(this,new Vector(80,0));

    private final FPSWindow fps = new FPSWindow(this,new Vector(30,0));

    private final InventoryWindow inventory = new InventoryWindow(this,new Vector(40,0));

    private final SpeedWindow speed = new SpeedWindow(this,new Vector(50,0));

    private final TPSWindow tps = new TPSWindow(this,new Vector(60,0));


    // -----------------------------------------------------------------

    protected GUI(Component title) {
        super(title);
        addGuiElements(saveButton);
        addGuiElements(armor,direction,fps,inventory,speed);
        //addGuiElements(armor,coordinates,direction,entityData,entityList,fps,inventory,speed,tps);
        startAnimationThread();
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        Vector mousePos = new Vector(mouseX,mouseY);

        //DRAW BACKGROUND
        DrawUtil.drawRectangle(stack,Vector.NULL, getSize(), new ColorE(0,0,0,100));
        RenderSystem.setShaderColor(1,1,1,1);

        //MODIFY SAVE BUTTON
        Vector saveButtonPos = new Vector(getSize().getX()-this.saveButton.getSize().getX() - 2,getSize().getY()-this.saveButton.getSize().getY() - 2);
        this.saveButton.setPos(saveButtonPos);

        //DRAW ALL ELEMENTS
        for (GuiWidget element : getGuiElementList()) {
            if (!element.isDrawn()) continue;
            element.draw(stack, mousePos);
        }
    }

    @Override
    public boolean keyPressed(int key, int scancode, int modifiers) {
        boolean ret = super.keyPressed(key,scancode,modifiers);
        for (GuiWidget element : getGuiElementList()) {
            if (element.isDrawn()) {
                element.keyPressed(key,scancode,modifiers);
            }
        }
        return ret;
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        Vector mousePos = new Vector(x,y);
        for (GuiWidget element : getGuiElementList()) {
            if (element.isDrawn()) {
                element.mousePressed(button,1,mousePos);
            }
        }
        return super.mouseClicked(x, y, button);
    }

    @Override
    public boolean mouseReleased(double x, double y, int button) {
        Vector mousePos = new Vector(x,y);
        for (GuiWidget element : getGuiElementList()) {
            if (element.isDrawn()) {
                element.mousePressed(button,0,mousePos);
            }
        }
        return super.mouseReleased(x, y, button);
    }

    private void startAnimationThread() {
        Thread animationThread = new Thread(() -> {
            StopWatch watch = new StopWatch();
            watch.start();
            while (true) {
                if (watch.hasTimePassedMS(1)) {
                    try {
                        EventRegistry.EVENT_CALCULATE_ANIMATION.post();
                    } catch (ConcurrentModificationException e) {
                        //
                    }
                    watch.restart();
                }
            }
        });
        animationThread.setName("Animation Thread");
        animationThread.setDaemon(true);
        animationThread.start();
    }

    public void addGuiElements(GuiWidget... elements) {
        guiElementList.addAll(Arrays.asList(elements));
    }


    public Vector getSize() {
        return new Vector(this.width,this.height);
    }

    public ArrayList<GuiWidget> getGuiElementList() {
        return guiElementList;
    }

}
