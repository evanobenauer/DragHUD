package com.ejo.draghud.mixin;


import com.ejo.draghud.event.EventRegistry;
import com.ejo.draghud.event.events.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class MixinConnection extends SimpleChannelInboundHandler<Packet<?>> {

    //Receive Packets
    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Connection;genericsFtw(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;)V"), cancellable = true)
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
        PacketEvent event = EventRegistry.EVENT_PACKET;
        event.post(packet);
        if (event.isCancelled()) {
            event.setCancelled(false);
            ci.cancel();
        }
    }

    //Send Packets
    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Connection;sendPacket(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;Z)V"), cancellable = true)
    private void send(Packet<?> packet, @Nullable PacketSendListener packetSendListener, boolean bl, CallbackInfo ci) {
        PacketEvent event = EventRegistry.EVENT_PACKET;
        event.post(packet);
        if (event.isCancelled()) {
            event.setCancelled(false);
            ci.cancel();
        }
    }

    @Shadow
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet) throws Exception {

    }


}
