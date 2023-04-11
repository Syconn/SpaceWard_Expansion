package syconn.swe.client;

import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import syconn.swe.client.model.ParachuteModel;

public class ModelInstances {

    private ParachuteModel parachuteModel;

    @SubscribeEvent
    public void onLoadModels(EntityRenderersEvent.AddLayers event) {
        EntityModelSet models = event.getEntityModels();
        this.parachuteModel = new ParachuteModel(models.bakeLayer(ParachuteModel.LAYER_LOCATION));
    }

    public ParachuteModel getParachuteModel() {
        return parachuteModel;
    }
}
