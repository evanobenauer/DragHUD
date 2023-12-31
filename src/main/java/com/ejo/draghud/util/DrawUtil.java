package com.ejo.draghud.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.DisplayRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

import java.util.logging.Level;

public class DrawUtil {

    public static ColorE HUD_BLUE = new ColorE(0,125,200);
    public static ColorE HUD_LABEL = new ColorE(190,190,190,215);

    public static void drawText(GuiGraphics graphics, String text, Vector pos, ColorE color, boolean drawShadow, float scale) {
        float x = (float) pos.getX();
        float y = (float) pos.getY();
        PoseStack stack = graphics.pose();
        stack.scale(scale,scale,1);
        Util.MC.font.drawInBatch(text, x*1/scale, y*1/scale, color.getHash(), drawShadow, stack.last().pose(), graphics.bufferSource(), Font.DisplayMode.SEE_THROUGH, 0, 15728880, Util.MC.font.isBidirectional());
        stack.scale(1/scale,1/scale,1);
        graphics.flush();
    }

    public static void drawText(GuiGraphics graphics, String text, Vector pos, ColorE color) {
        drawText(graphics, text, pos, color, true, 1);
    }

    public static void drawDualColorText(GuiGraphics graphics, String text1, String text2, Vector pos, ColorE color1, ColorE color2) {
        drawText(graphics, text1, pos, color1);
        drawText(graphics, text2, pos.getAdded(new Vector(getTextWidth(text1) + 1, 0)), color2);
    }


    public static double getTextWidth(String text, double scale) {
        return Minecraft.getInstance().font.width(text) * scale;
    }

    public static double getTextWidth(String text) {
        return getTextWidth(text,1);
    }

    public static double getTextHeight(double scale) {
        return Minecraft.getInstance().font.lineHeight * scale;
    }

    public static double getTextHeight() {
        return getTextHeight(1);
    }


    public static void drawRectangle(GuiGraphics graphics, Vector pos, Vector size, ColorE color) {
        float x = (float)pos.getX();
        float y = (float)pos.getY();
        float width = (float)size.getX();
        float height = (float)size.getY();

        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Matrix4f matrix = graphics.pose().last().pose();
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();

        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.vertex(matrix, x, y + height, 0).color(r, g, b, a).endVertex();
        bufferBuilder.vertex(matrix, x + width, y + height, 0).color(r, g, b, a).endVertex();
        bufferBuilder.vertex(matrix, x + width, y, 0).color(r, g, b, a).endVertex();
        bufferBuilder.vertex(matrix, x,  y, 0).color(r, g, b, a).endVertex();
        BufferBuilder.RenderedBuffer buff = bufferBuilder.end();
        BufferUploader.drawWithShader(buff);

        RenderSystem.disableBlend();
    }

    public static void drawTexturedRect(PoseStack stack, ResourceLocation texture, Vector pos, Vector size, Vector texturePos, Vector textureSize) {
        float x = (float)pos.getX();
        float y = (float)pos.getY();
        float width = (float)size.getX();
        float height = (float)size.getY();

        float texX = (float)texturePos.getX();
        float texY = (float)texturePos.getY();
        float texWidth = (float)textureSize.getX();
        float texHeight = (float)textureSize.getY();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);

        Matrix4f matrix = stack.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();

        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        bufferbuilder.vertex(matrix, x, y + height, 0)
                .uv(texX / texWidth, (texY + height) / texHeight).endVertex();

        bufferbuilder.vertex(matrix, x + width, y + height, 0)
                .uv((texX + width) / texWidth, (texY + height) / texHeight).endVertex();

        bufferbuilder.vertex(matrix, x + width,  y, 0)
                .uv((texX + width) / texWidth, texY / texHeight).endVertex();

        bufferbuilder.vertex(matrix, x, y, 0)
                .uv(texX / texWidth, texY / texHeight).endVertex();

        Tesselator.getInstance().end();
    }

    public static void drawTexturedRect(ResourceLocation texture, double x, double y, double width, double height) {
        drawTexturedRect(new PoseStack(),texture,new Vector(x,y),new Vector(width,height),new Vector(0,0),new Vector(width,height));
    }

    public static void drawTexturedRect(GuiGraphics graphics, ResourceLocation texture, Vector pos, Vector size) {
        drawTexturedRect(graphics.pose(),texture,pos,size,Vector.NULL,size);
    }


    public static void drawItemStack(GuiGraphics graphics, ItemStack stack, Vector pos) {
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        graphics.renderItem(stack,x,y);
        graphics.renderItemDecorations(Util.MC.font,stack,x,y);
    }

    public static void scaleStart(PoseStack stack, float scale) {
        stack.scale(scale,scale,1);
    }
}
