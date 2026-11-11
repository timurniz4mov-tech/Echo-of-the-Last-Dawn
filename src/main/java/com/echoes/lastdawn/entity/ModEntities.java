package com.echoes.lastdawn.entity;

import com.echoes.lastdawn.EchoesMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EchoesMod.MODID);

    public static final RegistryObject<EntityType<StoryKeeper>> STORY_KEEPER = ENTITY_TYPES.register(
            "story_keeper",
            () -> EntityType.Builder.of(StoryKeeper::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .build("echoes:story_keeper")
    );

    private ModEntities() {}

    public static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }
}
