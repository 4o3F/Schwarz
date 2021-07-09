package moe.exusiai.schwarz.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import moe.exusiai.schwarz.chunk.ChunkCommandHandler;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class Command {
    public static void registerCommand(CommandDispatcher dispatcher) {
        final LiteralArgumentBuilder<ServerCommandSource> builder = literal("schwarz")
                .then(literal("chunkanalyse").executes(Command::ChunkAnalyse));
        dispatcher.register(builder);
    }

    public static int ChunkAnalyse(CommandContext<ServerCommandSource> ctx) {
        ServerCommandSource serverCommandSource = ctx.getSource();
        ChunkCommandHandler.AnalyseChunk(serverCommandSource);
        return 1;
    }
}
