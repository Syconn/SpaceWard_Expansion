package syconn.swe.common.be;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import syconn.swe.init.ModBlockEntity;

public class DisperserBlockEntity extends BlockEntity {

    public DisperserBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntity.DISPERSER.get(), p_155229_, p_155230_);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, DisperserBlockEntity e) {

    }
}
