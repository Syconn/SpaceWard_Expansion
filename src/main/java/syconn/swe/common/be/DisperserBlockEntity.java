package syconn.swe.common.be;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.TickPriority;
import net.minecraftforge.fluids.capability.FluidHandlerBlockEntity;
import syconn.swe.init.ModBlockEntity;
import syconn.swe.init.ModInit;
import syconn.swe.util.GUIFluidHandlerBlockEntity;
import syconn.swe.util.NbtHelper;
import syconn.swe.util.data.AirBubblesSavedData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static syconn.swe.block.OxygenDisperser.addBlock;

public class DisperserBlockEntity extends GUIFluidHandlerBlockEntity {

    public List<BlockPos> list = new ArrayList<>();
    public int maxFill = 20;
    private int testRate = 100;
    private UUID uuid;

    public DisperserBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntity.DISPERSER.get(), p_155229_, p_155230_, 8000);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, DisperserBlockEntity e) {
        e.testRate--;
        if (e.testRate <= 0) {
            e.testRate = 100;
            addBlock(level, pos.relative(Direction.UP), pos, 1);
            level.scheduleTick(pos, ModInit.OXYGEN_DISPERSER.get(), 25, TickPriority.NORMAL);
        }
    }

    public void failed(boolean t) {
        for (BlockPos pos : list) {
            level.removeBlock(pos, false);
        }
        if (t) {
            list.clear();
            AirBubblesSavedData.get().remove(level.dimension(), uuid);
        } else AirBubblesSavedData.get().set(level.dimension(), uuid, list);
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        if (this.uuid == null) this.uuid = uuid;
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("list", NbtHelper.writePosses(list));
        tag.putInt("fill", maxFill);
        if (this.uuid != null) tag.putUUID("DisperserUUID", this.uuid);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        list = NbtHelper.readPosses(tag.getCompound("list"));
        maxFill = tag.getInt("fill");
        if(tag.hasUUID("DisperserUUID")) this.uuid = tag.getUUID("DisperserUUID");
    }
}
