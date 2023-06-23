package syconn.swe.common.be;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import syconn.swe.init.ModBlockEntity;
import syconn.swe.init.ModInit;

public class AirBlockEntity extends BlockEntity {

    private int b = 5;

    public AirBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntity.AIR.get(), p_155229_, p_155230_);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AirBlockEntity e) {

    }

    private static boolean isValidBlock(BlockState b){
        return b.isAir() && b.getBlock() != ModInit.OXYGEN.get();
    }
}
