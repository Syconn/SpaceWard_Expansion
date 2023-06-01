package syconn.swe.common.be;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import syconn.swe.init.ModBlockEntity;
import syconn.swe.util.NbtHelper;
import syconn.swe.util.PipeModule;

import java.util.ArrayList;
import java.util.List;

public class PipeBlockEntity extends BlockEntity {

    private PipeModule.PipeType type = PipeModule.PipeType.TRANSPORT;
    private Direction feeder;
    private List<BlockPos> inputs = new ArrayList<>();

    public PipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntity.PIPE.get(), pos, state);
    }

    public static void serverTick(Level l, BlockPos pos, BlockState state, PipeBlockEntity be){
        if (be.type == PipeModule.PipeType.EXTRACTOR){
            
        }
    }

    public Direction getFeederDirection() {
        return feeder;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        type = PipeModule.PipeType.getType(tag.getInt("type"));
        feeder = Direction.from3DDataValue(tag.getInt("feeder"));
        inputs = NbtHelper.readPosses(tag.getCompound("network"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (type != null) tag.putInt("type", type.getId());
        if (feeder != null) tag.putInt("feeder", feeder.get3DDataValue());
        if (!inputs.isEmpty()) tag.put("network", NbtHelper.writePosses(inputs));
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        if (type != null) tag.putInt("type", type.getId());
        if (feeder != null) tag.putInt("feeder", feeder.get3DDataValue());
        if (!inputs.isEmpty()) tag.put("network", NbtHelper.writePosses(inputs));
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
