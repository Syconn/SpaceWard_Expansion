package syconn.swe.common.be;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import syconn.swe.init.ModBlockEntity;

public class PipeBlockEntity extends BlockEntity {

    public PipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntity.PIPE.get(), pos, state);
    }
}
