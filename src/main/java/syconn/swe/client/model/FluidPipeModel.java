package syconn.swe.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import syconn.swe.Main;
import syconn.swe.util.PipeModule;

public class FluidPipeModel extends Model {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Main.MODID, "fluidpipemodel"), "main");
	private final ModelPart top;
	private final ModelPart top_fluid;
	private final ModelPart bot;
	private final ModelPart bot_fluid;

	public FluidPipeModel(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.top = root.getChild("top");
		this.top_fluid = root.getChild("top_fluid");
		this.bot = root.getChild("bot");
		this.bot_fluid = root.getChild("bot_fluid");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition top = partdefinition.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 11).addBox(-11.0F, -16.0F, 5.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition top_fluid = partdefinition.addOrReplaceChild("top_fluid", CubeListBuilder.create().texOffs(0, 0).addBox(-10.9F, -15.9F, 5.1F, 5.8F, 4.8F, 5.8F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bot = partdefinition.addOrReplaceChild("bot", CubeListBuilder.create().texOffs(0, 11).addBox(-11.0F, -5.0F, 5.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bot_fluid = partdefinition.addOrReplaceChild("bot_fluid", CubeListBuilder.create().texOffs(0, 0).addBox(-10.9F, -4.9F, 5.1F, 5.8F, 4.8F, 5.8F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		top.render(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0f, 1.0f, 1.0f, alpha);
		top_fluid.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bot.render(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0f, 1.0f, 1.0f, alpha);
		bot_fluid.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void renderStateToBuffer(BlockState state, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		PipeModule mod = new PipeModule(state);
		if (mod.isUp()) bot.render(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0f, 1.0f, 1.0f, alpha);
		if (mod.isUp() && !mod.getType().isEmpty()) bot_fluid.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		if (mod.isDown()) top.render(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0f, 1.0f, 1.0f, alpha);
		if (mod.isDown() && !mod.getType().isEmpty()) top_fluid.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}