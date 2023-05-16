package com.ejo.glowhud.gui.element.elements.window.windows;

import com.ejo.glowhud.gui.element.elements.window.GuiWindow;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.util.glowlib.math.Vector;

public class EntityDataWindow extends GuiWindow {

    public EntityDataWindow(Screen screen, Vector pos) {
        super(screen, "EntityData", pos, Vector.NULL);
    }


    @Override
    protected void drawWindow(PoseStack stack, Vector mousePos) {
        setSize(new Vector(100,12));
        //TODO: Add this
    }
}