package com.ejo.draghud.gui.element.window;

import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import com.ejo.glowlib.misc.ColorE;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import com.ejo.glowlib.math.Vector;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.ejo.draghud.DragHUD.MC;

public class EntityListWindow extends GuiWindow {

    private final SettingWidget<Boolean> label;
    private final SettingWidget<Integer> range;
    private final SettingWidget<Double> scale;


    public EntityListWindow(Screen screen, Vector pos) {
        super(screen, "EntityList", pos, Vector.NULL);
        this.label = new SettingWidget<>(this,"Label","Show the label",true);
        this.range = new SettingWidget<>(this,"Range","Block range away from player of entities",64,1,200,1);
        this.scale = new SettingWidget<>(this,"Scale","The scale of the armor",1d,.25d,1d,.05d);
    }


    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        assert MC.level != null;
        int yOff = 0;
        LinkedHashMap<String, Integer> entityNameList = new LinkedHashMap<>();
        for (Entity entity : MC.level.entitiesForRendering()) {
            if (entity.distanceTo(MC.player) > range.get()) continue;
            if (entity instanceof LocalPlayer) continue;
            String rawName = entity.getType().toShortString();
            String entityName = rawName.replaceFirst(String.valueOf(rawName.charAt(0)), String.valueOf(rawName.charAt(0)).toUpperCase()).replace("_"," ");
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

        float scale = this.scale.get().floatValue();
        graphics.pose().scale(scale,scale,1f);
        for (Map.Entry<String, Integer> set : entityNameList.entrySet()) {
            DrawUtil.drawDualColorText(graphics,set.getKey(),(set.getValue() == 1) ? "" : "(" + set.getValue() + ")",getPos().getAdded(2,2 + yOff).getMultiplied(1 / scale), ColorE.WHITE,ColorE.BLUE.green(190));
            yOff += 12 * scale;
        }
        setSize(new Vector(72 * scale, yOff));
        graphics.pose().scale(1 / scale,1 / scale,1f);
    }
}