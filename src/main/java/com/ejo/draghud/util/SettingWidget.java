package com.ejo.draghud.util;

import com.ejo.draghud.DragHUD;
import com.ejo.draghud.gui.element.elements.window.GuiWindow;
import org.util.glowlib.setting.SettingUI;

public class SettingWidget<T> extends SettingUI<T> {

    private final GuiWindow window;
    private final boolean numbersOnly;

    public SettingWidget(GuiWindow window, String name, String description, T defaultValue, double min, double max, double step) {
        super(DragHUD.getSettingManager(), window.getTitle() + "_" + name, name, description, defaultValue, min, max, step);
        this.window = window;
        this.numbersOnly = false;
    }

    public SettingWidget(GuiWindow window, String name, String description, T defaultValue, T... modes) {
        super(DragHUD.getSettingManager(), window.getTitle() + "_" + name, name, description, defaultValue, modes);
        this.window = window;
        this.numbersOnly = false;
    }

    public SettingWidget(GuiWindow window, String name, String description, String defaultValue, boolean numbersOnly) {
        super(DragHUD.getSettingManager(), window.getTitle() + "_" + name, name, description, (T)defaultValue);
        this.window = window;
        this.numbersOnly = numbersOnly;
    }

    public boolean isNumbersOnly() {
        return numbersOnly;
    }

    public GuiWindow getWindow() {
        return window;
    }

}
