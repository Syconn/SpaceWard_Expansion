package syconn.swe;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import syconn.swe.client.ClientHandler;
import syconn.swe.common.CommonHandler;
import syconn.swe.common.data.DimSettingsManager;
import syconn.swe.datagen.*;
import syconn.swe.init.ModBlockEntity;
import syconn.swe.init.ModContainers;
import syconn.swe.init.ModItems;
import syconn.swe.network.Network;
import syconn.swe.worldgen.dimension.MoonSpecialEffects;

@Mod(Main.MODID)
public class Main {

    public static final String MODID = "swe";
    public Main() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            modEventBus.addListener(this::createTab);
            modEventBus.addListener(ClientHandler::onRegisterLayers);
            modEventBus.addListener(ClientHandler::addLayers);
            modEventBus.addListener(ClientHandler::renderOverlay);
            modEventBus.addListener(ClientHandler::addLayers);
            modEventBus.addListener(ClientHandler::coloredBlocks);
            modEventBus.addListener(ClientHandler::coloredItems);
            modEventBus.addListener(ClientHandler::entityRender);
        });
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::dataGenerator);
        modEventBus.addListener(this::dimensionEffects);

        ModContainers.REGISTER.register(modEventBus);
        ModBlockEntity.REGISTER.register(modEventBus);
        ModItems.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Network.init();
        MinecraftForge.EVENT_BUS.register(new CommonHandler());
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientHandler());
    }

    public void dataGenerator(GatherDataEvent e){
        e.getGenerator().addProvider(e.includeClient(), new ItemModelGen(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));
        e.getGenerator().addProvider(e.includeClient(), new BlockModelGen(e.getGenerator().getPackOutput(), e.getExistingFileHelper()));
        e.getGenerator().addProvider(e.includeClient(), new LangGen(e.getGenerator().getPackOutput()));
        e.getGenerator().addProvider(e.includeServer(), new RecipeGen(e.getGenerator().getPackOutput()));
        e.getGenerator().addProvider(e.includeServer(), new TagsGen(e.getGenerator().getPackOutput(), e.getLookupProvider(), e.getExistingFileHelper()));
    }

    public void dimensionEffects(RegisterDimensionSpecialEffectsEvent e){
        e.register(new ResourceLocation(MODID, "moon"), new MoonSpecialEffects());
    }
    public void createTab(CreativeModeTabEvent.Register e){
        e.registerCreativeModeTab(new ResourceLocation(MODID, "space"), builder -> builder.noScrollBar().title(Component.translatable("itemGroup.space")).icon(() -> new ItemStack(ModItems.SPACE_HELMET.get())).displayItems((a, p) -> ModItems.addItems(p)).build());
    }

    @SubscribeEvent
    public void loadData(AddReloadListenerEvent e){
        e.addListener(new DimSettingsManager());
    }
}
