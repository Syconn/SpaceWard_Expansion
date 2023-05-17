package syconn.swe.client.renders.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import syconn.swe.Main;
import syconn.swe.capabilities.ISpaceSuit;
import syconn.swe.client.model.ChuteModel;
import syconn.swe.client.model.ParachuteModel;
import syconn.swe.client.model.TankModel;
import syconn.swe.init.ModCapabilities;
import syconn.swe.item.Canister;
import syconn.swe.item.EquipmentItem;
import syconn.swe.item.Parachute;
import syconn.swe.item.SpaceArmor;
import syconn.swe.util.CanisterStorageType;
import syconn.swe.util.Dyeable;
import syconn.swe.util.Helper;

public class SpaceSuitLayer<P extends Player, M extends PlayerModel<P>> extends RenderLayer<P, M> {

    private final ParachuteModel pm;
    private final ChuteModel cm;
    private final TankModel tm;

    public SpaceSuitLayer(RenderLayerParent<P, M> p_117346_, EntityModelSet e) {
        super(p_117346_);
        pm = new ParachuteModel(e.bakeLayer(ParachuteModel.LAYER_LOCATION));
        cm = new ChuteModel(e.bakeLayer(ChuteModel.LAYER_LOCATION));
        tm = new TankModel(e.bakeLayer(TankModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack ps, MultiBufferSource bs, int packedLight, P p, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        ItemStack itemstack;
        if (SpaceArmor.hasParachute(p)) itemstack = Helper.inventory(p).getItemBySlot(EquipmentItem.Slot.PARACHUTE);
        else itemstack = p.getItemBySlot(EquipmentSlot.CHEST);

        if (itemstack.getItem() instanceof Parachute) {
            int i = Dyeable.getColor(itemstack);
            float f = (float)(i >> 16 & 255) / 255.0F;
            float f1 = (float)(i >> 8 & 255) / 255.0F;
            float f2 = (float)(i & 255) / 255.0F;

            ps.pushPose();
            ps.translate(0.0F, -0.80F, 0.2F);
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(bs, RenderType.armorCutoutNoCull(new ResourceLocation(Main.MODID, "textures/entity/layers/parachute.png")), false, itemstack.hasFoil());
            this.pm.renderToBuffer(ps, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
            ps.popPose();
            if (p.getCapability(ModCapabilities.SPACE_SUIT).isPresent()) {
                ISpaceSuit suit = p.getCapability(ModCapabilities.SPACE_SUIT).resolve().get();
                if (suit.parachute()) {
                    ps.pushPose();
                    double seg = -0.69F / suit.chuteAnim().maxAnimLen();
                    ps.translate(0.0F, -0.11 + seg * suit.chuteAnim().animLen(), 0.2F);
                    VertexConsumer v2 = ItemRenderer.getArmorFoilBuffer(bs, RenderType.armorCutoutNoCull(new ResourceLocation(Main.MODID, "textures/entity/layers/chute.png")), false, itemstack.hasFoil());
                    this.cm.renderToBuffer(ps, v2, packedLight, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
                    ps.popPose();
                }
            }
        }

        itemstack = SpaceArmor.getGear(EquipmentItem.Slot.TANK, p);
        if (itemstack != null && itemstack.getItem() instanceof Canister) {
            int i = Canister.getType(itemstack).getColor();
            float f = (float) (i >> 16 & 255) / 255.0F;
            float f1 = (float) (i >> 8 & 255) / 255.0F;
            float f2 = (float) (i & 255) / 255.0F;
            ps.pushPose();
            ps.translate(0F, -0.80F, 0.3F);
            ps.mulPose(Axis.YP.rotationDegrees(180F));
            VertexConsumer v2 = ItemRenderer.getArmorFoilBuffer(bs, RenderType.armorCutoutNoCull(new ResourceLocation(Main.MODID, "textures/entity/layers/tank.png")), false, false);
            tm.renderToBuffer(ps, v2, packedLight, OverlayTexture.NO_OVERLAY, f, f1, f2, Canister.getType(itemstack) == CanisterStorageType.EMPTY ? 255F : 230.0F);
            ps.popPose();
        }
    }
}