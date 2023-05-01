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
import net.minecraft.client.renderer.entity.layers.SheepFurLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.ItemLayerModel;
import syconn.swe.Main;
import syconn.swe.client.model.ChuteModel;
import syconn.swe.client.model.ParachuteModel;
import syconn.swe.init.ModCapabilities;
import syconn.swe.item.Parachute;
import syconn.swe.util.Dyeable;

import java.awt.*;

public class ParachuteLayer<P extends Player, M extends PlayerModel<P>> extends RenderLayer<P, M> {

    private final ParachuteModel pm;
    private final ChuteModel cm;

    public ParachuteLayer(RenderLayerParent<P, M> p_117346_, EntityModelSet e) {
        super(p_117346_);
        pm = new ParachuteModel(e.bakeLayer(ParachuteModel.LAYER_LOCATION));
        cm = new ChuteModel(e.bakeLayer(ChuteModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack ps, MultiBufferSource bs, int packedLight, P p, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        ItemStack itemstack = p.getItemBySlot(EquipmentSlot.CHEST);
        if (itemstack.getItem() instanceof Parachute) {
            int i = Dyeable.getColor(itemstack.getOrCreateTag());
            float f = (float)(i >> 16 & 255) / 255.0F;
            float f1 = (float)(i >> 8 & 255) / 255.0F;
            float f2 = (float)(i & 255) / 255.0F;

            ps.pushPose();
            ps.translate(0.0F, -0.80F, 0.2F);
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(bs, RenderType.armorCutoutNoCull(new ResourceLocation(Main.MODID, "textures/entity/layers/parachute.png")), false, itemstack.hasFoil());
            this.pm.renderToBuffer(ps, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
            ps.popPose();
            p.getCapability(ModCapabilities.SPACE_SUIT).ifPresent(suit -> {
                if (suit.parachute()) {
                    ps.pushPose();
                    double seg = -0.69F / suit.chuteAnim().maxAnimLen();
                    ps.translate(0.0F, -0.11 + seg * suit.chuteAnim().animLen(), 0.2F);
                    VertexConsumer v2 = ItemRenderer.getArmorFoilBuffer(bs, RenderType.armorCutoutNoCull(new ResourceLocation(Main.MODID, "textures/entity/layers/chute.png")), false, itemstack.hasFoil());
                    this.cm.renderToBuffer(ps, v2, packedLight, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
                    ps.popPose();
                }
            });
        }
    }
}
