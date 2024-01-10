package com.ejo.draghud.gui.element.window;

import com.ejo.draghud.DragHUD;
import com.ejo.draghud.event.EventRegistry;
import com.ejo.draghud.event.events.PacketEvent;
import com.ejo.draghud.gui.element.GuiWidget;
import com.ejo.draghud.util.DrawUtil;
import com.ejo.draghud.util.SettingWidget;
import com.ejo.glowlib.event.EventAction;
import com.ejo.glowlib.time.StopWatch;
import com.google.common.collect.EvictingQueue;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import net.minecraft.network.protocol.common.ClientboundKeepAlivePacket;
import net.minecraft.network.protocol.common.ClientboundPingPacket;
import net.minecraft.network.protocol.configuration.ClientboundRegistryDataPacket;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;
import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;

import java.util.Collections;
import java.util.List;
import java.util.Queue;

import static com.ejo.draghud.DragHUD.MC;

public class TPSWindow extends GuiWindow {

    private final SettingWidget<Boolean> label;
    private final StopWatch tpsWatch = new StopWatch();
    private int ticks = 0;
    private int tps;

    public TPSWindow(Screen screen, Vector pos) {
        super(screen, "TPS", pos, Vector.NULL);
        this.label = new SettingWidget<>(this, "Label","Show the label",true);
        packetEvent.subscribe();
    }



    @Override
    protected void drawWindow(GuiGraphics graphics, Vector mousePos) {
        tpsWatch.start();

        String label = (this.label.get() ? "TPS " : "");
        String text = String.valueOf(tps);

        setSize(new Vector((int) DrawUtil.getTextWidth(label + text) + 6,13));

        DrawUtil.drawDualColorText(graphics,label,text,getPos().getAdded(new Vector(2,2)),DrawUtil.HUD_LABEL,ColorE.WHITE);
    }

    public EventAction packetEvent = new EventAction(EventRegistry.EVENT_PACKET, () -> {
        PacketEvent event = EventRegistry.EVENT_PACKET;
        if (tpsWatch.hasTimePassedS(20)) {
            tps = ticks;
            ticks = 0;
            tpsWatch.restart();
        }
        if (event.getPacket() instanceof ClientboundSetTimePacket) {
            ticks++;
        }
    });
}
