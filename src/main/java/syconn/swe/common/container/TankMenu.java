package syconn.swe.common.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;
import syconn.swe.common.be.TankBlockEntity;
import syconn.swe.init.ModContainers;

public class TankMenu extends AbstractContainerMenu {

    private TankBlockEntity be;

    public TankMenu(int id, Inventory inventory, TankBlockEntity be) {
        super(ModContainers.TANK_MENU.get(), id);
        this.be = be;

        be.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 14, 9)); //INPUT FILLED BUCKET
            this.addSlot(new SlotItemHandler(handler, 1, 14, 61)); //OUTPUT EMPTY BUCKET
//            this.addSlot(new SlotItemHandler(handler, 2, 72, 9)); //INPUT EMPTY BUCKET
//            this.addSlot(new SlotItemHandler(handler, 3, 72, 61)); //OUTPUT FILLED BUCKET
        });

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inventory, i1, 8 + i1 * 18, 142));
        }
    }

    public TankBlockEntity getBE() {
        return be;
    }


    @Override
    public ItemStack quickMoveStack(Player player, int quickMovedSlotIndex) {
        ItemStack quickMovedStack = ItemStack.EMPTY;
        Slot quickMovedSlot = this.slots.get(quickMovedSlotIndex);
        if (quickMovedSlot != null && quickMovedSlot.hasItem()) {
            ItemStack rawStack = quickMovedSlot.getItem();
            quickMovedStack = rawStack.copy();
            if (quickMovedSlotIndex == 0) {
//                if (!this.moveItemStackTo(rawStack, 5, 41, true)) {
//                    return ItemStack.EMPTY;
//                }
            }
            else if (quickMovedSlotIndex >= 5 && quickMovedSlotIndex < 41) {
                if (!this.moveItemStackTo(rawStack, 1, 5, false)) {
                    if (quickMovedSlotIndex < 32) {
                        if (!this.moveItemStackTo(rawStack, 32, 41, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                    else if (!this.moveItemStackTo(rawStack, 5, 32, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
//            else if (!this.moveItemStackTo(rawStack, 5, 41, false)) {
//                return ItemStack.EMPTY;
//            }

            if (rawStack.isEmpty()) {
                quickMovedSlot.set(ItemStack.EMPTY);
            } else {
                quickMovedSlot.setChanged();
            }

            if (rawStack.getCount() == quickMovedStack.getCount()) {
                return ItemStack.EMPTY;
            }
            quickMovedSlot.onTake(player, rawStack);
        }

        return quickMovedStack;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }
}
