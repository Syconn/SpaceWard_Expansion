package syconn.swe.block;

import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import syconn.swe.util.CanisterStorageType;

public abstract class FluidBaseBlock extends BaseEntityBlock {

    public static EnumProperty<CanisterStorageType> FLUID_TYPE = EnumProperty.create("fluid_type", CanisterStorageType.class);

    protected FluidBaseBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FLUID_TYPE);
    }

    public abstract boolean intractableBlock();
}
