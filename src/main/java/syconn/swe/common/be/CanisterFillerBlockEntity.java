package syconn.swe.common.be;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import syconn.swe.init.ModBlockEntity;
import syconn.swe.item.Canister;

public class CanisterFillerBlockEntity extends BlockEntity {

    private NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);

    public CanisterFillerBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntity.FILLER.get(), p_155229_, p_155230_);
    }

    public boolean addCanister(ItemStack stack) {
        if (stack.getItem() instanceof Canister) {
            for (int i = 0; i < 4; i++) {
                if (!items.get(i).isEmpty()) {
                    items.set(i, new ItemStack(stack.getItem()));
                    return true;
                }
            }
        }
        update();
        return false;
    }

    public ItemStack removeCanister() {
        for (int i = 0; i < 4; i++) {
            if (!items.get(i).isEmpty()) return items.remove(i);
        }
        update();
        return ItemStack.EMPTY;
    }

    public ItemStack getCanister(int i) {
        return items.get(i);
    }

    protected void saveAdditional(CompoundTag p_187471_) {
        super.saveAdditional(p_187471_);
        ContainerHelper.saveAllItems(p_187471_, this.items);
    }

    public void load(CompoundTag p_155245_) {
        super.load(p_155245_);
        this.items = NonNullList.withSize(4, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(p_155245_, this.items);
    }

    protected void update(){
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
    }
}
