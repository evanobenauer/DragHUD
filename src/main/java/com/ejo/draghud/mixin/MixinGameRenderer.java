package com.ejo.draghud.mixin;

import com.ejo.draghud.event.EventRegistry;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {

    @Inject(method = "render", at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/Gui;render(Lnet/minecraft/client/gui/GuiGraphics;F)V"), cancellable = true)
    private void onRender(CallbackInfo ci) {
        EventRegistry.EVENT_RENDER_HUD.post();
    }
}
