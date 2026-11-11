package com.echoes.lastdawn.events;

import com.echoes.lastdawn.EchoesMod;
import com.echoes.lastdawn.entity.ModEntities;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Registers the attributes required by the Story Keeper mob. */
@Mod.EventBusSubscriber(modid = EchoesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModEntityEvents {
    private ModEntityEvents() {}

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        var attributes = Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .build();
        event.put(ModEntities.STORY_KEEPER.get(), attributes);
    }
}
