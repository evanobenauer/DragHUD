package com.ejo.draghud.event.events;

import com.ejo.glowlib.event.EventE;
import net.minecraft.network.protocol.Packet;

public class PacketEvent extends EventE {

    private boolean cancelled = false;

    private Packet<?> packet;
    private Type type;

    @Override
    public void post(Object... args) {
        this.packet = (Packet<?>) args[0];
        this.type = (Type) args[1];
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

    public Type getType() {
        return type;
    }

    public enum Type {
        SEND,
        RECEIVE
    }

}
