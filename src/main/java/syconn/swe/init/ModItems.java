package syconn.swe.init;

import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import syconn.swe.block.FluidPipe;
import syconn.swe.block.FluidTank;
import syconn.swe.item.Canister;
import syconn.swe.item.Parachute;
import syconn.swe.item.SpaceArmor;
import syconn.swe.item.Wrench;
import syconn.swe.util.CanisterStorageType;
import syconn.swe.util.Dyeable;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

import static syconn.swe.Main.MODID;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<Parachute> PARACHUTE = ITEMS.register("parachute", Parachute::new);
    public static final RegistryObject<SpaceArmor> SPACE_HELMET = ITEMS.register("space_helmet", () -> new SpaceArmor(ArmorItem.Type.HELMET, new Item.Properties().defaultDurability(200)));
    public static final RegistryObject<SpaceArmor> SPACE_CHESTPLATE = ITEMS.register("space_chestplate", () -> new SpaceArmor(ArmorItem.Type.CHESTPLATE, new Item.Properties().defaultDurability(200)));
    public static final RegistryObject<SpaceArmor> SPACE_LEGGINGS = ITEMS.register("space_leggings", () -> new SpaceArmor(ArmorItem.Type.LEGGINGS, new Item.Properties().defaultDurability(200)));
    public static final RegistryObject<SpaceArmor> SPACE_BOOTS = ITEMS.register("space_boots", () -> new SpaceArmor(ArmorItem.Type.BOOTS, new Item.Properties().defaultDurability(200)));
    public static final RegistryObject<Canister> CANISTER = ITEMS.register("canister", Canister::new);
    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench", Wrench::new);

    public static final RegistryObject<FluidPipe> FLUID_PIPE = register("fluid_pipe", FluidPipe::new);
    public static final RegistryObject<Block> FLUID_TANK = register("fluid_tank", FluidTank::new);

    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> blockSupplier)
    {
        return register(id, blockSupplier, block1 -> new BlockItem(block1, new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> blockSupplier, @Nullable Function<T, BlockItem> supplier)
    {
        RegistryObject<T> registryObject = BLOCKS.register(id, blockSupplier);
        if(supplier != null)
        {
            ITEMS.register(id, () -> supplier.apply(registryObject.get()));
        }
        return registryObject;
    }

    public static void addItems(CreativeModeTab.Output e){
        for (DyeColor c : DyeColor.values()){
            ItemStack s = new ItemStack(ModItems.PARACHUTE.get());
            Dyeable.setColor(s, c.getFireworkColor());
            e.accept(s);
        }
        for (CanisterStorageType t : CanisterStorageType.values()){
            e.accept(Canister.create(1296, 1296, t));
        }
        for (RegistryObject<Item> i : ModItems.ITEMS.getEntries()){
            if (i.get() instanceof Parachute || i.get() instanceof Canister) continue;
            e.accept(i.get());
        }
    }
}
