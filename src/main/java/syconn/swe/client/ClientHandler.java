package syconn.swe.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import syconn.swe.Main;
import syconn.swe.block.FluidBaseBlock;
import syconn.swe.block.FluidPipe;
import syconn.swe.block.FluidTransportBlock;
import syconn.swe.client.gui.SpaceSuitOverlay;
import syconn.swe.client.model.*;
import syconn.swe.client.renders.ber.PipeBER;
import syconn.swe.client.renders.ber.TankBER;
import syconn.swe.client.renders.entity.layer.SpaceSuitLayer;
import syconn.swe.client.screen.PipeScreen;
import syconn.swe.client.screen.RenderUtil;
import syconn.swe.client.screen.TankScreen;
import syconn.swe.common.be.PipeBlockEntity;
import syconn.swe.common.be.TankBlockEntity;
import syconn.swe.init.ModBlockEntity;
import syconn.swe.init.ModContainers;
import syconn.swe.init.ModItems;
import syconn.swe.item.Canister;
import syconn.swe.util.ColorUtil;
import syconn.swe.util.Dyeable;
import syconn.swe.util.ResourceUtil;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Main.MODID, value = Dist.CLIENT)
public class ClientHandler {

    public ClientHandler() {
        registerProperties();
        MenuScreens.register(ModContainers.TANK_MENU.get(), TankScreen::new);
        MenuScreens.register(ModContainers.PIPE_MENU.get(), PipeScreen::new);
    }

    public static void registerProperties(){
        ItemProperties.register(ModItems.CANISTER.get(), new ResourceLocation(Main.MODID, "stage"), new ItemPropertyFunction() {
            public float call(ItemStack stack, @Nullable ClientLevel p_174677_, @Nullable LivingEntity p_174678_, int p_174679_) { return Canister.getDisplayValue(stack); }
        });
    }

    public static void coloredItems(RegisterColorHandlersEvent.Item e) {
        e.register((s, layer) -> layer == 0 ? Dyeable.getColor(s) : -1, ModItems.PARACHUTE.get());
        e.register((s, layer) -> layer == 1 ? ColorUtil.getClosetColor(s.getBarColor()).getMaterialColor().col : -1, ModItems.CANISTER.get());
    }

    public static void coloredBlocks(RegisterColorHandlersEvent.Block e) {}

    public static void addLayers(EntityRenderersEvent.AddLayers e){
        addBackpackLayer(e.getSkin("default"), e.getEntityModels());
        addBackpackLayer(e.getSkin("slim"), e.getEntityModels());
    }

    private static void addBackpackLayer(LivingEntityRenderer<?, ?> renderer, EntityModelSet s)
    {
        if(renderer instanceof PlayerRenderer playerRenderer)
        {
            playerRenderer.addLayer(new SpaceSuitLayer<>(playerRenderer, s));
        }
    }

    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ParachuteModel.LAYER_LOCATION, ParachuteModel::createBodyLayer);
        event.registerLayerDefinition(ChuteModel.LAYER_LOCATION, ChuteModel::createBodyLayer);
        event.registerLayerDefinition(TankModel.LAYER_LOCATION, TankModel::createBodyLayer);
        event.registerLayerDefinition(FluidPipeModel.LAYER_LOCATION, FluidPipeModel::createBodyLayer);
        event.registerLayerDefinition(FluidModel.LAYER_LOCATION, FluidModel::createBodyLayer);
        event.registerLayerDefinition(FluidInPipeModel.LAYER_LOCATION, FluidInPipeModel::createBodyLayer);
    }

    public static void renderOverlay(RegisterGuiOverlaysEvent e){
        e.registerAbove(VanillaGuiOverlay.AIR_LEVEL.id(), "o2", SpaceSuitOverlay.O2_OVERLAY);
    }

    @SubscribeEvent
    public void onPlayerRenderScreen(ContainerScreenEvent.Render.Background event)
    {
        RenderUtil.overridePlayerScreen(event.getPoseStack(), event.getContainerScreen());
    }

    public static void entityRender(EntityRenderersEvent.RegisterRenderers e){
        e.registerBlockEntityRenderer(ModBlockEntity.PIPE.get(), PipeBER::new);
        e.registerBlockEntityRenderer(ModBlockEntity.TANK.get(), TankBER::new);
    }
}
