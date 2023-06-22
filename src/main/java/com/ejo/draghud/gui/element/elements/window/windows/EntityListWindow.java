package com.ejo.draghud.gui.element.elements.window.windows;

import com.ejo.draghud.gui.element.elements.window.GuiWindow;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import com.ejo.glowlib.math.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class EntityListWindow extends GuiWindow {

    public EntityListWindow(Screen screen, Vector pos) {
        super(screen, "EntityList", pos, Vector.NULL);
    }


    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        /*
        setSize(new Vector(73,10));

        double xv = getX() + 2;
        double yv = getY() + 2;
        if ((isPinned.getBoolean() || MC.currentScreen instanceof ClickGUI) && HUD.entityListEnabled.getBoolean())
            try {
                ArrayList<String> nameList = new ArrayList<>();
                Map<String, Integer> entityAmountList = Maps.newHashMap();
                int itemAmount = 0;
                int playerAmount = 0;
                int dogAmount = 0;
                int catAmount = 0;
                for (Entity entity : MC.world.getAllEntities()) {
                    if (!(entity.getDistance(MC.player) > HUD.entityListRadius.getInt())) {
                        if (!(entity instanceof PlayerEntity) && !(entity instanceof ItemEntity) && !(entity instanceof WolfEntity)) {
                            String name = entity.getName().getString();
                            if (!nameList.contains(name)) {
                                entityAmountList.put(name, 1);
                                nameList.add(entity.getName().getString());
                            } else entityAmountList.replace(name, entityAmountList.get(name) + 1);
                        } else if (entity instanceof ItemEntity) {
                            if (!nameList.contains("Item")) {
                                itemAmount = 1;
                                nameList.add("Item");
                            } else itemAmount += 1;
                            entityAmountList.put("Item", itemAmount);
                        } else if (entity instanceof PlayerEntity) {
                            if (!nameList.contains("Player")) {
                                playerAmount = 1;
                                nameList.add("Player");
                            } else playerAmount += 1;
                            entityAmountList.put("Player", playerAmount);
                            //This is because of brendan's dog/cat army
                        } else if (entity instanceof WolfEntity) {
                            if (!nameList.contains("Wolf")) {
                                dogAmount = 1;
                                nameList.add("Wolf");
                            } else dogAmount += 1;
                            entityAmountList.put("Wolf", dogAmount);
                        } else if (entity instanceof CatEntity) {
                            if (!nameList.contains("Cat")) {
                                catAmount = 1;
                                nameList.add("Cat");
                            } else catAmount += 1;
                            entityAmountList.put("Cat", catAmount);
                        }
                    }
                }
                if (HUD.entityListOrder.getMode().equals("ABC")) Collections.sort(nameList);
                if (HUD.entityListOrder.getMode().equals("Length")) nameList.sort(Comparator.comparing(MC.fontRenderer::getStringWidth).reversed());

                for (String name2 : nameList) {
                    if (HUD.entityListSide.getMode().equals("Left")) {
                        TextUtils.drawText(FONT, name2 + " \u00A7b(" + entityAmountList.get(name2) + ")", xv, yv, true, Colors.WHITE, 1);
                        if (HUD.entityListDirection.getMode().equals("Up")) {
                            yv -= 10;
                            setHeight(13);
                        }
                        if (HUD.entityListDirection.getMode().equals("Down")) {
                            yv += 10;
                            setHeight(getHeight() + 10);
                        }
                    }
                    if (HUD.entityListSide.getMode().equals("Right")) {
                        setWidth(73);
                        TextUtils.drawText(FONT, "\u00A7b(" + entityAmountList.get(name2) + ") \u00A7r\u00A7f" + name2, xv + getWidth() - TextUtils.getStringWidth("\u00A7b(" + entityAmountList.get(name2) + ") \u00A7r\u00A7f" + name2) - 6, yv, true, Colors.WHITE, 1);
                        if (HUD.entityListDirection.getMode().equals("Up")) {
                            yv -= 10;
                            setHeight(13);
                        }
                        if (HUD.entityListDirection.getMode().equals("Down")) {
                            yv += 10;
                            setHeight(getHeight() + 10);
                        }
                    }
                }
                try {
                    setWidth((int) (5 + TextUtils.getStringWidth(FONT, TextUtils.getLongestString(nameList), 1) + TextUtils.getStringWidth(FONT, " (" + entityAmountList.get(TextUtils.getLongestString(nameList)) + ")", 1)));
                    if (HUD.entityListSide.getMode().equals("Right")) setWidth(73);
                } catch (Exception e) {
                    setWidth(73);
                    setHeight(10);
                }
            } catch (Exception e) {
            }
        else {
            setSize(new Vector(73,12));
        }

        if (!HUD.entityListEnabled.getBoolean()) RenderUtils2D.drawRect(getX(),getY(),getWidth(),getHeight(),Colors.toRGBA(255,0,0,100));

         */
    }
}