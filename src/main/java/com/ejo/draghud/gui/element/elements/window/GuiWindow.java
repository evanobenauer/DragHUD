package com.ejo.draghud.gui.element.elements.window;

import com.ejo.draghud.DragHUD;
import com.ejo.draghud.gui.element.GuiWidget;
import com.ejo.draghud.gui.element.elements.window.windows.SettingWindow;
import com.ejo.draghud.util.Key;
import com.ejo.draghud.util.DrawUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;
import org.util.glowlib.math.Vector;
import org.util.glowlib.math.VectorMod;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.setting.Setting;
import org.util.glowlib.setting.SettingManager;

public abstract class GuiWindow extends GuiWidget {

    //TODO: Make Packet Inflow Window
    //TODO: Make Entity List Window

    private SettingWindow settingWindow;

    private final String title;

    private final Setting<Boolean> isPinned;

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
    }

    @Override
    public void drawWidget(PoseStack stack, Vector mousePos) {
        if (Minecraft.getInstance().screen == DragHUD.getGuiManager().getGui()) {
            if (isDragging()) setPos(mousePos.getAdded(getDragOffset()));

            if (getPos().getX() < 0)
                setPos(new Vector(0, getPos().getY()));
            if (getPos().getY() < 0)
                setPos(new Vector(getPos().getX(), 0));

            if (getPos().getX() + getSize().getX() > getScreen().width)
                setPos(new Vector(getScreen().width - getSize().getX(), getPos().getY()));
            if (getPos().getY() + getSize().getY() > getScreen().height)
                setPos(new Vector(getPos().getX(), getScreen().height - getSize().getY()));
        }
        //TODO: Add Width Anchoring Here (Dont put inside of the gui only if statement)

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        drawWindow(stack, mousePos);
        if (isSettingsOpen()) getSettingWindow().draw(stack, mousePos);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        if (Minecraft.getInstance().screen == DragHUD.getGuiManager().getGui() && shouldDrawPin()) {
            if (isPinned()) {
                DrawUtil.drawRectangle(stack, getPos(),new Vector(10,10), new ColorE(0,255,0,125));
            } else {
                DrawUtil.drawRectangle(stack, getPos(),new Vector(10,10), new ColorE(255,0,0,125));
            }
        }
    }


    protected abstract void drawWindow(PoseStack stack, Vector mousePos);


    @Override
    public void mousePressed(int button, int state, Vector mousePos) {
        if (isSettingsOpen()) {
            getSettingWindow().mousePressed(button, state, mousePos);
        }

        if (state == 1) {
            if (isMouseOver()) {
                if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                    getDragOffset().set(getPos().getAdded(mousePos.getMultiplied(-1)));
                    setDragging(true);

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

        if (isDragging() && state == 0) {
            setDragging(false);
        }

    }

    @Override
    public void keyPressed(int key, int scancode, int modifiers) {
        if (isSettingsOpen()) getSettingWindow().keyPressed(key,scancode,modifiers);
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
