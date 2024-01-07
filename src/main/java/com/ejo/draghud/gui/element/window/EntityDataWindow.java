package com.ejo.draghud.gui.element.window;

import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import com.ejo.glowlib.math.MathE;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.util.ColorUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import com.ejo.glowlib.math.Vector;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import static com.ejo.draghud.DragHUD.MC;

public class EntityDataWindow extends GuiWindow {

    private final SettingWidget<Boolean> label;

    public EntityDataWindow(Screen screen, Vector pos) {
        super(screen, "EntityData", pos, Vector.NULL);
        this.label = new SettingWidget<>(this,"Label","Show the label",true);
    }

    private String currentOwnerName;
    private Entity prevHoveredEntity;


    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        setSize(new Vector(100, 12));

        Entity entity = MC.crosshairPickEntity;

        int yOff = 0;

        if (label.get()) {
            DrawUtil.drawText(graphics, "Entity Data:", getPos().getAdded(2, 2), DrawUtil.HUD_LABEL);
            yOff += 12;
        }
        if (entity == null) return;

        if (entity instanceof LivingEntity living) {
            float healthPercent = living.getHealth() / living.getMaxHealth();
            ColorE healthColor = healthPercent <= 1 ? ColorUtil.getRedGreenScaledColor(healthPercent) : new ColorE(255, 255, 0);
            DrawUtil.drawDualColorText(graphics, "\"" + entity.getName().getString() + "\"", " (" + MathE.roundDouble(living.getHealth(), 2) + "/" + MathE.roundDouble(living.getMaxHealth(),2) + ")", getPos().getAdded(2, 2 + yOff), ColorE.WHITE, healthColor);
        } else {
            DrawUtil.drawText(graphics, "\"" + entity.getName().getString() + "\"", getPos().getAdded(2, 2 + yOff), ColorE.WHITE);
        }
        yOff += 12;
        DrawUtil.drawText(graphics, " " + entity.getType().toShortString(), getPos().getAdded(2, 2 + yOff), ColorE.WHITE);
        yOff += 12;
        if (entity instanceof AbstractHorse horse) {
            double speed = MathE.roundDouble(horse.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).getValue() * 42.16, 2);
            double jump = horse.getAttributes().getInstance(Attributes.JUMP_STRENGTH).getValue();
            double blockHeight = MathE.roundDouble(-0.1817584952 * Math.pow(jump, 3) + 3.689713992 * Math.pow(jump, 2) + 2.128599134 * jump - 0.343930367, 2);

            // Max Speed: 14.229m/s
            // Max Jump: 5.29m

            double speedPercent = (speed - (.1125 * 42.16)) / ((.3375 - .1125) * 42.16);
            double jumpPercent = (jump - .4) / (1 - .4);
            ColorE speedColor = ColorUtil.getRedGreenScaledColor(speedPercent);
            ColorE jumpColor = ColorUtil.getRedGreenScaledColor(jumpPercent);

            DrawUtil.drawDualColorText(graphics, " Speed: ", speed + " m/s", getPos().getAdded(2, 2 + yOff), ColorE.WHITE, speedColor);
            yOff += 12;
            DrawUtil.drawDualColorText(graphics, " Jump: ", blockHeight + " m", getPos().getAdded(2, 2 + yOff), ColorE.WHITE, jumpColor);
            yOff += 12;
        }

        UUID uuid = null;
        if (entity instanceof OwnableEntity e) uuid = e.getOwnerUUID();
        if (uuid != null) {
            if (entity != prevHoveredEntity) updateUsernameString(uuid);

            DrawUtil.drawText(graphics, " Owner: " + this.currentOwnerName, getPos().getAdded(2, 2 + yOff), ColorE.WHITE);
            yOff += 12;
        }

        this.prevHoveredEntity = entity;
        setSize(new Vector(100, yOff));
    }

    private void updateUsernameString(UUID uuid) {
        Thread thread = new Thread(() -> {
            try {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                String url = "https://api.mojang.com/user/profile/" + uuid.toString();
                HttpGet httpGet = new HttpGet(url);
                HttpResponse response = httpClient.execute(httpGet);
                String jsonString = EntityUtils.toString(response.getEntity());
                this.currentOwnerName = (String)new JSONObject(jsonString).get("name");
            } catch (IOException | JSONException e) {
                this.currentOwnerName = uuid.toString();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

}