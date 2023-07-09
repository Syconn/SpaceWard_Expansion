package syconn.swe.client.renders.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import syconn.swe.common.be.CanisterFillerBlockEntity;

public class CanisterBER implements BlockEntityRenderer<CanisterFillerBlockEntity> {

    private final ItemRenderer renderer;

    public CanisterBER(BlockEntityRendererProvider.Context ctx) {
        renderer = ctx.getItemRenderer();
    }

    @Override
    public void render(CanisterFillerBlockEntity p_112307_, float p_112308_, PoseStack p_112309_, MultiBufferSource p_112310_, int p_112311_, int p_112312_) {
        p_112309_.pushPose();
        int j = LevelRenderer.getLightColor(p_112307_.getLevel(), p_112307_.getBlockPos());
        renderer.renderStatic(p_112307_.getCanister(0), ItemDisplayContext.GROUND, j, OverlayTexture.NO_OVERLAY, p_112309_, p_112310_, p_112307_.getLevel(), 0);
        p_112309_.popPose();
    }
}
