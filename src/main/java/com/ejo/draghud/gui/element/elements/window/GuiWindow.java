package com.ejo.draghud.gui.element.elements.window;

import com.ejo.draghud.DragHUD;
import com.ejo.draghud.gui.element.GuiWidget;
import com.ejo.draghud.gui.element.elements.window.windows.SettingWindow;
import com.ejo.draghud.util.Key;
import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import com.ejo.draghud.util.Util;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.math.VectorMod;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.setting.Setting;
import com.ejo.glowlib.setting.SettingManager;
import com.ejo.glowlib.time.StopWatch;

public abstract class GuiWindow extends GuiWidget {

    //TODO: Make Packet Inflow Window
    //TODO: Make Entity List Window

    private SettingWindow settingWindow;

    private final String title;

    private final Setting<Boolean> isPinned;
    private final Setting<String> anchorX;
    private final Setting<String> anchorY;

    private boolean isDragging;
    private final VectorMod dragOffset;

    private boolean settingsOpen;


    public GuiWindow(Screen screen, String title, Vector pos, Vector size) {
        super(screen, pos, size,true);
        this.title = title;
        this.dragOffset = new VectorMod(Vector.NULL);
        this.settingsOpen = false;

        SettingManager manager = this instanceof SettingWindow ? SettingManager.getDefaultManager() : DragHUD.getSettingManager();
        this.pos = new Setting<>(manager, title + "_pos",pos);
        this.isPinned = new Setting<>(manager, title + "_pinned",false);
        this.anchorX = new Setting<>(manager,title + "_anchorX","NONE");
        this.anchorY = new Setting<>(manager,title + "_anchorY","NONE");
    }
    
    @Override
    public void drawWidget(GuiGraphics graphics, Vector mousePos) {
        if (Minecraft.getInstance().screen == DragHUD.getGuiManager().getGui()) {
            if (isDragging()) setPos(mousePos.getAdded(getDragOffset()));

            //Force HUD elements on screen
            if (getPos().getX() < 0) {
                setPos(new Vector(0, getPos().getY()));
            }
            if (getPos().getY() < 0) {
                setPos(new Vector(getPos().getX(), 0));
            }
            if (getPos().getX() + getSize().getX() > getScreen().width) {
                setPos(new Vector(getScreen().width - getSize().getX(), getPos().getY()));
            }
            if (getPos().getY() + getSize().getY() > getScreen().height) {
                setPos(new Vector(getPos().getX(), getScreen().height - getSize().getY()));
            }

            //Draw borderlines
            if (getPos().getX() <= 0 && anchorX.get().equals("L")) {
                DrawUtil.drawRectangle(graphics,getPos(),new Vector(1,getSize().getY()),ColorE.RED);
            }
            if (getPos().getX() + getSize().getX() >= getScreen().width && anchorX.get().equals("R")) {
                DrawUtil.drawRectangle(graphics,getPos().getAdded(getSize().getX() - 1,0),new Vector(1,getSize().getY()),ColorE.RED);
            }
            if (getPos().getY() <= 0 && anchorY.get().equals("U")) {
                DrawUtil.drawRectangle(graphics,getPos(),new Vector(getSize().getX(),1),ColorE.RED);
            }
            if (getPos().getY() + getSize().getY() >= getScreen().height && anchorY.get().equals("D")) {
                DrawUtil.drawRectangle(graphics,getPos().getAdded(0,getSize().getY() - 1),new Vector(getSize().getX(),1),ColorE.RED);
            }
        }

        setAnchorCoordinates();

        //TODO: Add width/height scaling to anchored objects based on their orientation for easy window resizing
        // Maybe don't? Anchoring is good for simply that, anchoring. It doesn't necessarily need scaling

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        drawWindow(graphics, mousePos);
        if (isSettingsOpen()) getSettingWindow().draw(graphics, mousePos);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);


        if (Minecraft.getInstance().screen == DragHUD.getGuiManager().getGui() && shouldDrawPin()) {
            if (isPinned()) {
                DrawUtil.drawRectangle(graphics, getPos(),new Vector(10,10), new ColorE(0,255,0,125));
            } else {
                DrawUtil.drawRectangle(graphics, getPos(),new Vector(10,10), new ColorE(255,0,0,125));
            }
        }

    }

    //This method will be called in the set position method every time the position is set
    public void setAnchorCoordinates() {
        if (anchorX.get().equals("NONE") && anchorY.get().equals("NONE")) return;
        //Set Anchor Coordinates
        if (anchorX.get().equals("L"))
            setPos(new Vector(0, getPos().getY()));
        else if (anchorX.get().equals("R"))
            setPos(new Vector(Util.getScaledWidth() - getSize().getX(), getPos().getY()));

        if (anchorY.get().equals("U"))
            setPos(new Vector(getPos().getX(), 0));
        else if (anchorY.get().equals("D"))
            setPos(new Vector(getPos().getX(), Util.getScaledHeight() - getSize().getY()));
    }


    protected abstract void drawWindow(GuiGraphics graphics, Vector mousePos);


    @Override
    public void mousePressed(int button, int state, Vector mousePos) {
        if (button == 0 && !Key.KEY_LSHIFT.isKeyDown()) {
            if (isMouseOver() && state == 1) {
                anchorX.set("NONE");
                anchorY.set("NONE");
            } else {
                //Set if anchored
                if (getPos().getX() <= 0) {
                    if (isDragging()) anchorX.set("L");
                } else if (getPos().getX() + getSize().getX() >= getScreen().width) {
                    if (isDragging()) anchorX.set("R");
                }
                if (getPos().getY() <= 0) {
                    if (isDragging()) anchorY.set("U");
                } else if (getPos().getY() + getSize().getY() >= getScreen().height) {
                    if (isDragging()) anchorY.set("D");
                }
            }
        }

        if (state == 1) {
            if (isMouseOver()) {
                if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                    getDragOffset().set(getPos().getAdded(mousePos.getMultiplied(-1)));
                    if (this instanceof SettingWindow || !Key.KEY_LSHIFT.isKeyDown()) setDragging(true);

                    //If the window is not focused, it will not be able to detect if shift is down
                    if (!isSettingsOpen() && Key.KEY_LSHIFT.isKeyDown() && !(this instanceof SettingWindow)) {
                        this.settingWindow = new SettingWindow(getScreen(), this, new Vector(getScreen().width / 2f - 100 / 2f, getScreen().height / 2f - 100 / 2f)) {
                            @Override
                            public boolean shouldDrawPin() {
                                return false;
                            }
                        };
                        setSettingsOpen(true);
                    }
                }
                if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                    setPinned(!isPinned());
                }
            }
        }

        if (isSettingsOpen()) {
            getSettingWindow().mousePressed(button, state, mousePos);
        }

        if (isDragging() && state == 0) {
            setDragging(false);
        }
    }

    @Override
    public void keyPressed(int key, int scancode, int modifiers) {
        if (isSettingsOpen()) getSettingWindow().keyPressed(key,scancode,modifiers);
    }

    @Override
    public void setSize(Vector vector) {
        super.setSize(vector);
        setAnchorCoordinates();
    }

    public boolean shouldDrawPin() {
        return true;
    }


    public void setSettingsOpen(boolean settingsOpen) {
        this.settingsOpen = settingsOpen;
    }

    public void setPinned(boolean pinned) {
        isPinned.set(pinned);
    }

    public void setDragging(boolean dragging) {
        isDragging = dragging;
    }


    public boolean isSettingsOpen() {
        return settingsOpen;
    }

    public boolean isPinned() {
        return isPinned.get();
    }

    public boolean isDragging() {
        return isDragging;
    }

    public VectorMod getDragOffset() {
        return dragOffset;
    }

    public String getTitle() {
        return title;
    }

    public SettingWindow getSettingWindow() {
        return settingWindow;
    }

}
