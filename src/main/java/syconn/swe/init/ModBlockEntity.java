package syconn.swe.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import syconn.swe.Main;
import syconn.swe.common.be.PipeBlockEntity;
import syconn.swe.common.be.TankBlockEntity;

import java.util.function.Supplier;

public class ModBlockEntity {

    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MODID);

    public static final RegistryObject<BlockEntityType<PipeBlockEntity>> PIPE = register("pipe_be", PipeBlockEntity::new, () -> new Block[]{ModItems.FLUID_PIPE.get()});
    public static final RegistryObject<BlockEntityType<TankBlockEntity>> TANK = register("tank_be", TankBlockEntity::new, () -> new Block[]{ModItems.FLUID_TANK.get()});

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> supplier, Supplier<Block[]> validBlocksSupplier)
    {
        return REGISTER.register(name, () -> BlockEntityType.Builder.of(supplier, validBlocksSupplier.get()).build(null));
    }
}
