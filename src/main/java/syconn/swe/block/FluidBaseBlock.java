package syconn.swe.block;

import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.fluids.FluidStack;
import syconn.swe.util.CanisterStorageType;

public abstract class FluidBaseBlock extends BaseEntityBlock {
    public static BooleanProperty FLUID_TYPE = BooleanProperty.create("fluid_type");

    protected FluidBaseBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }
}
