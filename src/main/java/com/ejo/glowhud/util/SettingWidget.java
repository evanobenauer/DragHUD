package com.ejo.glowhud.util;

import com.ejo.glowhud.GlowHUD;
import com.ejo.glowhud.gui.element.elements.window.GuiWindow;
import org.util.glowlib.setting.SettingUI;

public class SettingWidget<T> extends SettingUI<T> {

    private final GuiWindow window;
    private final String name;
    private final boolean numbersOnly;

    public SettingWidget(GuiWindow window, String name, String description, T defaultValue, double min, double max, double step) {
        super(GlowHUD.getSettingManager(), window.getTitle() + "_" + name, description, defaultValue, min, max, step);
        this.window = window;
        this.name = name;
        this.numbersOnly = false;
    }

    public SettingWidget(GuiWindow window, String name, String description, T defaultValue, T... modes) {
        super(GlowHUD.getSettingManager(), window.getTitle() + "_" + name, description, defaultValue, modes);
        this.window = window;
        this.name = name;
        this.numbersOnly = false;
    }

    public SettingWidget(GuiWindow window, String name, String description, String defaultValue, boolean numbersOnly) {
        super(GlowHUD.getSettingManager(), window.getTitle() + "_" + name, description, (T)defaultValue);
        this.window = window;
        this.name = name;
        this.numbersOnly = numbersOnly;
    }

    public boolean isNumbersOnly() {
        return numbersOnly;
    }

    public GuiWindow getWindow() {
        return window;
    }

    public String getName() {
        return name;
    }

}
