package com.ejo.glowhud.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class Util {

    public static String prefix = "[GlowHUD] ";

    public static void sendMessage(String message) {
        try {
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable(prefix + message));
        } catch (Exception e) {
            System.out.println("Could Not Send Message!");
        }
    }

    public static void sendConsoleMessage(String message) {
        System.out.println(prefix + message);
    }

}
