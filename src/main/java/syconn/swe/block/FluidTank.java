package syconn.swe.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import syconn.swe.common.be.PipeBlockEntity;
import syconn.swe.common.be.TankBlockEntity;
import syconn.swe.init.ModBlockEntity;

public class FluidTank extends FluidBaseBlock {

    public FluidTank() {
        super(Properties.of(Material.METAL));
    }

    public InteractionResult use(BlockState state, Level l, BlockPos pos, Player p, InteractionHand p_48710_, BlockHitResult p_48711_) {
        if (l.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            if(FluidUtil.interactWithFluidHandler(p, p_48710_, l, pos, p_48711_.getDirection()))
            {
                return InteractionResult.SUCCESS;
            }
            BlockEntity blockentity = l.getBlockEntity(pos);
            if (blockentity instanceof TankBlockEntity) {
                NetworkHooks.openScreen((ServerPlayer) p, (MenuProvider) blockentity, pos);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level l, BlockState p_153213_, BlockEntityType<T> p_153214_) {
//        return !l.isClientSide ? createTickerHelper(p_153214_, ModBlockEntity.TANK.get(), TankBlockEntity::serverTick) : null;
        return createTickerHelper(p_153214_, ModBlockEntity.TANK.get(), TankBlockEntity::serverTick);
    }

    @Override
    public boolean intractableBlock() {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TankBlockEntity(pos, state);
    }
}
