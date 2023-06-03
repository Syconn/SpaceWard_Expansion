package syconn.swe.common.be;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.EmptyFluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.FluidHandlerBlockEntity;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import syconn.swe.common.container.TankMenu;
import syconn.swe.init.ModBlockEntity;

public class TankBlockEntity extends FluidHandlerBlockEntity implements MenuProvider {

    private final ItemStackHandler items = new ItemStackHandler(getContainerSize()) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
        }
    };
    private final LazyOptional<IItemHandler> holder = LazyOptional.of(() -> items);

    public TankBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntity.TANK.get(), pos, state);
        this.tank = new FluidTank(16000){
            @Override
            protected void onContentsChanged() {
                setChanged();
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
            }
        };
    }

    public FluidTank getFluidTank()
    {
        return this.tank;
    }

    public ItemStackHandler getItems() {
        return items;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Inventory")) {
            items.deserializeNBT(tag.getCompound("Inventory"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tank.writeToNBT(tag);
        tag.put("Inventory", items.serializeNBT());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tank.writeToNBT(tag);
        tag.put("items", items.serializeNBT());
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        holder.invalidate();
    }

    private int getContainerSize(){
        return 2;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Fluid Tank");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory p_39955_, Player p_39956_) {
        return new TankMenu(id, p_39955_, this);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.ITEM_HANDLER)
            return holder.cast();
        return super.getCapability(capability, facing);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TankBlockEntity e) {
        if (!level.isClientSide) {
            ItemStack heldItem = e.getItems().getStackInSlot(0);
            if (heldItem.getItem() instanceof BucketItem b) {
                int fill = FluidUtil.getFluidHandler(heldItem).map(handler -> e.tank.fill(handler.getFluidInTank(0), IFluidHandler.FluidAction.EXECUTE)).orElse(0);
                if (fill > 0) {
                    e.getItems().extractItem(0, 1, false);
                    e.getItems().insertItem(1, new ItemStack(Items.BUCKET), false);
                }
                else if (b.getFluid() instanceof EmptyFluid && !e.getFluidTank().isEmpty() && !(e.getItems().getStackInSlot(1).getItem() instanceof BucketItem)) {
                    FluidStack fluidStack = FluidUtil.getFluidHandler(heldItem).map(handler -> e.tank.drain(handler.getTankCapacity(0), IFluidHandler.FluidAction.EXECUTE)).orElse(FluidStack.EMPTY);
                    e.getItems().extractItem(0, 1, false);
                    if (fluidStack == FluidStack.EMPTY) e.getItems().insertItem(1, new ItemStack(Items.BUCKET), false);
                    else e.getItems().insertItem(1, FluidUtil.getFilledBucket(fluidStack), false);
                }
            }
            else if (e.getItems().getStackInSlot(0) != ItemStack.EMPTY){
                e.getItems().insertItem(1, e.getItems().getStackInSlot(0), false);
                e.getItems().extractItem(0, 1, false);
            }

            if (!e.tank.isEmpty()) {
                for (Direction d : Direction.values()){
                    if (level.getBlockEntity(pos.relative(d)) instanceof PipeBlockEntity pe && pe.getImporter().equals(pos)){
                        pe.setSource(pos, level);
                    }
                }
            }
        }
    }
}
