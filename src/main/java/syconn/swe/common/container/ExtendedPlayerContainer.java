package syconn.swe.common.container;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import syconn.swe.common.container.slot.EquipmentItemSlot;
import syconn.swe.init.ModCapabilities;
import syconn.swe.item.extras.EquipmentItem;
import syconn.swe.util.data.SpaceSlot;

public class ExtendedPlayerContainer extends InventoryMenu {

    public ExtendedPlayerContainer(Inventory playerInventory, boolean localWorld, Player playerIn)
    {
        super(playerInventory, localWorld, playerIn);
        playerIn.getCapability(ModCapabilities.SPACE_SUIT).ifPresent(suit -> {
            this.addSlot(new EquipmentItemSlot(playerIn, SpaceSlot.TANK, suit, 0, 77, 44));
            this.addSlot(new EquipmentItemSlot(playerIn, SpaceSlot.PARACHUTE, suit, 1, 77, 26));
        });
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index)
    {
        ItemStack copyStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if(slot.hasItem())
        {
            ItemStack slotStack = slot.getItem();
            copyStack = slotStack.copy();
            EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(copyStack);
            if(index != 46 && copyStack.getItem() instanceof EquipmentItem)
            {
                if(!this.slots.get(46).hasItem())
                {
                    if(!this.moveItemStackTo(slotStack, 46, 47, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if(index == 0)
            {
                if(!this.moveItemStackTo(slotStack, 9, 45, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(slotStack, copyStack);
            }
            else if(index < 5)
            {
                if(!this.moveItemStackTo(slotStack, 9, 45, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(index < 9)
            {
                if(!this.moveItemStackTo(slotStack, 9, 45, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(equipmentSlot.getType() == EquipmentSlot.Type.ARMOR && !this.slots.get(8 - equipmentSlot.getIndex()).hasItem())
            {
                int i = 8 - equipmentSlot.getIndex();
                if(!this.moveItemStackTo(slotStack, i, i + 1, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(equipmentSlot == EquipmentSlot.OFFHAND && !this.slots.get(45).hasItem())
            {
                if(!this.moveItemStackTo(slotStack, 45, 46, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(index == 46)
            {
                if(!this.moveItemStackTo(slotStack, 9, 45, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(index < 36)
            {
                if(!this.moveItemStackTo(slotStack, 36, 45, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(index < 45)
            {
                if(!this.moveItemStackTo(slotStack, 9, 36, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.moveItemStackTo(slotStack, 9, 45, false))
            {
                return ItemStack.EMPTY;
            }

            if(slotStack.isEmpty())
            {
                slot.set(ItemStack.EMPTY);
            }
            else
            {
                slot.setChanged();
            }

            if(slotStack.getCount() == copyStack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, slotStack);
            if(index == 0)
            {
                playerIn.drop(slotStack, false);
            }
        }

        return copyStack;
    }
}
