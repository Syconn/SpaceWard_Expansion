package syconn.swe.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import syconn.swe.common.be.AirBlockEntity;
import syconn.swe.util.config.Config;
import syconn.swe.init.ModBlockEntity;

public class DispersibleAirBlock extends BaseEntityBlock {

    public DispersibleAirBlock() {
        super(BlockBehaviour.Properties.of(Material.AIR).noCollission().noLootTable().air());
    }

    public VoxelShape getShape(BlockState p_48760_, BlockGetter p_48761_, BlockPos p_48762_, CollisionContext p_48763_) {
        return Shapes.empty();
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        if (Config.showOxygen.get()) return RenderShape.MODEL;
        return RenderShape.INVISIBLE;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return !p_153212_.isClientSide ? createTickerHelper(p_153214_, ModBlockEntity.AIR.get(), AirBlockEntity::serverTick) : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new AirBlockEntity(p_153215_, p_153216_);
    }
}
