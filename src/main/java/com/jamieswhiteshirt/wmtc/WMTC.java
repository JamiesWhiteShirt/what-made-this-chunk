package com.jamieswhiteshirt.wmtc;

import com.jamieswhiteshirt.wmtc.api.IChunkInfo;
import com.jamieswhiteshirt.wmtc.impl.ChunkInfo;
import com.jamieswhiteshirt.wmtc.impl.ChunkInfoProvider;
import com.jamieswhiteshirt.wmtc.impl.ChunkInfoStorage;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

@Mod(
        modid = WMTC.MODID,
        name = WMTC.NAME,
        version = WMTC.VERSION,
        acceptedMinecraftVersions = "[1.12.2,1.13)",
        dependencies = "required-after:forge@[14.23.0.2499,)"
)
public class WMTC {
    public static final String MODID = "wmtc";
	public static final String NAME = "What Made This Chunk?";
    public static final String VERSION = "1.0";

    @CapabilityInject(IChunkInfo.class)
    private static final Capability<IChunkInfo> CHUNK_INFO_CAPABILITY = null;
    private static final ResourceLocation CHUNK_INFO_PROVIDER = new ResourceLocation(MODID, "chunk_info");

    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityManager.INSTANCE.register(IChunkInfo.class, new ChunkInfoStorage(), ChunkInfo.class);
    }

    @SubscribeEvent
    public void attachChunkCapabilities(AttachCapabilitiesEvent<Chunk> event) {
        event.addCapability(CHUNK_INFO_PROVIDER, new ChunkInfoProvider(event.getObject()));
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        IChunkInfo info = event.getChunk().getCapability(CHUNK_INFO_CAPABILITY, null);
        if (info != null) {
            info.addStackTrace(Arrays.asList(Thread.currentThread().getStackTrace()));
        }
    }
}
