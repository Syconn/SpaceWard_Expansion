package syconn.swe.common.be;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.FluidHandlerBlockEntity;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import syconn.swe.block.FluidBaseBlock;
import syconn.swe.common.container.PipeMenu;
import syconn.swe.init.ModBlockEntity;
import syconn.swe.util.Helper;
import syconn.swe.util.data.FluidPointSystem;

public class PipeBlockEntity extends FluidHandlerBlockEntity implements MenuProvider {

    private FluidPointSystem system = new FluidPointSystem();
    private Direction target = null;
    private BlockPos source = BlockPos.ZERO;
    private static final int transferSpeed = 80;

    private final ItemStackHandler items = new ItemStackHandler(3) {
        protected void onContentsChanged(int slot) {
            update();
        }
    };
    private final LazyOptional<IItemHandler> holder = LazyOptional.of(() -> items);

    public PipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntity.PIPE.get(), pos, state);
    }

    public BlockPos getSource() {
        return source;
    }

    public ItemStackHandler getItems() {
        return items;
    }

    public void setSource(BlockPos source, Level level) {
        this.source = source;
        update();
        for (Direction d : Direction.values()){
            if (level.getBlockEntity(worldPosition.relative(d)) instanceof PipeBlockEntity pe && !pe.source.equals(this.source)){
                pe.setSource(source, level);
            }
        }
    }

    public void setImporter(BlockPos importer) {
        if (level.getBlockEntity(importer) != null && level.getBlockEntity(importer).getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent() && !(level.getBlockEntity(importer) instanceof PipeBlockEntity)) {
            Direction d = Helper.dirToBlockPos(worldPosition, importer);
            boolean u = system.update(d, new FluidPointSystem.FluidPoint(importer, d, true, 1));
            if (u && target == null) target = d;
            updateStates();
        }
    }

    public void setExporter(BlockPos exporter) {
        if (level.getBlockEntity(exporter) != null && level.getBlockEntity(exporter).getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent() && !(level.getBlockEntity(exporter) instanceof PipeBlockEntity)) {
            Direction d = Helper.dirToBlockPos(worldPosition, exporter);
            boolean u = system.update(d, new FluidPointSystem.FluidPoint(exporter, d, false, 1));
            if (u && target == null) target = d;
            updateStates();
        }
    }

    public void updateStates(){
        this.source = BlockPos.ZERO;
        update();
        for (Direction d : Direction.values()){
            if (level.getBlockEntity(worldPosition.relative(d)) instanceof PipeBlockEntity pe && pe.source.equals(this.source)){
                pe.setSource(BlockPos.ZERO, level);
            }
        }
    }

    public FluidPointSystem.FluidPoint selectedTab() {
        return system.getPoint(target);
    }

    // TODO FIXES
    //  - Update Sources to highest Priority
    //  - EXPORTS not working rn
    //  - BETTER MULTI IMPORT/EXPORT SYSTEM
    //  - PIPE UPGRADE - EXTRACTOR PRIORITIES - SPEED - SETTINGS MENU?
    public static void serverTick(Level l, BlockPos pos, BlockState state, PipeBlockEntity be){
        // TODO Might need to check every Tick
        if (!isValidSource(l, be)) {
            if (!be.tank.isEmpty()) be.tank.drain(be.tank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE);
            be.source = BlockPos.ZERO;
            state = state.setValue(FluidBaseBlock.FLUID_TYPE, false);
            l.setBlock(pos, state, 3);
        } else {
            state = state.setValue(FluidBaseBlock.FLUID_TYPE, true);
            l.setBlock(pos, state, 3);
        }

//        if (!be.source.equals(BlockPos.ZERO) && !be.exporter.equals(BlockPos.ZERO) && l.getBlockEntity(be.exporter) != null && l.getBlockEntity(be.source) != null) {
//            IFluidHandler export = l.getBlockEntity(be.exporter).getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null);
//            IFluidHandler source = l.getBlockEntity(be.source).getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null);
//
//            if (export.isFluidValid(0, source.getFluidInTank(0)) && export.getFluidInTank(0).getAmount() / export.getTankCapacity(0) != 1){
//                FluidStack stack = source.drain(transferSpeed, IFluidHandler.FluidAction.EXECUTE);
//                int filled = export.fill(stack, IFluidHandler.FluidAction.EXECUTE);
//                if (filled != stack.getAmount()) source.fill(new FluidStack(stack, stack.getAmount() - filled), IFluidHandler.FluidAction.EXECUTE);
//            }
//        }
        update(l, pos, state);
    }

    private static boolean isValidSource(Level l, PipeBlockEntity be){
        return !be.source.equals(BlockPos.ZERO) && l.getBlockEntity(be.source) != null && l.getBlockEntity(be.source).getCapability(ForgeCapabilities.FLUID_HANDLER).map(f -> f.getFluidInTank(0).getAmount()).orElse(0) > 0;
    }

    public FluidPointSystem getSystem() {
        return system;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Inventory")) items.deserializeNBT(tag.getCompound("Inventory"));
        if (tag.contains("target")) target = Direction.from3DDataValue(tag.getInt("target"));
        system = new FluidPointSystem(tag.getCompound("system"));
        source = NbtUtils.readBlockPos(tag.getCompound("source"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (target != null) tag.putInt("target", target.get3DDataValue());
        tag.put("Inventory", items.serializeNBT());
        tag.put("system", system.write());
        tag.put("source", NbtUtils.writeBlockPos(source));
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        if (target != null) tag.putInt("target", target.get3DDataValue());
        tag.put("Inventory", items.serializeNBT());
        tag.put("system", system.write());
        tag.put("source", NbtUtils.writeBlockPos(source));
        return tag;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        holder.invalidate();
    }

    public void update(){
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
    }

    private static void update(Level level, BlockPos pos, BlockState state){
        setChanged(level, pos, state);
        level.sendBlockUpdated(pos, state, state, 2);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return new PipeMenu(p_39954_, p_39955_, this);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Pipe Screen");
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.ITEM_HANDLER)
            return holder.cast();
        return super.getCapability(capability, facing);
    }
}
