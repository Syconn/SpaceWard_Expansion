package syconn.swe.block;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import syconn.swe.util.CanisterStorageType;

public class FluidPipe extends FluidTransportBlock {
    private final VoxelShape ALONE = Block.box(0.3125, 0.3125, 0.3125, 0.6875, 0.6875, 0.6875);
    private final VoxelShape SARM = Block.box(0.3125, 0.3125, 0, 0.6875, 0.6875, 0.6875);

    public FluidPipe() {
        super(BlockBehaviour.Properties.of(Material.METAL));
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE).setValue(FLUID_TYPE, CanisterStorageType.EMPTY));
    }

    public RenderShape getRenderShape(BlockState p_51307_) {
        return RenderShape.MODEL;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_53334_) {
        super.createBlockStateDefinition(p_53334_);
        p_53334_.add(NORTH, EAST, WEST, SOUTH);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return null;
    }
}
