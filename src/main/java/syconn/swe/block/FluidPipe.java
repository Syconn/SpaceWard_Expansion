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
import syconn.swe.common.be.PipeBlockEntity;
import syconn.swe.util.CanisterStorageType;
import syconn.swe.util.PipeModule;

import java.util.Map;
import java.util.stream.Stream;

public class FluidPipe extends FluidTransportBlock {

    public FluidPipe() {
        super(BlockBehaviour.Properties.of(Material.METAL).noOcclusion().dynamicShape());
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE).setValue(UP, Boolean.FALSE).setValue(DOWN, Boolean.FALSE).setValue(FLUID_TYPE, CanisterStorageType.EMPTY));
    }

    public RenderShape getRenderShape(BlockState p_51307_) {
        return RenderShape.MODEL;
    }

    public VoxelShape getShape(BlockState state, BlockGetter p_151965_, BlockPos p_151966_, CollisionContext p_151967_) {
        return new PipeModule(state).getShape();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        if (!ctx.getPlayer().level.isClientSide) return new PipeModule().getStateForPlacement(super.getStateForPlacement(ctx).setValue(FluidBaseBlock.FLUID_TYPE, CanisterStorageType.LAVA), ctx.getClickedPos(), ctx.getLevel());
        return super.getStateForPlacement(ctx);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction p_60542_, BlockState p_60543_, LevelAccessor level, BlockPos pos, BlockPos p_60546_) {
        return new PipeModule().getStateForPlacement(state, pos, level);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_53334_) {
        p_53334_.add(NORTH, EAST, WEST, SOUTH, UP, DOWN, FLUID_TYPE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PipeBlockEntity(pos, state);
    }
}
