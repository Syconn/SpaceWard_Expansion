package syconn.swe.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.HashMap;
import java.util.Map;

public abstract class FluidTransportBlock extends FluidBaseBlock {
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;

    protected Map<Direction, BooleanProperty> DIRECTIONS = new HashMap<>(){{
        put(Direction.NORTH, NORTH);
        put(Direction.EAST, EAST);
        put(Direction.SOUTH, SOUTH);
        put(Direction.WEST, WEST);
    }};
    protected FluidTransportBlock(Properties p_49224_) {
        super(p_49224_);
    }
}
