package syconn.swe;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import syconn.swe.client.ClientHandler;
import syconn.swe.common.CommonHandler;
import syconn.swe.init.ModItems;
import syconn.swe.item.Dyeable;
import syconn.swe.worldgen.dimension.MoonSpecialEffects;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MODID)
public class Main {

    public static final String MODID = "swe";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Main() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            modEventBus.addListener(this::addCreative);
            modEventBus.addListener(ClientHandler::addLayers);
        });
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::dimensionEffects);
        modEventBus.addListener(ClientHandler::addLayers);
        modEventBus.addListener(ClientHandler::coloredItems);

        ModItems.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new CommonHandler());
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientHandler());
    }

    public void dimensionEffects(RegisterDimensionSpecialEffectsEvent e){
        e.register(new ResourceLocation(MODID, "moon"), new MoonSpecialEffects());
    }
    public void addCreative(CreativeModeTabEvent.BuildContents e){
        if (e.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            for (DyeColor c : DyeColor.values()){
                ItemStack s = new ItemStack(ModItems.PARACHUTE.get());
                Dyeable.setColor(s.getOrCreateTag(), c.getFireworkColor());
                e.accept(s);
            }
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
