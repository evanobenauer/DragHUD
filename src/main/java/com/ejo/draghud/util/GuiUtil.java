package com.ejo.draghud.util;

import net.minecraft.client.Minecraft;

public class GuiUtil {

    public static int getScaledWidth() {
        return Minecraft.getInstance().getWindow().getGuiScaledWidth();
    }

    public static int getScaledHeight() {
        return Minecraft.getInstance().getWindow().getGuiScaledHeight();
    }

}
