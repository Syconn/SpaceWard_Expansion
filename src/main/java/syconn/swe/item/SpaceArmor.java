package syconn.swe.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import syconn.swe.init.ModArmorTypes;

public class SpaceArmor extends ArmorItem {
    public SpaceArmor(Type p_266831_, Properties p_40388_) {
        super(ModArmorTypes.SPACE_SUIT, p_266831_, p_40388_);
    }

    public static boolean hasFullKit(Player p){
        return p.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ArmorItem && p.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ArmorItem
                && p.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ArmorItem && p.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ArmorItem;
    }
}
