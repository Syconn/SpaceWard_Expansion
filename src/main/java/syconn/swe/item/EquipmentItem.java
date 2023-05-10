package syconn.swe.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class EquipmentItem extends Item {

    public EquipmentItem(Properties properties) {
        super(properties);
    }

    public static boolean is(ItemStack stack){
        return stack.getItem() instanceof EquipmentItem;
    }

    public abstract Slot getSlot();

    public enum Slot {
        OXYGEN("empty_canister"),
        PARACHUTE("empty_parachute");

        String loc;

        Slot(String loc) {
            this.loc = loc;
        }

        public String getLoc() {
            return loc;
        }
    }
}
