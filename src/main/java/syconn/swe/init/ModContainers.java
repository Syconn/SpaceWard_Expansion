package syconn.swe.init;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import syconn.swe.Main;
import syconn.swe.common.be.PipeBlockEntity;
import syconn.swe.common.be.TankBlockEntity;
import syconn.swe.common.container.PipeMenu;
import syconn.swe.common.container.TankMenu;

public class ModContainers {

    public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Main.MODID);

    public static final RegistryObject<MenuType<TankMenu>> TANK_MENU = register("tank", (IContainerFactory<TankMenu>) (windowId, playerInventory, data) -> {
        TankBlockEntity tank = (TankBlockEntity) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new TankMenu(windowId, playerInventory, tank);
    });
    public static final RegistryObject<MenuType<PipeMenu>> PIPE_MENU = register("pipe_menu", (IContainerFactory<PipeMenu>) (windowId, playerInventory, data) -> {
        PipeBlockEntity tank = (PipeBlockEntity) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new PipeMenu(windowId, playerInventory, tank);
    });

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String id, MenuType.MenuSupplier<T> factory)
    {
        return REGISTER.register(id, () -> new MenuType<>(factory, FeatureFlagSet.of()));
    }
}
