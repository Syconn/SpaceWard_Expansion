package syconn.swe.common.be;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import syconn.swe.init.ModBlockEntity;
import syconn.swe.util.PipeModule;

public class PipeBlockEntity extends BlockEntity {

    private PipeModule.PipeType type;

    public PipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntity.PIPE.get(), pos, state);
        type = PipeModule.PipeType.TRANSPORT;
    }

    public static void serverTick(Level l, BlockPos pos, BlockState state, PipeBlockEntity be){

    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        type = PipeModule.PipeType.getType(tag.getInt("type"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (type != null) tag.putInt("type", type.getId());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        if (type != null) tag.putInt("type", type.getId());
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // Will get tag from #getUpdateTag
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
