package com.echoes.lastdawn.client;

import com.echoes.lastdawn.EchoesMod;
import com.echoes.lastdawn.entity.StoryKeeper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class StoryKeeperRenderer extends MobRenderer<StoryKeeper, StoryKeeperModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(EchoesMod.MODID, "textures/entity/story_keeper.png");

    public StoryKeeperRenderer(EntityRendererProvider.Context context) {
        super(context, new StoryKeeperModel(context.bakeLayer(StoryKeeperModel.LAYER_LOCATION)), 0.45F);
    }

    @Override
    public ResourceLocation getTextureLocation(StoryKeeper entity) {
        return TEXTURE;
    }
}
