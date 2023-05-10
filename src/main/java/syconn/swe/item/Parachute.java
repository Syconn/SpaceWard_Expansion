package syconn.swe.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import syconn.swe.util.Dyeable;

import java.util.Optional;
import java.util.function.Consumer;

public class Parachute extends EquipmentItem implements Equipable, Dyeable {

    public Parachute() {
        super(new Properties().stacksTo(1));
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level l, @NotNull Player p, @NotNull InteractionHand h) {
        return this.swapWithEquipmentSlot(this, l, p, h);
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack p_150902_) {
        return super.getTooltipImage(p_150902_);
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.CHEST;
    }

    public @NotNull SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_ELYTRA;
    }

    @Override
    public Slot getSlot() {
        return Slot.PARACHUTE;
    }
}
