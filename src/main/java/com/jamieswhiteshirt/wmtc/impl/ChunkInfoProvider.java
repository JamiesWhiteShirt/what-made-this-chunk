package com.jamieswhiteshirt.wmtc.impl;

import com.jamieswhiteshirt.wmtc.api.IChunkInfo;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChunkInfoProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(IChunkInfo.class)
    private static final Capability<IChunkInfo> CAPABILITY = null;

    private final ChunkInfo instance;

    public ChunkInfoProvider(Chunk chunk) {
        instance = new ChunkInfo(chunk);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CAPABILITY ? CAPABILITY.cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return CAPABILITY.getStorage().writeNBT(CAPABILITY, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        CAPABILITY.getStorage().readNBT(CAPABILITY, instance, null, nbt);
    }
}
