package com.echoes.lastdawn.item;

import com.echoes.lastdawn.EchoesMod;
import com.echoes.lastdawn.entity.ModEntities;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EchoesMod.MODID);

    public static final RegistryObject<Item> ANCIENT_JOURNAL = ITEMS.register("ancient_journal",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MEMORY_SHARD = ITEMS.register("memory_shard",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STAR_KEY = ITEMS.register("star_key",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> STORY_KEEPER_SPAWN_EGG = ITEMS.register("story_keeper_spawn_egg",
            () -> new SpawnEggItem(ModEntities.STORY_KEEPER, 0x26364A, 0x8E7BD1, new Item.Properties()));

    private ModItems() {}

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
