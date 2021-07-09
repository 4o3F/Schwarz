package moe.exusiai.schwarz.chunk;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.world.chunk.WorldChunk;

import java.util.Iterator;
import java.util.List;

public class ChunkInit {
    public static void ChunkInit() {
        ServerChunkEvents.CHUNK_LOAD.register(((world, chunk) -> {
            ChunkData.loadedChunks.add(chunk);

        }));
        ServerChunkEvents.CHUNK_UNLOAD.register(((world, chunk) -> {
            List<WorldChunk> loadedChunks = ChunkData.loadedChunks;
            Iterator iterator = loadedChunks.iterator();
            while (iterator.hasNext()) {
                WorldChunk loadedchunk = (WorldChunk) iterator.next();
                if (loadedchunk.getPos().equals(chunk.getPos())) {
                    iterator.remove();
                }
            }
            ChunkData.loadedChunks = loadedChunks;
        }));
    }
}
