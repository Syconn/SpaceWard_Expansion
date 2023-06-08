package syconn.swe.common.be;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.FluidHandlerBlockEntity;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import syconn.swe.block.FluidBaseBlock;
import syconn.swe.block.FluidPipe;
import syconn.swe.init.ModBlockEntity;
import syconn.swe.util.NbtHelper;
import syconn.swe.util.PipeModule;
import syconn.swe.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

public class PipeBlockEntity extends FluidHandlerBlockEntity {

    //EXPORTER AS LIST OF BLOCK POS
    private BlockPos importer = BlockPos.ZERO;
    private BlockPos exporter = BlockPos.ZERO;
    private BlockPos source = BlockPos.ZERO;

    private static final int transferSpeed = 80;

    public PipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntity.PIPE.get(), pos, state);
    }

    public BlockPos getImporter() {
        return importer;
    }

    public BlockPos getExporter() {
        return exporter;
    }

    public BlockPos getSource() {
        return source;
    }

    public void setSource(BlockPos source, Level level) {
        this.source = source;
        for (Direction d : Direction.values()){
            if (level.getBlockEntity(worldPosition.relative(d)) instanceof PipeBlockEntity pe && !pe.source.equals(this.source)){
                pe.setSource(source, level);
            }
        }
        update();
    }

    public void setImporter(BlockPos importer) {
        if (level.getBlockEntity(importer) != null) {
            if (this.importer.equals(importer)) updateImporter();
            else if (level.getBlockEntity(importer).getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent() && !(level.getBlockEntity(importer) instanceof PipeBlockEntity))
                this.importer = importer;
            if (this.exporter.equals(importer)) exporter = BlockPos.ZERO;
            update();
        }
    }

    public void setExporter(BlockPos exporter) {
        if (level.getBlockEntity(exporter) != null) {
            if (this.exporter.equals(exporter) || this.importer.equals(exporter)) this.exporter = BlockPos.ZERO;
            else if (level.getBlockEntity(exporter).getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent() && !(level.getBlockEntity(exporter) instanceof PipeBlockEntity)) this.exporter = exporter;
            if (this.importer.equals(exporter)) updateImporter();
            update();
        }
    }

    private void updateImporter(){
        importer = BlockPos.ZERO;
        updateStates();
        this.source = BlockPos.ZERO;
    }

    public void updateStates(){
        for (Direction d : Direction.values()){
            if (level.getBlockEntity(worldPosition.relative(d)) instanceof PipeBlockEntity pe && pe.source.equals(this.source)){
                pe.setSource(BlockPos.ZERO, level);
            }
        }
    }

    // TODO FIXES / CHANGES TO PERFECT
    // - BETTER MULTI IMPORT/EXPORT SYSTEM
    // - PIPE UPGRADE - EXTRACTOR PRIORITIES - SPEED - SETTINGS MENU?
    public static void serverTick(Level l, BlockPos pos, BlockState state, PipeBlockEntity be){
        if (!isValidSource(l, be)) {
            if (!be.tank.isEmpty()) be.tank.drain(be.tank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE);
            be.source = BlockPos.ZERO;
            state = state.setValue(FluidBaseBlock.FLUID_TYPE, false);
            l.setBlock(pos, state, 3);
        } else {
            state = state.setValue(FluidBaseBlock.FLUID_TYPE, true);
            l.setBlock(pos, state, 3);
        }
        if (l.getBlockEntity(be.importer) == null || !l.getBlockEntity(be.importer).getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent() && be.importer != BlockPos.ZERO){
            be.importer = BlockPos.ZERO;
        }
        if (l.getBlockEntity(be.exporter) == null || !l.getBlockEntity(be.exporter).getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent() && be.exporter != BlockPos.ZERO){
            be.exporter = BlockPos.ZERO;
        }
        if (!be.source.equals(BlockPos.ZERO) && !be.exporter.equals(BlockPos.ZERO) && l.getBlockEntity(be.exporter) != null && l.getBlockEntity(be.source) != null) {
            IFluidHandler export = l.getBlockEntity(be.exporter).getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null);
            IFluidHandler source = l.getBlockEntity(be.source).getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null);

            if (export.isFluidValid(0, source.getFluidInTank(0)) && export.getFluidInTank(0).getAmount() / export.getTankCapacity(0) != 1){
                FluidStack stack = source.drain(transferSpeed, IFluidHandler.FluidAction.EXECUTE);
                int filled = export.fill(stack, IFluidHandler.FluidAction.EXECUTE);
                if (filled != stack.getAmount()) source.fill(new FluidStack(stack, stack.getAmount() - filled), IFluidHandler.FluidAction.EXECUTE);
            }
        }
        update(l, pos, state);
    }

    private static boolean isValidSource(Level l, PipeBlockEntity be){
        return !be.source.equals(BlockPos.ZERO) && l.getBlockEntity(be.source) != null && l.getBlockEntity(be.source).getCapability(ForgeCapabilities.FLUID_HANDLER).map(f -> f.getFluidInTank(0).getAmount()).orElse(0) > 0;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        importer = NbtUtils.readBlockPos(tag.getCompound("importer"));
        exporter = NbtUtils.readBlockPos(tag.getCompound("exporter"));
        source = NbtUtils.readBlockPos(tag.getCompound("source"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("importer", NbtUtils.writeBlockPos(importer));
        tag.put("exporter", NbtUtils.writeBlockPos(exporter));
        tag.put("source", NbtUtils.writeBlockPos(source));
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("importer", NbtUtils.writeBlockPos(importer));
        tag.put("exporter", NbtUtils.writeBlockPos(exporter));
        tag.put("source", NbtUtils.writeBlockPos(source));
        return tag;
    }

    private void update(){
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
}
