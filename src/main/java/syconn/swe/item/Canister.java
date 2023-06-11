package syconn.swe.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import syconn.swe.init.ModItems;
import syconn.swe.item.extras.EquipmentItem;
import syconn.swe.item.extras.ItemFluidHandler;
import syconn.swe.util.ResourceUtil;
import syconn.swe.util.data.SpaceSlot;

import java.util.List;
import java.util.Objects;

import static net.minecraft.world.level.material.Fluids.EMPTY;

/** USED FOR LIQUIDS ONLY */
public class Canister extends Item implements EquipmentItem, ItemFluidHandler {

    private static final String FLUID = "fluid";
    private static final String MAX = "max";
    private static final String CURRENT = "current";
    private static final String COLOR = "color";

    public Canister() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        if (getType(stack) == EMPTY)
            return false;
        return getDisplayValue(stack) != 6F;
    }

    @Override
    public int getBarColor(ItemStack stack) {
//        return stack.getOrCreateTag().getInt(COLOR);
        return ResourceUtil.getColor(getType(stack));
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return 13 * getValue(stack) / getMaxValue(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {

        if (getType(stack) != EMPTY) {
            list.add(Component.empty());
            list.add(Component.literal(getValue(stack) + "mb / " + getMaxValue(stack) + "mb").withStyle(ChatFormatting.YELLOW));
        }
        super.appendHoverText(stack, level, list, flag);
    }

    @Override
    public void onEquipmentTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide){
            if (getType(stack) == Fluids.LAVA) {
                player.setSecondsOnFire(2);
            }
            //TODO REVIST
//            else if (getType(stack) == CanisterStorageType.O2 && SpaceSuitOverlay.displayOxygen(player) && !player.isCreative()) {
//                setValue(stack, getValue(stack) - 1);
//            }
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        if (getType(stack) != EMPTY) {
            return getFluid(stack).getDisplayName().copy().append(" ").append(super.getName(stack));
        }
        return super.getName(stack);
    }

    @Override
    public SpaceSlot getSlot() {
        return SpaceSlot.TANK;
    }

    public static ItemStack create(int c, int m, Fluid type){
        ItemStack stack = new ItemStack(ModItems.CANISTER.get());
        if (c == 0) type = EMPTY;
        stack.getOrCreateTag().putString(FLUID, ForgeRegistries.FLUIDS.getKey(type).toString());
        if (type != EMPTY) stack.getOrCreateTag().putInt(COLOR, ResourceUtil.getColor(getType(stack)));
        else stack.getOrCreateTag().putInt(COLOR, -1);
        stack.getOrCreateTag().putInt(MAX, m);
        if (type != EMPTY) stack.getOrCreateTag().putInt(CURRENT, c);
        else stack.getOrCreateTag().putInt(CURRENT, 0);
        return stack;
    }

    public static Fluid getType(ItemStack stack){
        if (stack.getOrCreateTag().contains(FLUID)){
            ResourceLocation fluidName = new ResourceLocation(stack.getOrCreateTag().getString(FLUID));
            return Objects.requireNonNullElse(ForgeRegistries.FLUIDS.getValue(fluidName), EMPTY);
        }
        return EMPTY;
    }

    @Override
    public void setAmount(ItemStack stack, int v, Fluid fluid) {
        if (v >= 0 && v <= getMaxValue(stack)) stack.getOrCreateTag().putInt(CURRENT, v);
        if (v <= 0) stack.getOrCreateTag().putString(FLUID, ForgeRegistries.FLUIDS.getKey(EMPTY).toString());
        else stack.getOrCreateTag().putString(FLUID, ForgeRegistries.FLUIDS.getKey(fluid).toString());
    }

    public static int getValue(ItemStack stack){
        return stack.getOrCreateTag().getInt(CURRENT);
    }

    public static int getMaxValue(ItemStack stack){
        return stack.getOrCreateTag().getInt(MAX);
    }

    public static float getDisplayValue(ItemStack stack){
        if (stack.getOrCreateTag().contains(CURRENT) && stack.getOrCreateTag().contains(MAX))
            return (float) (stack.getOrCreateTag().getInt(CURRENT)) / stack.getOrCreateTag().getInt(MAX) * 6.0f;
        return 0;
    }

    @Override
    public FluidStack getFluid(ItemStack stack) {
        return new FluidStack(getType(stack), getValue(stack));
    }

    @Override
    public void setFluid(ItemStack item, FluidStack fluid) {
        item.getOrCreateTag().putString(FLUID, ForgeRegistries.FLUIDS.getKey(fluid.getFluid()).toString());
        item.getOrCreateTag().putInt(CURRENT, fluid.getAmount());
    }

    @Override
    public ItemStack create(FluidStack stack) {
        return create(stack.getAmount(), 8000, stack.getFluid());
    }

    @Override
    public ItemStack createEmpty() {
        return create(0, 8000, EMPTY);
    }

    @Override
    public int getColor(ItemStack stack) {
        return stack.getOrCreateTag().getInt(COLOR);
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return getMaxValue(stack);
    }
}
