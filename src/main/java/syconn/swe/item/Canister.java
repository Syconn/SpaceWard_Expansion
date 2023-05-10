package syconn.swe.item;

import net.minecraft.world.item.ItemStack;
import syconn.swe.common.container.slot.EquipmentItemSlot;
import syconn.swe.init.ModItems;
import syconn.swe.util.CanisterStorageType;

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
        return Math.round(13.0F - (float)getValue(stack) * 13.0F / (float)getMaxValue(stack));
    }

    @Override
    public Slot getSlot() {
        return Slot.OXYGEN;
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
        stack.getOrCreateTag().putInt(CURRENT, v);
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
