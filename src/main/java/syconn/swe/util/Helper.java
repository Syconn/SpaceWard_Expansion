package syconn.swe.util;

import net.minecraft.world.entity.player.Player;
import syconn.swe.common.inventory.ExtendedPlayerInventory;

public class Helper {

    public static ExtendedPlayerInventory inventory(Player p){
        return ((ExtendedPlayerInventory) p.getInventory());
    }
}
