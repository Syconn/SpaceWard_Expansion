package syconn.swe.common.be;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.TickPriority;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.FluidHandlerBlockEntity;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import syconn.swe.block.DispersibleAirBlock;
import syconn.swe.common.container.DisperserMenu;
import syconn.swe.init.ModBlockEntity;
import syconn.swe.init.ModFluids;
import syconn.swe.init.ModInit;
import syconn.swe.util.GUIFluidHandlerBlockEntity;
import syconn.swe.util.NbtHelper;
import syconn.swe.util.data.AirBubblesSavedData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static syconn.swe.block.OxygenDisperser.addBlock;

public class DisperserBlockEntity extends GUIFluidHandlerBlockEntity implements MenuProvider {

    public List<BlockPos> list = new ArrayList<>();
    public int maxFill = 20;
    private int testRate = 0;
    private UUID uuid;

    public DisperserBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntity.DISPERSER.get(), p_155229_, p_155230_, 1000, List.of(Direction.DOWN));
        this.tank = new FluidTank(1000){
            @Override
            protected void onContentsChanged() { update(); }

            @Override
            public int fill(FluidStack resource, FluidAction action) {
                if (fluid.isEmpty()) updateTextures(resource);
                return super.fill(resource, action);
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return validator.test(stack) && stack.getFluid() == ModFluids.SOURCE_O2_FLUID.get();
            }
        };
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, DisperserBlockEntity e) {
        if (e.tank.getFluidInTank(0).getAmount() > 0) {
            e.testRate--;
            if (e.testRate <= 0) {
                e.testRate = 100;
                addBlock(level, pos.relative(Direction.UP), pos, 1);
                level.scheduleTick(pos, ModInit.OXYGEN_DISPERSER.get(), 25, TickPriority.NORMAL);
            }
        } else { // OPTIMISE
            AirBubblesSavedData.get().remove(level.dimension(), e.uuid);
        }
    }

    public void failed(boolean t) {
        for (BlockPos pos : list) if (level.getBlockState(pos).getBlock() instanceof DispersibleAirBlock) level.removeBlock(pos, false);
        if (t) {
            list.clear();
            AirBubblesSavedData.get().remove(level.dimension(), uuid);
        } else { // TODO REMOVE O2 AND PROCESS HOW LONG O2 WIll LAST
            AirBubblesSavedData.get().set(level.dimension(), uuid, list);
        }
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        if (this.uuid == null) this.uuid = uuid;
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("list", NbtHelper.writePosses(list));
        tag.putInt("fill", maxFill);
        if (this.uuid != null) tag.putUUID("DisperserUUID", this.uuid);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        list = NbtHelper.readPosses(tag.getCompound("list"));
        maxFill = tag.getInt("fill");
        if(tag.hasUUID("DisperserUUID")) this.uuid = tag.getUUID("DisperserUUID");
    }

    public Component getDisplayName() {
        return Component.literal("Oxygen Disperser");
    }

    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return new DisperserMenu(p_39954_, p_39955_, this);
    }
}
