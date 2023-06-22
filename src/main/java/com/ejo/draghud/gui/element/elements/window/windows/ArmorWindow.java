package com.ejo.draghud.gui.element.elements.window.windows;

import com.ejo.draghud.gui.element.elements.window.GuiWindow;
import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

public class ArmorWindow extends GuiWindow {

    public SettingWidget<String> mode;

    public ArmorWindow(Screen screen, Vector pos) {
        super(screen, "Armor",pos, new Vector(65,15));
        mode = new SettingWidget<>(this,"Mode","The direction the armor is rendered","Horizontal","Horizontal","Vertical");
    }

    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        boolean drawBar = Minecraft.getInstance().screen == getScreen();
        double xPos = getPos().getX();
        double yPos = getPos().getY() - 1;
        int i = 0;

        if (mode.get().equals("Horizontal")) {
            setSize(new Vector(65,15));
        }
        if (mode.get().equals("Vertical")) {
            xPos += 1;
            setSize(new Vector(18,63));
        }
        if (drawBar) {
            ResourceLocation helmet = new ResourceLocation("textures/item/empty_armor_slot_helmet.png");
            ResourceLocation chestplate = new ResourceLocation("textures/item/empty_armor_slot_chestplate.png");
            ResourceLocation leggings = new ResourceLocation("textures/item/empty_armor_slot_leggings.png");
            ResourceLocation boots = new ResourceLocation("textures/item/empty_armor_slot_boots.png");

            RenderSystem.setShaderColor(.2F,.2F,.2F,1F);
            double separation = 16;
            Vector texSize = new Vector(16,16);
            if (mode.get().equals("Horizontal")) {
                DrawUtil.drawTexturedRect(helmet, xPos, yPos, 16, 16);
                DrawUtil.drawTexturedRect(chestplate, xPos + separation, yPos, 16, 16);
                separation += 16;
                DrawUtil.drawTexturedRect(leggings, xPos + separation, yPos, 16, 16);
                separation += 16;
                DrawUtil.drawTexturedRect(boots, xPos + separation, yPos, 16, 16);
            }
            if (mode.get().equals("Vertical")) {
                DrawUtil.drawTexturedRect(helmet, xPos, yPos, 16, 16);
                DrawUtil.drawTexturedRect(chestplate, xPos, yPos + separation, 16, 16);
                separation += 16;
                DrawUtil.drawTexturedRect(leggings, xPos, yPos + separation, 16, 16);
                separation += 16;
                DrawUtil.drawTexturedRect(boots, xPos, yPos + separation, 16, 16);
            }

        }
        RenderSystem.setShaderColor(1F,1F,1F,1F);

        for (int j = 0; j < 4; j++) {
            ItemStack Istack = getArmorItem(Minecraft.getInstance().player, j);
            DrawUtil.drawText(graphics, "DEBUG", new Vector(-20,-20), ColorE.WHITE,true, 1);
            DrawUtil.drawRectangle(graphics, Vector.NULL,new Vector(-20,-20), ColorE.WHITE);

            enableItemRendering();
            if (mode.get().equals("Horizontal")) DrawUtil.drawItemStack(graphics,Istack, new Vector((xPos + 16 * i++), yPos));
            if (mode.get().equals("Vertical")) DrawUtil.drawItemStack(graphics,Istack, new Vector(xPos, yPos + 16 * i++));
            graphics.flush();
            disableItemRendering();

            /*
            if (HUD.armorEnchantments.getBoolean()) {
                double xI2 = xPos;
                double yI2 = yPos;
                if (armorDirection.equals("Horizontal")) {
                    xI2 = xPos + 3 + j * 16;
                    yI2 = yPos - 3;
                }
                if (armorDirection.equals("Vertical")) {
                    xI2 = xPos + 2 + 16;
                    yI2 = yPos + 16 - 6 + j * 16;
                }
                for (EnchantmentUtils.TranslatedEnchantment enchant : EnchantmentUtils.getEnchantmentsSorted(EnchantmentUtils.getEnchantmentTagList(stack))) {
                    TextUtils.drawText(FONT, enchant.getShortName(), xI2 - 1, yI2 - 1, true, Colors.WHITE, 0.5);
                    if ((yI2 -= 4.5) <= yI2) continue;
                }
            }
            //*/
        }
    }

    public static void enableItemRendering() {
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.disableColorLogicOp();
        RenderSystem.disableCull();
        RenderSystem.disableScissor();
    }

    public static void disableItemRendering() {
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.enableCull();
        RenderSystem.enablePolygonOffset();
    }

    public static ItemStack getArmorItem(Player Player, int slot) {
        return Player.inventoryMenu.getSlot(5 + slot).getItem();
    }
    
}
