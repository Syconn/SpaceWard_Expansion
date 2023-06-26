package syconn.swe.util.data;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.server.ServerLifecycleHooks;
import syconn.swe.util.Helper;
import syconn.swe.util.NbtHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirBubblesSavedData extends SavedData {

    private final Map<ResourceKey<Level>, List<BlockPos>> levelBlockPositions = new HashMap<>();

    public void add(ResourceKey<Level> level, List<BlockPos> pos) {
        levelBlockPositions.put(level, Helper.combineLists(levelBlockPositions.get(level), pos));
    }

    public CompoundTag save(CompoundTag tag) {
        ListTag levelPoses = new ListTag();
        levelBlockPositions.forEach((level, positions) -> {
            CompoundTag cp = new CompoundTag();
            cp.putString("loc", level.location().toString());
            cp.put("positions", NbtHelper.writePosses(positions));
            levelPoses.add(cp);
        });
        tag.put("air_bubbles", levelPoses);
        return tag;
    }

    public void read(CompoundTag tag) {
        levelBlockPositions.clear();
        if(tag.contains("", Tag.TAG_LIST))
        {
            tag.getList("air_bubbles", Tag.TAG_COMPOUND).forEach(nbt -> {
                CompoundTag data = (CompoundTag) nbt;
                levelBlockPositions.put(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(data.getString("loc"))), NbtHelper.readPosses(data.getCompound("positions")));
            });
        }
    }

    public static AirBubblesSavedData create() {
        return new AirBubblesSavedData();
    }

    public static AirBubblesSavedData load(CompoundTag tag) {
        AirBubblesSavedData data = create();
        data.read(tag);
        return data;
    }

    public static AirBubblesSavedData get() {
        return ServerLifecycleHooks.getCurrentServer().overworld().getDataStorage().computeIfAbsent(AirBubblesSavedData::load, AirBubblesSavedData::create, "air_bubbles");
    }
}
