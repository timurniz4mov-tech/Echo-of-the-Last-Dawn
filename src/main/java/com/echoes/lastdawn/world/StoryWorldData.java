package com.echoes.lastdawn.world;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class StoryWorldData extends SavedData {
    private static final String DATA_NAME = "echoes_last_dawn_story";
    private boolean observatoryGenerated;

    public static StoryWorldData load(CompoundTag tag) {
        StoryWorldData data = new StoryWorldData();
        data.observatoryGenerated = tag.getBoolean("ObservatoryGenerated");
        return data;
    }

    public static StoryWorldData get(net.minecraft.server.level.ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(StoryWorldData::load, StoryWorldData::new, DATA_NAME);
    }

    public boolean isObservatoryGenerated() {
        return observatoryGenerated;
    }

    public void setObservatoryGenerated() {
        this.observatoryGenerated = true;
        this.setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putBoolean("ObservatoryGenerated", observatoryGenerated);
        return tag;
    }
}
