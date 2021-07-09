package moe.exusiai.schwarz;

import moe.exusiai.schwarz.chunk.ChunkInit;
import net.fabricmc.api.ModInitializer;

public class Schwarz implements ModInitializer {

    @Override
    public void onInitialize() {
        ChunkInit.ChunkInit();
    }
}
