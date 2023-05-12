package syconn.swe.init;

import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import syconn.swe.item.Canister;
import syconn.swe.item.Parachute;
import syconn.swe.item.SpaceArmor;
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
            e.accept(Canister.create(400, 400, t));
        }
        for (RegistryObject<Item> i : ModItems.ITEMS.getEntries()){
            if (i.get() instanceof Parachute || i.get() instanceof Canister) continue;
            e.accept(i.get());
        }
    }
}
