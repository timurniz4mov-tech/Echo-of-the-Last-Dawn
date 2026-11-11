package com.echoes.lastdawn.item;

import com.echoes.lastdawn.EchoesMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, EchoesMod.MODID);

    public static final RegistryObject<CreativeModeTab> ECHOES_TAB = TABS.register("echoes_tab", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.echoes"))
                    .icon(() -> new ItemStack(ModItems.MEMORY_SHARD.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.ANCIENT_JOURNAL.get());
                        output.accept(ModItems.MEMORY_SHARD.get());
                        output.accept(ModItems.STAR_KEY.get());
                        output.accept(ModItems.STORY_KEEPER_SPAWN_EGG.get());
                    })
                    .build());

    private ModCreativeTabs() {}

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
