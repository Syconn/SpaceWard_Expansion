package syconn.swe.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import syconn.swe.init.ModInit;
import syconn.swe.util.data.AirBubblesSavedData;

import static net.minecraft.world.level.material.Fluids.EMPTY;

public class AutoRefillCanister extends Canister {

    @Override
    public void inventoryTick(ItemStack stack, Level l, Entity e, int p_41407_, boolean p_41408_) {
        if (AirBubblesSavedData.get().breathable(e.level.dimension(), e.getOnPos().above(1)) && getType(stack) != Fluids.EMPTY && getValue(stack) < getMaxValue(stack)) {
            setAmount(stack, getValue(stack) + 1, getFluid(stack).getFluid());
        }
    }

    public void onEquipmentTick(ItemStack stack, Level level, Player player) {
        super.onEquipmentTick(stack, level, player);
        if (AirBubblesSavedData.get().breathable(level.dimension(), player.getOnPos().above(1)) && getType(stack) != Fluids.EMPTY && getValue(stack) < getMaxValue(stack)) {
            setAmount(stack, getValue(stack) + 1, getFluid(stack).getFluid());
        }
    }

    public ItemStack create(FluidStack stack) {
        return create(stack.getAmount(), 8000, stack.getFluid(), ModInit.AUTO_REFILL_CANISTER.get());
    }

    public ItemStack createEmpty() {
        return create(0, 8000, EMPTY, ModInit.AUTO_REFILL_CANISTER.get());
    }
}
