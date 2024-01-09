package com.ejo.draghud.event.events;

import com.ejo.glowlib.event.EventE;
import net.minecraft.network.protocol.Packet;

public class PacketEvent extends EventE {

    private boolean cancelled = false;

    private Packet<?> packet;

    @Override
    public void post(Object... args) {
        packet = (Packet<?>) args[0];
        super.post(args);
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
