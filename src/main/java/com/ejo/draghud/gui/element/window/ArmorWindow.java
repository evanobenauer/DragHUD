package com.ejo.draghud.gui.element.window;

import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import com.ejo.glowlib.math.VectorMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import com.ejo.glowlib.math.Vector;

public class ArmorWindow extends GuiWindow {

    public SettingWidget<String> mode;
    public SettingWidget<Double> scale;

    public ArmorWindow(Screen screen, Vector pos) {
        super(screen, "Armor",pos, new Vector(65,15));
        this.mode = new SettingWidget<>(this,"Mode","The direction the armor is rendered","Horizontal","Horizontal","Vertical");
        this.scale = new SettingWidget<>(this,"Scale","The scale of the armor",1d,.25d,1d,.05d);
    }

    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        VectorMod nPos = new VectorMod((int)getPos().getX(),(int)getPos().getY());

        float scale = this.scale.get().floatValue();
        setSize(new Vector(178,82).getMultiplied(scale));
        graphics.pose().scale(scale,scale,1f);

        Vector texSize = new Vector(16,16);
        Vector addVec = Vector.NULL;
        switch (mode.get()) {
            case "Horizontal" -> {
                setSize(new Vector(65,16).getMultiplied(scale));
                addVec = new Vector(texSize.getX(),0);
            }
            case "Vertical" -> {
                setSize(new Vector(17,63).getMultiplied(scale));
                nPos.add(new Vector(1,0));
                addVec = new Vector(0,texSize.getY());
            }
        }

        nPos.multiply(1 / scale);

        if (Minecraft.getInstance().screen == getScreen()) {
            ResourceLocation helmet = new ResourceLocation("textures/item/empty_armor_slot_helmet.png");
            ResourceLocation chestplate = new ResourceLocation("textures/item/empty_armor_slot_chestplate.png");
            ResourceLocation leggings = new ResourceLocation("textures/item/empty_armor_slot_leggings.png");
            ResourceLocation boots = new ResourceLocation("textures/item/empty_armor_slot_boots.png");

            RenderSystem.setShaderColor(.2F,.2F,.2F,1F);

            DrawUtil.drawTexturedRect(graphics,helmet, nPos, texSize);
            nPos.add(addVec);
            DrawUtil.drawTexturedRect(graphics,chestplate, nPos, texSize);
            nPos.add(addVec);
            DrawUtil.drawTexturedRect(graphics,leggings, nPos, texSize);
            nPos.add(addVec);
            DrawUtil.drawTexturedRect(graphics,boots, nPos, texSize);

            RenderSystem.setShaderColor(1F,1F,1F,1F);
            nPos.add(addVec.getMultiplied(-3));
        }

        for (int j = 0; j < 4; j++) {
            ItemStack Istack = getArmorItem(Minecraft.getInstance().player, j);
            enableItemRendering();
            DrawUtil.drawItemStack(graphics,Istack,nPos);
            nPos.add(addVec);
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
        graphics.pose().scale(1 / scale,1 / scale,1f);
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
