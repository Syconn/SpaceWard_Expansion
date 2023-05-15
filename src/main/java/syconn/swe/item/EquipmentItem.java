package syconn.swe.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class EquipmentItem extends Item {

    public EquipmentItem(Properties properties) {
        super(properties);
    }

    public static boolean is(ItemStack stack){
        return stack.getItem() instanceof EquipmentItem;
    }

    public abstract Slot getSlot();

    /**
     * Called to tick equipment in the equipment slot. Override to do something
     */
    public void onEquipmentTick(ItemStack stack, Level level, Player player) {

    }

    public enum Slot {
        TANK("empty_canister"),
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
