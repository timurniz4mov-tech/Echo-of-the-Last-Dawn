package com.echoes.lastdawn.world;

import com.echoes.lastdawn.entity.ModEntities;
import com.echoes.lastdawn.entity.StoryKeeper;
import com.echoes.lastdawn.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Heightmap;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class ObservatoryGenerator {
    private ObservatoryGenerator() {}

    public static void create(ServerLevel level, ServerPlayer firstPlayer) {
        BlockPos ground = findGround(level, level.getSharedSpawnPos());
        int x = ground.getX();
        int y = ground.getY();
        int z = ground.getZ();
        BlockPos origin = new BlockPos(x, y, z);

        // A weathered 13x13 stone platform.
        for (int dx = -6; dx <= 6; dx++) {
            for (int dz = -6; dz <= 6; dz++) {
                BlockState floor = ((dx + dz) % 5 == 0) ? Blocks.CRACKED_STONE_BRICKS.defaultBlockState()
                        : Blocks.STONE_BRICKS.defaultBlockState();
                level.setBlock(origin.offset(dx, 0, dz), floor, 3);
            }
        }

        // Broken outer walls and four old corner pillars.
        for (int n = -5; n <= 5; n++) {
            if (Math.abs(n) > 1) {
                int wallHeight = (Math.abs(n) == 5 || n % 2 == 0) ? 2 : 1;
                for (int h = 1; h <= wallHeight; h++) {
                    level.setBlock(origin.offset(n, h, -5), Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), 3);
                    if (n != 0) level.setBlock(origin.offset(n, h, 5), Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), 3);
                    if (n != 0) level.setBlock(origin.offset(-5, h, n), Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), 3);
                    level.setBlock(origin.offset(5, h, n), Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), 3);
                }
            }
        }
        for (int dx : new int[]{-5, 5}) {
            for (int dz : new int[]{-5, 5}) {
                for (int h = 1; h <= 4; h++) {
                    level.setBlock(origin.offset(dx, h, dz), h == 4 ? Blocks.CHISELED_STONE_BRICKS.defaultBlockState() : Blocks.STONE_BRICKS.defaultBlockState(), 3);
                }
                level.setBlock(origin.offset(dx, 5, dz), Blocks.LANTERN.defaultBlockState(), 3);
            }
        }

        // The entrance arch faces the world spawn.
        for (int h = 1; h <= 3; h++) {
            level.setBlock(origin.offset(-1, h, -5), Blocks.POLISHED_DEEPSLATE.defaultBlockState(), 3);
            level.setBlock(origin.offset(1, h, -5), Blocks.POLISHED_DEEPSLATE.defaultBlockState(), 3);
        }
        level.setBlock(origin.offset(0, 4, -5), Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), 3);
        level.setBlock(origin.offset(-1, 3, -4), Blocks.CHAIN.defaultBlockState(), 3);
        level.setBlock(origin.offset(1, 3, -4), Blocks.CHAIN.defaultBlockState(), 3);

        // Central star altar with amethyst and an eerie light.
        level.setBlock(origin.offset(0, 1, 0), Blocks.POLISHED_DEEPSLATE.defaultBlockState(), 3);
        level.setBlock(origin.offset(0, 2, 0), Blocks.AMETHYST_BLOCK.defaultBlockState(), 3);
        level.setBlock(origin.offset(0, 3, 0), Blocks.PURPLE_STAINED_GLASS.defaultBlockState(), 3);
        level.setBlock(origin.offset(0, 4, 0), Blocks.SEA_LANTERN.defaultBlockState(), 3);
        level.setBlock(origin.offset(0, 5, 0), Blocks.PURPLE_STAINED_GLASS.defaultBlockState(), 3);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            level.setBlock(origin.relative(direction).above(1), Blocks.AMETHYST_BLOCK.defaultBlockState(), 3);
        }

        // A chest containing the three memories needed for the first conversation quest.
        BlockPos chestPos = origin.offset(3, 1, 0);
        level.setBlock(chestPos, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH), 3);
        BlockEntity blockEntity = level.getBlockEntity(chestPos);
        if (blockEntity instanceof ChestBlockEntity chest) {
            chest.setCustomName(Component.literal("Ящик забытых воспоминаний"));
            chest.setItem(0, new ItemStack(ModItems.MEMORY_SHARD.get(), 3));
            chest.setItem(1, new ItemStack(Items.AMETHYST_SHARD, 4));
            chest.setItem(2, new ItemStack(Items.GOLD_NUGGET, 9));
            chest.setChanged();
        }

        // A few ruined details keep the structure from looking like a perfect square.
        level.setBlock(origin.offset(-3, 1, 3), Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 3);
        level.setBlock(origin.offset(-3, 2, 3), Blocks.MOSSY_COBBLESTONE_WALL.defaultBlockState(), 3);
        level.setBlock(origin.offset(4, 1, -2), Blocks.CRACKED_STONE_BRICKS.defaultBlockState(), 3);
        level.setBlock(origin.offset(4, 2, -2), Blocks.COBWEB.defaultBlockState(), 3);
        level.setBlock(origin.offset(-4, 1, -2), Blocks.POTTED_DEAD_BUSH.defaultBlockState(), 3);

        StoryKeeper keeper = ModEntities.STORY_KEEPER.get().create(level);
        if (keeper != null) {
            keeper.moveTo(x + 0.5D, y + 1.0D, z + 2.5D, 180.0F, 0.0F);
            level.addFreshEntity(keeper);
        }

        level.sendParticles(ParticleTypes.END_ROD, x + 0.5D, y + 5.3D, z + 0.5D, 45, 0.45D, 0.8D, 0.45D, 0.025D);
        level.sendParticles(ParticleTypes.PORTAL, x + 0.5D, y + 3.0D, z + 0.5D, 80, 1.5D, 1.0D, 1.5D, 0.2D);
        level.playSound(null, origin, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.AMBIENT, 1.0F, 0.6F);
        firstPlayer.sendSystemMessage(Component.literal("§5Вдалеке звучит тихий звон... §7На краю долины появилась древняя обсерватория."));
    }

    private static BlockPos findGround(ServerLevel level, BlockPos center) {
        for (int radius = 0; radius <= 32; radius += 4) {
            for (int dx = -radius; dx <= radius; dx += 4) {
                for (int dz = -radius; dz <= radius; dz += 4) {
                    int px = center.getX() + dx;
                    int pz = center.getZ() + dz;
                    BlockPos surface = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, px, pz);
                    if (level.getFluidState(surface).isEmpty() && level.getBlockState(surface.below()).isSolidRender(level, surface.below())) {
                        return surface;
                    }
                }
            }
        }
        return level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, center.getX(), center.getZ());
    }
}
