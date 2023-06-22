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
import net.minecraft.world.item.ItemStack;
import org.lwjgl.opengl.GL11;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

import java.util.List;

public class InventoryWindow extends GuiWindow {

    public SettingWidget<Integer> transparency;

    public InventoryWindow(Screen screen, Vector pos) {
        super(screen,"Inventory" ,pos, new Vector(178,82));
        transparency = new SettingWidget<>(this,"Transparency","The transparency of the inventory window",125,0,255,1);
    }

    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        Vector adjust = new Vector(1,1);
        int itemSize = 16;

        RenderSystem.setShaderColor(1, 1, 1, transparency.get() / 255f);
        renderChestInventory(graphics,"Inventory",getPos().getAdded(adjust));

        List<ItemStack> list = Minecraft.getInstance().player.inventoryMenu.getItems();

        int i = (list.size() % 9 == 0) ? 0 : 1 + list.size()/9;
        int size = list.size();
        for (int l = 0; l < size; l++) {
            if (l > 8 && l < 36) {
                Vector pos = new Vector(
                        (getPos().getX() + adjust.getX() + 8) + ((itemSize + 2) * (l % 9)),
                        (getPos().getY() + adjust.getY() - 126) + (itemSize + 2) * (l / 9 + 1) + (itemSize + 2) * i);
                DrawUtil.drawItemStack(graphics,list.get(l),pos);
            }
        }
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.enableBlend();
    }

    private void renderChestInventory(GuiGraphics graphics, String title, Vector pos) {
        //INFO: Width = 176, Height = 80
        double x = pos.getX();
        double y = pos.getY();
        ResourceLocation GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
        GL11.glEnable(GL11.GL_BLEND);
        DrawUtil.drawTexturedRect(graphics.pose(),GUI_TEXTURE,pos.getAdded(new Vector(0,74)),new Vector(176,6),new Vector(0,160), new Vector(256,256));
        DrawUtil.drawTexturedRect(graphics.pose(),GUI_TEXTURE,pos,new Vector(176,74),Vector.NULL, new Vector(256,256));
        DrawUtil.drawText(graphics, title, new Vector(x + 7, y + 5), new ColorE(175, 175, 175, 220));
        GL11.glDisable(GL11.GL_BLEND);
    }

}
