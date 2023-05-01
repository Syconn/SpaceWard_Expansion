package syconn.swe.client;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import syconn.swe.Main;
import syconn.swe.client.gui.SpaceSuitOverlay;
import syconn.swe.client.model.ChuteModel;
import syconn.swe.client.model.ParachuteModel;
import syconn.swe.client.renders.entity.layer.ParachuteLayer;
import syconn.swe.client.screen.RenderUtil;
import syconn.swe.init.ModItems;
import syconn.swe.util.Dyeable;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Main.MODID, value = Dist.CLIENT)
public class ClientHandler {

    public ClientHandler() {
    }

    public static void coloredItems(RegisterColorHandlersEvent.Item e){
        e.register((s, layer) -> layer == 0 ? Dyeable.getColor(s.getOrCreateTag()) : -1, ModItems.PARACHUTE.get());
    }

    public static void addLayers(EntityRenderersEvent.AddLayers e){
        addBackpackLayer(e.getSkin("default"), e.getEntityModels());
        addBackpackLayer(e.getSkin("slim"), e.getEntityModels());
    }

    private static void addBackpackLayer(LivingEntityRenderer<?, ?> renderer, EntityModelSet s)
    {
        if(renderer instanceof PlayerRenderer playerRenderer)
        {
            playerRenderer.addLayer(new ParachuteLayer<>(playerRenderer, s));
        }
    }

    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ParachuteModel.LAYER_LOCATION, ParachuteModel::createBodyLayer);
        event.registerLayerDefinition(ChuteModel.LAYER_LOCATION, ChuteModel::createBodyLayer);
    }

    public static void renderOverlay(RegisterGuiOverlaysEvent e){
        e.registerAbove(VanillaGuiOverlay.AIR_LEVEL.id(), "o2", SpaceSuitOverlay.O2_OVERLAY);
    }

    @SubscribeEvent
    public void onPlayerRenderScreen(ContainerScreenEvent.Render.Background event)
    {
        RenderUtil.overridePlayerScreen(event.getPoseStack(), event.getContainerScreen());
    }
}
