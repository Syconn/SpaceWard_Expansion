package syconn.swe.block;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import syconn.swe.util.CanisterStorageType;

import java.util.Map;

public class FluidPipe extends FluidTransportBlock {
    private final VoxelShape ALONE = Block.box(5, 5, 5, 11, 11, 11);

    public FluidPipe() {
        super(BlockBehaviour.Properties.of(Material.METAL).noOcclusion().dynamicShape());
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE).setValue(FLUID_TYPE, CanisterStorageType.EMPTY));
    }

    public RenderShape getRenderShape(BlockState p_51307_) {
        return RenderShape.MODEL;
    }

    public VoxelShape getShape(BlockState state, BlockGetter p_151965_, BlockPos p_151966_, CollisionContext p_151967_) {
        return ALONE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        boolean n = false; boolean e = false; boolean s = false; boolean w = false;

        if (!ctx.getPlayer().level.isClientSide) {
            if (ctx.getPlayer().level.getBlockState(ctx.getClickedPos().offset(Direction.NORTH.getStepX(), Direction.NORTH.getStepY(), Direction.NORTH.getStepZ())).getBlock() instanceof FluidBaseBlock) n = true;
            if (ctx.getPlayer().level.getBlockState(ctx.getClickedPos().offset(Direction.EAST.getStepX(), Direction.EAST.getStepY(), Direction.EAST.getStepZ())).getBlock() instanceof FluidBaseBlock) e = true;
            if (ctx.getPlayer().level.getBlockState(ctx.getClickedPos().offset(Direction.WEST.getStepX(), Direction.WEST.getStepY(), Direction.WEST.getStepZ())).getBlock() instanceof FluidBaseBlock) w = true;
            if (ctx.getPlayer().level.getBlockState(ctx.getClickedPos().offset(Direction.SOUTH.getStepX(), Direction.SOUTH.getStepY(), Direction.SOUTH.getStepZ())).getBlock() instanceof FluidBaseBlock) s = true;
            return super.getStateForPlacement(ctx).setValue(WEST, w).setValue(NORTH, n).setValue(EAST, e).setValue(SOUTH, s);
        }

        return super.getStateForPlacement(ctx);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction p_60542_, BlockState p_60543_, LevelAccessor level, BlockPos pos, BlockPos p_60546_) {
        boolean n = false; boolean e = false; boolean s = false; boolean w = false;

        if (level.getBlockState(pos.offset(Direction.NORTH.getStepX(), Direction.NORTH.getStepY(), Direction.NORTH.getStepZ())).getBlock() instanceof FluidBaseBlock) n = true;
        if (level.getBlockState(pos.offset(Direction.EAST.getStepX(), Direction.EAST.getStepY(), Direction.EAST.getStepZ())).getBlock() instanceof FluidBaseBlock) e = true;
        if (level.getBlockState(pos.offset(Direction.WEST.getStepX(), Direction.WEST.getStepY(), Direction.WEST.getStepZ())).getBlock() instanceof FluidBaseBlock) w = true;
        if (level.getBlockState(pos.offset(Direction.SOUTH.getStepX(), Direction.SOUTH.getStepY(), Direction.SOUTH.getStepZ())).getBlock() instanceof FluidBaseBlock) s = true;
        return state.setValue(WEST, w).setValue(NORTH, n).setValue(EAST, e).setValue(SOUTH, s);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_53334_) {
        p_53334_.add(NORTH, EAST, WEST, SOUTH, FLUID_TYPE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return null;
    }
}
