package syconn.swe.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.fluids.FluidStack;
import syconn.swe.init.ModFluids;
import syconn.swe.init.ModInit;
import syconn.swe.util.data.AirBubblesSavedData;

import static net.minecraft.world.level.material.Fluids.EMPTY;

public class AutoRefillCanister extends Canister {

    // TODO HAVE O2 CANISTER WORK UNDERWATER
    // TODO CANT SHIFT CLICK ITEM IN INVENTORY

    public AutoRefillCanister() {
        super(Rarity.RARE);
    }

    public void inventoryTick(ItemStack stack, Level level, Entity e, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, level, e, p_41407_, p_41408_);
        if (e instanceof Player player && (player.getItemInHand(InteractionHand.MAIN_HAND) == stack || player.getItemInHand(InteractionHand.OFF_HAND) == stack)) {
            onTick(level, stack, player);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (blockhitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        } else if (!level.isClientSide) {
            FluidState state = level.getFluidState(blockhitresult.getBlockPos());
            System.out.println(state.is(getType(itemstack)));
            if (increaseFluid(itemstack, new FluidStack(state.getType(), 1000))) level.setBlock(blockhitresult.getBlockPos(), Blocks.AIR.defaultBlockState(), 2);
        }
        return InteractionResultHolder.fail(itemstack);
    }

    public void onEquipmentTick(ItemStack stack, Level level, Player player) {
        super.onEquipmentTick(stack, level, player);
        onTick(level, stack, player);
    }

    private void onTick(Level level, ItemStack stack, Player player) {
        if (!level.isClientSide && AirBubblesSavedData.get().breathable(level.dimension(), player.getOnPos().above(1)) && SpaceArmor.hasFullKit(player)) {
            if (getType(stack) == ModFluids.SOURCE_O2_FLUID.get() && getValue(stack) < getMaxValue(stack)) {
                increaseFluid(stack, new FluidStack(ModFluids.SOURCE_O2_FLUID.get(), 1));
            }
        }
        if (getType(stack) != EMPTY && level.getFluidState(player.getOnPos().above(2)).is(getType(stack))) { // TODO MAY BE TO MUCH OF DOOP METHOD
            increaseFluid(stack, new FluidStack(level.getFluidState(player.getOnPos().above()).getType(), 1));
        }
    }

    public ItemStack create(FluidStack stack) {
        return create(stack.getAmount(), 8000, stack.getFluid(), ModInit.AUTO_REFILL_CANISTER.get());
    }

    public ItemStack createEmpty() {
        return create(0, 8000, EMPTY, ModInit.AUTO_REFILL_CANISTER.get());
    }

    public int getOutlineColor() {
        return 0xA3954D;
    }
}
