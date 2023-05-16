package com.ejo.glowhud.gui.element.elements;

import com.ejo.glowhud.gui.element.GuiWidget;
import com.ejo.glowhud.util.DrawUtil;
import com.ejo.glowhud.util.SettingWidget;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.setting.Setting;
import org.util.glowlib.setting.SettingUI;
import org.util.glowlib.time.StopWatch;

public class GuiTextField extends GuiWidget {

    private final Setting<String> setting;
    private final String title;

    private final StopWatch cursorTimer = new StopWatch();
    private boolean typing;


    private GuiTextField(Screen screen, String title, SettingUI<String> setting, Vector pos, Vector size) {
        super(screen, pos, size, true);
        this.typing = false;
        this.setting = setting;
        this.title = title;
    }

    public GuiTextField(Screen screen, SettingUI<String> setting, Vector pos, Vector size) {
        this(screen,setting.getKey(),setting,pos,size);
    }

    public GuiTextField(Screen screen, SettingWidget<String> setting, Vector pos, Vector size) {
        this(screen,setting.getName(),setting,pos,size);
    }

    @Override
    protected void drawWidget(PoseStack stack, Vector mousePos) {
        DrawUtil.drawRectangle(stack,getPos(), getSize(), new ColorE(0,0,0,255));

        DrawUtil.drawRectangle(stack,
                getPos().getAdded(new Vector(2 + DrawUtil.getTextWidth(getTitle() + ": "), - 2)),
                getSize().getAdded(new Vector(- 4 - DrawUtil.getTextWidth(getTitle() + ": "),1-getSize().getY())),
                ColorE.WHITE);

        String msg = getTitle() + ": " + (isTyping() ? "\u00A7a" : "") + getSetting().get();
        if (isTyping()) {
            cursorTimer.start();
            if (cursorTimer.hasTimePassedS(1)) cursorTimer.restart();
            int alpha = cursorTimer.hasTimePassedMS(500) ? 255 : 0;
            double x = (DrawUtil.getTextWidth(msg) > getSize().getX() - 4)
                    ? getPos().getX() + getSize().getX() - 1
                    : (getPos().getX() + 3 + DrawUtil.getTextWidth(getTitle() + ": ") + DrawUtil.getTextWidth(getSetting().get()));
            DrawUtil.drawRectangle(stack, new Vector(x,getPos().getY() + 3),new Vector(1,getSize().getY() - 6),new ColorE(255,255,255,alpha));
        }

        DrawUtil.drawText(stack, msg, getPos().getAdded(new Vector(getPos().getX() + 2, getSize().getY() / 2 - DrawUtil.getTextHeight() / 2)), ColorE.WHITE);
    }

    @Override
    public void mousePressed(int button, int state, Vector mousePos) {
        if (button == 0 && state == 1) {
            if (isTyping()) setTyping(false);
            if (isMouseOver()) setTyping(true);
        }
    }

    @Override
    public void keyPressed(int key, int scancode, int modifiers) {
        String buttonText = getSetting().get();
        try {
            if (isTyping()) {
                if (key == GLFW.GLFW_KEY_ESCAPE || key == GLFW.GLFW_KEY_ENTER) {
                    setTyping(false);
                } else if (key == GLFW.GLFW_KEY_BACKSPACE) {
                    if (buttonText.length() > 0) buttonText = buttonText.substring(0, buttonText.length() - 1);
                } else if (key == GLFW.GLFW_KEY_SPACE) {
                    if (isKeyNumber(getSetting(), key)) buttonText = buttonText + " ";
                } else if (!GLFW.glfwGetKeyName(key, -1).equals("null")) {
                    if (GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) == 1 || GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_SHIFT) == 1) {
                        buttonText = buttonText + GLFW.glfwGetKeyName(key, -1).toUpperCase();
                    } else {
                        if (isKeyNumber(getSetting(), key)) buttonText = buttonText + GLFW.glfwGetKeyName(key, -1);
                    }
                }
            }
        } catch (Exception e) {
        }
        getSetting().set(buttonText);
    }


    public void setTyping(boolean typing) {
        this.typing = typing;
    }


    public static boolean isKeyNumber(Setting<?> setting, int key) {
        if (setting instanceof SettingWidget<?> settingw) {
            if (settingw.isNumbersOnly()) {
                if (key == GLFW.GLFW_KEY_PERIOD ||
                        key == GLFW.GLFW_KEY_KP_SUBTRACT ||
                        key == GLFW.GLFW_KEY_MINUS ||
                        key == GLFW.GLFW_KEY_0 ||
                        key == GLFW.GLFW_KEY_1 ||
                        key == GLFW.GLFW_KEY_2 ||
                        key == GLFW.GLFW_KEY_3 ||
                        key == GLFW.GLFW_KEY_4 ||
                        key == GLFW.GLFW_KEY_5 ||
                        key == GLFW.GLFW_KEY_6 ||
                        key == GLFW.GLFW_KEY_7 ||
                        key == GLFW.GLFW_KEY_8 ||
                        key == GLFW.GLFW_KEY_9 ||
                        key == GLFW.GLFW_KEY_KP_0 ||
                        key == GLFW.GLFW_KEY_KP_1 ||
                        key == GLFW.GLFW_KEY_KP_2 ||
                        key == GLFW.GLFW_KEY_KP_3 ||
                        key == GLFW.GLFW_KEY_KP_4 ||
                        key == GLFW.GLFW_KEY_KP_5 ||
                        key == GLFW.GLFW_KEY_KP_6 ||
                        key == GLFW.GLFW_KEY_KP_7 ||
                        key == GLFW.GLFW_KEY_KP_8 ||
                        key == GLFW.GLFW_KEY_KP_9) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public boolean isTyping() {
        return typing;
    }

    public String getTitle() {
        return title;
    }

    public Setting<String> getSetting() {
        return setting;
    }

}
