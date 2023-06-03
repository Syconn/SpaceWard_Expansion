package syconn.swe.client.renders.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import syconn.swe.Main;
import syconn.swe.client.model.FluidPipeModel;
import syconn.swe.common.be.PipeBlockEntity;
import syconn.swe.util.Helper;
import syconn.swe.util.PipeModule;

public class PipeBER implements BlockEntityRenderer<PipeBlockEntity> {

    private final FluidPipeModel pm;

    public PipeBER(BlockEntityRendererProvider.Context ctx) {
        pm = new FluidPipeModel(ctx.bakeLayer(FluidPipeModel.LAYER_LOCATION));
    }

    @Override
    public void render(PipeBlockEntity be, float p_112308_, PoseStack ps, MultiBufferSource bs, int packedLight, int p_112312_) {
        PipeModule mod = new PipeModule(be.getBlockState());
        if (mod.isDown() || mod.isUp()) {
            int i = IClientFluidTypeExtensions.of(be.getFluidTank().getFluidInTank(0).getFluid().defaultFluidState()).getTintColor();
            float f = (float)(i >> 16 & 255) / 255.0F;
            float f1 = (float)(i >> 8 & 255) / 255.0F;
            float f2 = (float)(i & 255) / 255.0F;
            ps.pushPose();
            ps.translate(1, -0.5f, 0);
            VertexConsumer vertexconsumer = bs.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation(Main.MODID, "textures/models/ber/fluid_pipe.png")));
            this.pm.render(be.getBlockState(), be, ps, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
            ps.popPose();
        }

        if (!be.getImporter().equals(BlockPos.ZERO)) {
            Direction d = Helper.dirToBlockPos(be.getBlockPos(), be.getImporter());
            ps.pushPose();
            if (d == Direction.EAST) ps.translate(1, -0.5f, 0);
            if (d == Direction.WEST) ps.translate(0, -0.5f, 1);
            if (d == Direction.SOUTH) ps.translate(1, -0.5f, 1);
            if (d == Direction.NORTH) ps.translate(0, -0.5f, 0);
            ps.mulPose(Axis.YP.rotationDegrees(Helper.rotationFromDir(d)));
            VertexConsumer vertexconsumer = bs.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation(Main.MODID, "textures/models/ber/fluid_pipe.png")));
            this.pm.renderImport(ps, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
            ps.popPose();
        }

        if (!be.getExporter().equals(BlockPos.ZERO)) {
            Direction d = Helper.dirToBlockPos(be.getBlockPos(), be.getExporter());
            ps.pushPose();
            if (d == Direction.EAST) ps.translate(1.7, -0.5f, 0);
            if (d == Direction.WEST) ps.translate(-0.7, -0.5f, 1);
            if (d == Direction.SOUTH) ps.translate(1, -0.5f, 1.7);
            if (d == Direction.NORTH) ps.translate(0, -0.5f, -.7);
            ps.mulPose(Axis.YP.rotationDegrees(Helper.rotationFromDir(d)));
            VertexConsumer vertexconsumer = bs.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation(Main.MODID, "textures/models/ber/fluid_pipe.png")));
            this.pm.renderExport(ps, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
            ps.popPose();
        }
    }
}
