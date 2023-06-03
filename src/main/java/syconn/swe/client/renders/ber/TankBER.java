package syconn.swe.client.renders.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import syconn.swe.Main;
import syconn.swe.client.model.FluidModel;
import syconn.swe.common.be.TankBlockEntity;
import syconn.swe.util.ResourceUtil;

public class TankBER implements BlockEntityRenderer<TankBlockEntity> {

    private final FluidModel model;

    public TankBER(BlockEntityRendererProvider.Context ctx) {
        model = new FluidModel(ctx.bakeLayer(FluidModel.LAYER_LOCATION));
    }

    @Override
    public void render(TankBlockEntity be, float p_112308_, PoseStack ps, MultiBufferSource bs, int packedLight, int p) {
        if (!be.getFluidTank().isEmpty()) {
            //TODO MODIFY IMAGE WITH CODE TO PRETTY THE TEXTURES
            ps.pushPose();
            ps.translate(1, -.5, 0);
            float i = (float) (be.getFluidTank().getFluidAmount()) / be.getFluidTank().getCapacity();
            float range = 1.0f - 0.4f;
            ps.scale(1, 0.4f + i * range, 1);
            VertexConsumer vertexconsumer = bs.getBuffer(RenderType.entityCutoutNoCull(ResourceUtil.getFluidTexture(be.getFluidTank().getFluid().getFluid().defaultFluidState())));
            model.renderFluid(be.getFluidTank().getFluid().getFluid(), ps, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
            ps.popPose();
        }
    }
}
