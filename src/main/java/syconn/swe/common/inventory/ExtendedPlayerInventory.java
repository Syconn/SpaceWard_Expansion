package syconn.swe.common.inventory;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ExtendedPlayerInventory extends Inventory {

    private NonNullList<ItemStack> space_gear = NonNullList.withSize(0, ItemStack.EMPTY);
    private final List<NonNullList<ItemStack>> allInventories = ImmutableList.of(this.items, this.armor, this.offhand, this.space_gear);

    public ExtendedPlayerInventory(Player p) {
        super(p);

    }
}
