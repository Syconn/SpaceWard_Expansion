package syconn.swe.common.inventory;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;

public class ExtendedInventoryMenu extends InventoryMenu {

    public ExtendedInventoryMenu(Inventory inv, boolean localWorld, Player player) {
        super(inv, localWorld, player);
    }
}
