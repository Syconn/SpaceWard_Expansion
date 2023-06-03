package syconn.swe.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import syconn.swe.common.inventory.ExtendedPlayerInventory;

public class Helper {

    public static ExtendedPlayerInventory inventory(Player p){
        return ((ExtendedPlayerInventory) p.getInventory());
    }

    public static int rotationFromDir(Direction d){
        return switch (d){
            case DOWN, EAST, UP -> 0;
            case NORTH -> 90;
            case SOUTH -> 270;
            case WEST -> 180;
        };
    }

    public static Direction dirToBlockPos(BlockPos pos1, BlockPos pos2){
        for (Direction d : Direction.values()){
            if (pos2.equals(pos1.relative(d))) return d;
        }
        return Direction.NORTH;
    }
}
