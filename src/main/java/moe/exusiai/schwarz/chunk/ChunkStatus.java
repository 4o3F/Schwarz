package moe.exusiai.schwarz.chunk;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ChunkStatus implements Comparable<ChunkStatus> {
    private int score = 0;
    private int coords[] = new int[2];

    private WorldChunk chunk;
    private String world;

    private HashMap<BlockEntityType, Integer> blockEntityScore = new HashMap<>(){
        {
            put(BlockEntityType.CHEST, 1);
            put(BlockEntityType.TRAPPED_CHEST, 2);
            put(BlockEntityType.BLAST_FURNACE, 3);
            put(BlockEntityType.FURNACE, 3);
            put(BlockEntityType.SIGN, 1);
            put(BlockEntityType.BEACON, 35);
            put(BlockEntityType.HOPPER, 6);
            put(BlockEntityType.PISTON, 5);
            put(BlockEntityType.DISPENSER, 10);
            put(BlockEntityType.MOB_SPAWNER, 60);
        }
    };

    private HashMap<EntityType, Integer> livingEntityScore = new HashMap<>() {
        {
            put(EntityType.PLAYER, 15);
            put(EntityType.ZOMBIE, 2);
            put(EntityType.SPIDER, 2);
            put(EntityType.SKELETON, 2);
            put(EntityType.CREEPER, 3);
            put(EntityType.COW, 1);
            put(EntityType.PIG, 1);
            put(EntityType.SHEEP, 1);
            put(EntityType.EYE_OF_ENDER, 15);
            put(EntityType.EGG, 3);
            put(EntityType.FISHING_BOBBER, 2);
            put(EntityType.SNOWBALL, 2);
            put(EntityType.ARROW, 1);
            put(EntityType.PAINTING, 1);
            put(EntityType.ITEM_FRAME, 3);
            put(EntityType.ARMOR_STAND, 4);
            put(EntityType.ITEM, 2);
            put(EntityType.EXPERIENCE_ORB, 3);
            put(EntityType.FIREWORK_ROCKET, 8);
            put(EntityType.ENDER_DRAGON, 85);
            put(EntityType.END_CRYSTAL, 10);
            put(EntityType.WITHER, 55);
            put(EntityType.WITHER_SKULL, 20);
            put(EntityType.TNT, 7);
            put(EntityType.TNT_MINECART, 15);
            put(EntityType.HOPPER_MINECART, 20);
            put(EntityType.SPAWNER_MINECART, 80);
            put(EntityType.ENDERMITE, 5);
        }
    };

        public ChunkStatus(WorldChunk chunk) {
        this.chunk = chunk;
        if (chunk.getWorld().getRegistryKey().equals(World.OVERWORLD)) {
            this.world = "overworld";
        } else if (chunk.getWorld().getRegistryKey().equals(World.NETHER)) {
            this.world = "nether";
        } else if (chunk.getWorld().getRegistryKey().equals(World.END)) {
            this.world = "end";
        }
        coords[0] = (chunk.getPos().getStartX() + chunk.getPos().getEndX()) / 2;
        coords[1] = (chunk.getPos().getStartZ() + chunk.getPos().getEndZ()) / 2;
    }

    public void genScore() {
        Map<BlockPos, BlockEntity> blockentities = chunk.getBlockEntities();
        for (BlockEntity blockEntity : blockentities.values()) {
            if (blockEntityScore.get(blockEntity.getType()) != null) {
                score += blockEntityScore.get(blockEntity.getType());
            }
        }

        Integer startx = chunk.getPos().getStartX();
        Integer startz = chunk.getPos().getStartZ();
        Integer endx = chunk.getPos().getEndX();
        Integer endz = chunk.getPos().getEndZ();
        Box box = new Box(startx, 0, startz, endx, 256, endz);
        List<Entity> livingEntityList = new ArrayList<>();
//        chunk.collectEntities(null, box, livingEntityList, new Predicate<Entity>() {
//            @Override
//            public boolean test(Entity entity) {
//                return true;
//            }
//        });
        livingEntityList = chunk.getWorld().getOtherEntities(null, box);
        for (Entity entity : livingEntityList) {
            if (livingEntityScore.get(entity.getType()) != null) {
                score += livingEntityScore.get(entity.getType());
            }
        }
    }

    public LiteralText genText() {
        String text = "Chunk Center Pos: " + String.valueOf(coords[0]) + " " + String.valueOf(coords[1])
                + "\nScore: " + String.valueOf(score)
                + "\nWorld: " + world;
//                + "\nCenterPos: " + String.valueOf(coords[0] * 16 + 8) + " " + String.valueOf(coords[1] * 16 +8);
        LiteralText literalText = new LiteralText(text);
        return literalText;
    }

    private static Integer getChunkBlockEntitySize(WorldChunk chunk) {
        return chunk.getBlockEntities().size();
    }

    private static Integer getChunkLivingEntitySize(WorldChunk chunk) {
        Integer startx = chunk.getPos().getStartX();
        Integer startz = chunk.getPos().getStartZ();
        Integer endx = chunk.getPos().getEndX();
        Integer endz = chunk.getPos().getEndZ();
        Box box = new Box(startx, 0, startz, endx, 256, endz);
        List<Entity> entityList = new ArrayList<>();
//        chunk.collectEntities(null, box, entityList, new Predicate<Entity>() {
//            @Override
//            public boolean test(Entity entity) {
//                return true;
//            }
//        });
        entityList = chunk.getWorld().getOtherEntities(null, box);
        return entityList.size();
    }

    @Override
    public int compareTo(@NotNull ChunkStatus from) {
        int CompareQuantity = from.score;
        return CompareQuantity - score;
    }
}
