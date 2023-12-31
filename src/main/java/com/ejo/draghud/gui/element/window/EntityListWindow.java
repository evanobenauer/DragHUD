package com.ejo.draghud.gui.element.window;

import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import com.ejo.draghud.util.Util;
import com.ejo.glowlib.misc.ColorE;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import com.ejo.glowlib.math.Vector;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class EntityListWindow extends GuiWindow {

    private final SettingWidget<Boolean> label;
    private final SettingWidget<Integer> range;


    public EntityListWindow(Screen screen, Vector pos) {
        super(screen, "EntityList", pos, Vector.NULL);
        this.label = new SettingWidget<>(this,"Label","Show the label",true);
        this.range = new SettingWidget<>(this,"Range","Block range away from player of entities",64,1,200,1);
    }


    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        setSize(new Vector(72,12));
        assert Util.MC.level != null;
        int yOff = 0;
        LinkedHashMap<String, Integer> entityNameList = new LinkedHashMap<>();
        for (Entity entity : Util.MC.level.entitiesForRendering()) {
            if (entity.distanceTo(Util.MC.player) > range.get()) continue;
            if (entity instanceof LocalPlayer) continue;
            String entityName = entity.getClass().getSimpleName();
            if (entityNameList.containsKey(entityName)) {
                entityNameList.put(entityName,entityNameList.get(entityName) + 1);
                continue;
            }
            entityNameList.put(entityName,1);
        }

        if (label.get()) {
            DrawUtil.drawText(graphics,"Entity List:",getPos().getAdded(2,2), DrawUtil.HUD_LABEL);
            yOff += 12;
        }

        for (Map.Entry<String, Integer> set : entityNameList.entrySet()) {
            DrawUtil.drawDualColorText(graphics,set.getKey(),(set.getValue() == 1) ? "" : "(" + set.getValue() + ")",getPos().getAdded(2,2 + yOff), ColorE.WHITE,ColorE.BLUE.green(190));
            yOff += 12;
            setSize(new Vector(72, yOff));
        }
    }
}