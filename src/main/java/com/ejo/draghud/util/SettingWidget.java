package com.ejo.draghud.util;

import com.ejo.draghud.DragHUD;
import com.ejo.draghud.gui.element.window.GuiWindow;
import com.ejo.glowlib.setting.Setting;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingWidget<T> extends Setting<T> {

    private final GuiWindow window;

    private final String name;
    private final String description;

    private Double min;
    private Double max;
    private Double step;

    private T[] modes;

    private final boolean numbersOnly;

    public SettingWidget(GuiWindow window, String name, String description, T defaultValue, double min, double max, double step) {
        super(DragHUD.getSettingManager(), window.getTitle() + "_" + name, defaultValue);
        this.window = window;
        this.name = name;
        this.description = description;

        this.min = min;
        this.max = max;
        this.step = step;

        this.numbersOnly = false;
    }

    @SafeVarargs
    public SettingWidget(GuiWindow window, String name, String description, T defaultValue, T... modes) {
        super(DragHUD.getSettingManager(), window.getTitle() + "_" + name, defaultValue);
        this.window = window;
        this.name = name;
        this.description = description;

        this.modes = modes;

        this.numbersOnly = false;
    }

    public SettingWidget(GuiWindow window, String name, String description, String defaultValue, boolean numbersOnly) {
        super(DragHUD.getSettingManager(), window.getTitle() + "_" + name, (T)defaultValue);
        this.window = window;
        this.name = name;
        this.description = description;

        this.numbersOnly = numbersOnly;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public Double getStep() {
        return step;
    }

    public ArrayList<T> getModes() {
        try {
            return new ArrayList<>(Arrays.asList(modes));
        } catch (NullPointerException e) {
            return new ArrayList<>();
        }
    }

    public boolean isNumbersOnly() {
        return numbersOnly;
    }

    public GuiWindow getWindow() {
        return window;
    }

}
