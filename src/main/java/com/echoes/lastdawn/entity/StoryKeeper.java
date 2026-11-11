package com.echoes.lastdawn.entity;

import com.echoes.lastdawn.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

public class StoryKeeper extends PathfinderMob {
    public StoryKeeper(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.setPersistenceRequired();
        this.setCustomName(Component.translatable("entity.echoes.story_keeper"));
        this.setCustomNameVisible(true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.35D));
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        if (!this.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
            int stage = player.getPersistentData().getInt("echoes_story_stage");

            if (stage == 0) {
                player.getPersistentData().putInt("echoes_story_stage", 1);
                give(player, ModItems.ANCIENT_JOURNAL.get());
                speak(player, "Ты всё-таки нашёл это место... Я — последний Хранитель обсерватории. Возьми дневник. Он помнит то, что забыли люди.");
                serverPlayer.playNotifySound(SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.NEUTRAL, 0.8F, 0.75F);
            } else if (stage == 1) {
                int fragments = player.getInventory().countItem(ModItems.MEMORY_SHARD.get());
                if (fragments >= 3) {
                    removeItems(player, ModItems.MEMORY_SHARD.get(), 3);
                    player.getPersistentData().putInt("echoes_story_stage", 2);
                    give(player, ModItems.STAR_KEY.get());
                    speak(player, "Три воспоминания... Значит, зал ещё помнит наше имя. Это звёздный ключ. Он понадобится тебе, когда ты доберёшься до Звёздного зала. Путь туда ещё предстоит найти.");
                    if (this.level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.PORTAL, this.getX(), this.getY() + 1.2D, this.getZ(), 70, 0.8D, 0.8D, 0.8D, 0.25D);
                        serverLevel.sendParticles(ParticleTypes.END_ROD, this.getX(), this.getY() + 1.0D, this.getZ(), 25, 0.45D, 0.7D, 0.45D, 0.04D);
                        serverLevel.playSound(null, this.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.AMBIENT, 0.8F, 0.65F);
                    }
                } else {
                    speak(player, "В сундуке рядом лежат три осколка памяти. Принеси мне все три. Не торопись: иногда прошлое прячется на самом виду. Сейчас у тебя " + fragments + " из 3.");
                }
            } else {
                speak(player, "Звёздный зал ждёт. Ключ у тебя, но не всякая дверь открывается одной силой. Это только начало.");
            }
        }

        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    private static void speak(Player player, String message) {
        player.sendSystemMessage(Component.literal("[Хранитель] ").withStyle(ChatFormatting.DARK_PURPLE)
                .append(Component.literal(message).withStyle(ChatFormatting.GRAY)));
    }

    private static void give(Player player, Item item) {
        ItemStack stack = new ItemStack(item);
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }

    private static void removeItems(Player player, Item item, int amount) {
        int left = amount;
        for (int slot = 0; slot < player.getInventory().getContainerSize() && left > 0; slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.is(item)) {
                int taken = Math.min(left, stack.getCount());
                stack.shrink(taken);
                left -= taken;
            }
        }
    }
}
