package syconn.swe.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.ticks.TickPriority;
import org.jetbrains.annotations.Nullable;
import syconn.swe.common.be.DisperserBlockEntity;
import syconn.swe.init.ModBlockEntity;
import syconn.swe.init.ModInit;
import syconn.swe.util.data.AirBubblesSavedData;

import java.util.UUID;

public class OxygenDisperser extends FluidBaseBlock {

    public OxygenDisperser() {
        super(Properties.of(Material.METAL));
    }

    @Override
    public void onPlace(BlockState state, Level l, BlockPos pos, BlockState p_54113_, boolean p_54114_) {
        l.getBlockEntity(pos, ModBlockEntity.DISPERSER.get()).get().setUUID(UUID.randomUUID());
        addBlock(l, pos.relative(Direction.UP), pos, 1);
        l.scheduleTick(pos, this, 25, TickPriority.NORMAL);
    }

    @Override
    public void onRemove(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_, boolean p_60519_) {
        if (p_60515_.hasBlockEntity() && (!p_60515_.is(p_60518_.getBlock()) || !p_60518_.hasBlockEntity()) && p_60516_.getBlockEntity(p_60517_) instanceof DisperserBlockEntity de) {
            AirBubblesSavedData.get().remove(p_60516_.dimension(), de.getUUID());
            for (BlockPos pos : de.list) {
                p_60516_.removeBlock(pos, false);
            }
        }
        super.onRemove(p_60515_, p_60516_, p_60517_, p_60518_, p_60519_);
    }

    @Override
    public void tick(BlockState p_222945_, ServerLevel p_222946_, BlockPos p_222947_, RandomSource p_222948_) {
        if (p_222946_.getBlockEntity(p_222947_) instanceof DisperserBlockEntity de) de.failed(false);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return !p_153212_.isClientSide ? createTickerHelper(p_153214_, ModBlockEntity.DISPERSER.get(), DisperserBlockEntity::serverTick) : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new DisperserBlockEntity(p_153215_, p_153216_);
    }

    public static void addBlock(Level l, BlockPos target, BlockPos source, int distance){
        if (l.getBlockState(target).isAir() && !(l.getBlockState(target).getBlock() instanceof DispersibleAirBlock) && l.getBlockEntity(source, ModBlockEntity.DISPERSER.get()).isPresent()) {
            l.setBlock(target, ModInit.OXYGEN.get().defaultBlockState(), 2);
            l.getBlockEntity(source, ModBlockEntity.DISPERSER.get()).get().list.add(target);
            if (l.getBlockEntity(target, ModBlockEntity.AIR.get()).isPresent()) {
                l.getBlockEntity(target, ModBlockEntity.AIR.get()).get().setup(distance, source);
            }
        }
    }

    public static int maxFill(Level l, BlockPos pos) {
        return l.getBlockEntity(pos, ModBlockEntity.DISPERSER.get()).get().maxFill;
    }
}
