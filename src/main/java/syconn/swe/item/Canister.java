package syconn.swe.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import syconn.swe.capabilities.ISpaceSuit;
import syconn.swe.client.gui.SpaceSuitOverlay;
import syconn.swe.init.ModCapabilities;
import syconn.swe.init.ModItems;
import syconn.swe.util.CanisterStorageType;
import java.util.List;

/** USED FOR LIQUIDS ONLY */
public class Canister extends EquipmentItem {

    public static String TYPE = "type";
    public static String MAX = "max";
    public static String CURRENT = "current";

    public Canister() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        if (getType(stack) == CanisterStorageType.EMPTY)
            return false;
        return getDisplayValue(stack) != 6F;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return getType(stack).getColor();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return 13 * getValue(stack) / getMaxValue(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {

        if (getType(stack) != CanisterStorageType.EMPTY) {
            list.add(Component.empty());
            list.add(Component.literal(getValue(stack) + "mb / " + getMaxValue(stack) + "mb").withStyle(ChatFormatting.YELLOW));
        }
        super.appendHoverText(stack, level, list, flag);
    }

    @Override
    public void onEquipmentTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide){
            if (getType(stack) == CanisterStorageType.LAVA) {
                player.setSecondsOnFire(2);
            } else if (getType(stack) == CanisterStorageType.O2 && SpaceSuitOverlay.displayOxygen(player) && !player.isCreative()) {
                setValue(stack, getValue(stack) - 1);
            }
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        if (getType(stack) != CanisterStorageType.EMPTY) {
            return Component.literal(StringUtils.capitalize(getType(stack).name().toLowerCase()) + " ").append(super.getName(stack));
        }
        return super.getName(stack);
    }

    @Override
    public Slot getSlot() {
        return Slot.TANK;
    }

    public static ItemStack create(int c, int m, CanisterStorageType type){
        ItemStack stack = new ItemStack(ModItems.CANISTER.get());
        stack.getOrCreateTag().putInt(TYPE, type.getType());
        stack.getOrCreateTag().putInt(MAX, m);
        if (type != CanisterStorageType.EMPTY) stack.getOrCreateTag().putInt(CURRENT, c);
        else stack.getOrCreateTag().putInt(CURRENT, 0);
        return stack;
    }

    public static CanisterStorageType getType(ItemStack stack){
        if (stack.getOrCreateTag().contains(TYPE))
            return CanisterStorageType.get(stack.getOrCreateTag().getInt(TYPE));
        return CanisterStorageType.EMPTY;
    }

    public static void setValue(ItemStack stack, int v){
        if (v >= 0 && v <= getMaxValue(stack)) stack.getOrCreateTag().putInt(CURRENT, v);
        else if (v <= 0) stack.getOrCreateTag().putInt(TYPE, CanisterStorageType.EMPTY.getType());
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
}
