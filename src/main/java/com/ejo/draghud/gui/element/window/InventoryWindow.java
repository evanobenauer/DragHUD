package com.ejo.draghud.gui.element.window;

import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.opengl.GL11;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

import java.util.List;

import static com.ejo.draghud.DragHUD.MC;

public class InventoryWindow extends GuiWindow {

    public SettingWidget<Integer> transparency;
    public SettingWidget<Double> scale;

    public InventoryWindow(Screen screen, Vector pos) {
        super(screen,"Inventory" ,pos, new Vector(178,82));
        transparency = new SettingWidget<>(this,"Transparency","The transparency of the inventory window",125,0,255,1);
        scale = new SettingWidget<>(this,"Scale","The scale of the inventory window",1d,.25d,1d,.05d);
    }

    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        int itemSize = 16;

        //RenderSystem.setShaderColor(1, 1, 1, transparency.get() / 255f);
        graphics.setColor(1,1,1,transparency.get() / 255f);
        float scale = this.scale.get().floatValue();
        setSize(new Vector(178,82).getMultiplied(scale));
        graphics.pose().scale(scale,scale,1f);

        Vector adjustedPos = getPos().getAdded(1,1).getMultiplied(1/scale);

        renderChestInventory(graphics,"Inventory",adjustedPos);

        List<ItemStack> list = MC.player.inventoryMenu.getItems();

        int i = (list.size() % 9 == 0) ? 0 : 1 + list.size()/9;
        int size = list.size();
        for (int l = 0; l < size; l++) {
            if (l > 8 && l < 36) {
                Vector pos = new Vector(
                        (adjustedPos.getX() + 8) + ((itemSize + 2) * (l % 9)),
                        (adjustedPos.getY() - 126) + (itemSize + 2) * (l / 9 + 1) + (itemSize + 2) * i);
                DrawUtil.drawItemStack(graphics,list.get(l),pos);
            }
        }
        graphics.setColor(1f,1f,1f,1f);
        graphics.pose().scale(1 / scale,1 / scale,1f);
    }

    private void renderChestInventory(GuiGraphics graphics, String title, Vector pos) {
        //INFO: Width = 176, Height = 80
        double x = pos.getX();
        double y = pos.getY();
        ResourceLocation GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
        RenderSystem.enableBlend();
        DrawUtil.drawTexturedRect(graphics.pose(),GUI_TEXTURE,pos.getAdded(new Vector(0,74)),new Vector(176,6),new Vector(0,160), new Vector(256,256));
        DrawUtil.drawTexturedRect(graphics.pose(),GUI_TEXTURE,pos,new Vector(176,74),Vector.NULL, new Vector(256,256));
        DrawUtil.drawText(graphics, title, new Vector(x + 7, y + 5), new ColorE(175, 175, 175, 220));
        RenderSystem.disableBlend();
    }

}
