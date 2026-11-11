package com.echoes.lastdawn;

import com.echoes.lastdawn.entity.ModEntities;
import com.echoes.lastdawn.item.ModCreativeTabs;
import com.echoes.lastdawn.item.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EchoesMod.MODID)
public class EchoesMod {
    public static final String MODID = "echoes";

    public EchoesMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntities.register(modBus);
        ModItems.register(modBus);
        ModCreativeTabs.register(modBus);
    }
}
