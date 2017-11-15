package com.jamieswhiteshirt.wmtc.impl;

import com.jamieswhiteshirt.wmtc.api.IChunkInfo;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChunkInfoStorage implements Capability.IStorage<IChunkInfo> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IChunkInfo> capability, IChunkInfo instance, EnumFacing side) {
        NBTTagList stackTracesTag = new NBTTagList();
        for (List<StackTraceElement> stackTraceElements : instance.getStackTraces()) {
            NBTTagList stackTraceElementsTag = new NBTTagList();
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                NBTTagCompound stackTraceElementTag = new NBTTagCompound();
                stackTraceElementTag.setString("className", stackTraceElement.getClassName());
                stackTraceElementTag.setString("methodName", stackTraceElement.getMethodName());
                stackTraceElementTag.setString("fileName", stackTraceElement.getFileName());
                stackTraceElementTag.setInteger("lineNumber", stackTraceElement.getLineNumber());
                stackTraceElementsTag.appendTag(stackTraceElementTag);
            }
            stackTracesTag.appendTag(stackTraceElementsTag);
        }
        return stackTracesTag;
    }

    @Override
    public void readNBT(Capability<IChunkInfo> capability, IChunkInfo instance, EnumFacing side, NBTBase nbt) {
        if (nbt instanceof NBTTagList) {
            NBTTagList stackTracesTag = (NBTTagList) nbt;
            Set<List<StackTraceElement>> stackTraces = new HashSet<>();
            for (NBTBase nbt2 : stackTracesTag) {
                if (nbt2 instanceof NBTTagList) {
                    NBTTagList stackTraceElementsTag = (NBTTagList) nbt2;
                    StackTraceElement[] stackTraceElements = new StackTraceElement[stackTraceElementsTag.tagCount()];
                    for (int i = 0; i < stackTraceElements.length; i++) {
                        NBTTagCompound stackTraceElementTag = stackTraceElementsTag.getCompoundTagAt(i);
                        stackTraceElements[i] = new StackTraceElement(
                                stackTraceElementTag.getString("className"),
                                stackTraceElementTag.getString("methodName"),
                                stackTraceElementTag.getString("fileName"),
                                stackTraceElementTag.getInteger("lineNumber")
                        );
                    }
                    stackTraces.add(Arrays.asList(stackTraceElements));
                }
            }
            instance.setStackTraces(stackTraces);
        }
    }
}
