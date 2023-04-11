package syconn.swe.client.renders.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.ItemLayerModel;
import syconn.swe.Main;
import syconn.swe.client.model.ParachuteModel;

public class ParachuteLayer<P extends Player, M extends PlayerModel<P>> extends RenderLayer<P, M> {

    private final ParachuteModel pm;

    public ParachuteLayer(RenderLayerParent<P, M> p_117346_, EntityModelSet e) {
        super(p_117346_);
        pm = new ParachuteModel(e.bakeLayer(ParachuteModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack ps, MultiBufferSource bs, int packedLight, P p, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        ItemStack itemstack = p.getItemBySlot(EquipmentSlot.CHEST);
        ps.pushPose();
        ps.translate(0.0F, 0.0F, 0.125F);
        VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(bs, RenderType.armorCutoutNoCull(new ResourceLocation(Main.MODID, "textures/entity/layers/parachute.png")), false, itemstack.hasFoil());
        this.pm.renderToBuffer(ps, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        ps.popPose();
    }
}
