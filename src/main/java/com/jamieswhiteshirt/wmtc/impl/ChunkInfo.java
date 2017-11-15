package com.jamieswhiteshirt.wmtc.impl;

import com.jamieswhiteshirt.wmtc.api.IChunkInfo;
import net.minecraft.world.chunk.Chunk;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ChunkInfo implements IChunkInfo {
    private final Chunk chunk;
    private CopyOnWriteArraySet<List<StackTraceElement>> stackTraces = new CopyOnWriteArraySet<>();

    public ChunkInfo(Chunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public Set<List<StackTraceElement>> getStackTraces() {
        return Collections.unmodifiableSet(stackTraces);
    }

    @Override
    public void setStackTraces(Set<List<StackTraceElement>> stackTraces) {
        this.stackTraces = new CopyOnWriteArraySet<>(stackTraces);
    }

    @Override
    public void addStackTrace(List<StackTraceElement> stackTrace) {
        if (stackTraces.add(stackTrace)) {
            chunk.markDirty();
        }
    }
}
