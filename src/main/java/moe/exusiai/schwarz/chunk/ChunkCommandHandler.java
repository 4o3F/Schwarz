package moe.exusiai.schwarz.chunk;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.world.chunk.WorldChunk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChunkCommandHandler {
    private static List<ChunkStatus> scores = new ArrayList<ChunkStatus>();

    public static void AnalyseChunk(ServerCommandSource serverCommandSource) {
        List<WorldChunk> currentWorldChunkList = ChunkData.loadedChunks;
        scores.clear();
        for (WorldChunk chunk : currentWorldChunkList) {
            scores.add(new ChunkStatus(chunk));
        }

        for (ChunkStatus chunkStatus : scores) {
            chunkStatus.genScore();
        }
        Collections.sort(scores);

        serverCommandSource.sendFeedback(new LiteralText("Chunk Analyse Report \n"), false);
        for (int i = 0; i < 10; i++) {
            if (i >= scores.size()) {
                break;
            }
            ChunkStatus chunkStatus = scores.get(i);
            serverCommandSource.sendFeedback(chunkStatus.genText(), false);
        }
    }


}
