package syconn.swe.item;

import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import syconn.swe.capabilities.SpaceSuit;
import syconn.swe.init.ModCapabilities;

import java.util.Optional;

public class Parachute extends Item implements Equipable, Dyeable {

    public Parachute() {
        super(new Properties().stacksTo(1));
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    public InteractionResultHolder<ItemStack> use(Level l, Player p, InteractionHand h) {
        return this.swapWithEquipmentSlot(this, l, p, h);
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack p_150902_) {
        return super.getTooltipImage(p_150902_);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.CHEST;
    }

    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_ELYTRA;
    }
}
