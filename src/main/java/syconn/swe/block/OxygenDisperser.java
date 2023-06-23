package syconn.swe.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;
import syconn.swe.common.be.DisperserBlockEntity;
import syconn.swe.init.ModBlockEntity;

public class OxygenDisperser extends FluidBaseBlock {

    public OxygenDisperser() {
        super(Properties.of(Material.METAL));
    }

    @Override
    public void onPlace(BlockState state, Level l, BlockPos pos, BlockState p_54113_, boolean p_54114_) {

    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return !p_153212_.isClientSide ? createTickerHelper(p_153214_, ModBlockEntity.DISPERSER.get(), DisperserBlockEntity::serverTick) : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new DisperserBlockEntity(p_153215_, p_153216_);
    }
}
