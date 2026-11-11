package com.echoes.lastdawn.events;

import com.echoes.lastdawn.EchoesMod;
import com.echoes.lastdawn.world.ObservatoryGenerator;
import com.echoes.lastdawn.world.StoryWorldData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EchoesMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class StoryEvents {
    private StoryEvents() {}

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!player.serverLevel().dimension().equals(Level.OVERWORLD)) return;

        StoryWorldData data = StoryWorldData.get(player.serverLevel());
        if (!data.isObservatoryGenerated()) {
            ObservatoryGenerator.create(player.serverLevel(), player);
            data.setObservatoryGenerated();
        }
    }
}
