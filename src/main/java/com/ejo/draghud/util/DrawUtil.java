package com.ejo.draghud.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

public class DrawUtil {

    public static void drawText(PoseStack stack, String text, Vector pos, ColorE color, boolean drawShadow, float scale) {
        float x = (float)pos.getX();
        float y = (float)pos.getY();
        stack.scale(scale, scale, 1);
        if (drawShadow) {
            Minecraft.getInstance().font.drawShadow(stack, text, x / scale, y / scale, color.getHash());
        } else {
            Minecraft.getInstance().font.draw(stack, text, x / scale, y / scale, color.getHash());
        }
    }

    public static void drawText(PoseStack stack, String text, Vector pos, ColorE color) {
        drawText(stack, text, pos, color, true, 1);
    }

    public static void drawDualColorText(PoseStack stack, String text1, String text2, Vector pos, ColorE color1, ColorE color2) {
        drawText(stack, text1, pos, color1);
        drawText(stack, text2, pos.getAdded(new Vector(getTextWidth(text1) + 1, 0)), color2);
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



    public static void drawRectangle(PoseStack stack, Vector pos, Vector size, ColorE color) {
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

        Matrix4f matrix = stack.last().pose();
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

    public static void drawTexturedRect(ResourceLocation texture, Vector pos, Vector size) {
        drawTexturedRect(new PoseStack(),texture,pos,size,Vector.NULL,size);
    }


    public static void drawItemStack(PoseStack poseStack, ItemStack stack, Vector pos) {
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        Minecraft.getInstance().getItemRenderer().renderGuiItem(poseStack, stack, x, y);
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(poseStack, Minecraft.getInstance().font, stack, x, y);
    }
}
