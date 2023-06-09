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
import syconn.swe.Main;

import java.util.function.Function;

public class TankModel extends Model {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Main.MODID, "tank"), "main");
	private final ModelPart chamber2;
	private final ModelPart chamber1;
	private final ModelPart fluid;
	private final ModelPart top;
	private final ModelPart glass;

	public TankModel(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.chamber2 = root.getChild("chamber2");
		this.chamber1 = root.getChild("chamber1");
		this.fluid = root.getChild("fluid");
		this.top = root.getChild("top");
		this.glass = root.getChild("glass");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition chamber2 = partdefinition.addOrReplaceChild("chamber2", CubeListBuilder.create().texOffs(20, 0).addBox(-2.0F, -12.0F, 3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(20, 17).addBox(-2.0F, -1.0F, 3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(14, 3).addBox(-2.0F, -11.0F, 6.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 3).addBox(1.0F, -11.0F, 6.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(28, 31).addBox(1.0F, -11.0F, 3.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(33, 31).addBox(-2.0F, -11.0F, 3.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 24.0F, -5.0F));

		PartDefinition chamber1 = partdefinition.addOrReplaceChild("chamber1", CubeListBuilder.create().texOffs(20, 0).addBox(-2.0F, -12.0F, 3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(20, 17).addBox(-2.0F, -1.0F, 3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(14, 3).addBox(-2.0F, -11.0F, 6.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 3).addBox(1.0F, -11.0F, 6.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(28, 31).addBox(1.0F, -11.0F, 3.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(33, 31).addBox(-2.0F, -11.0F, 3.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 24.0F, -5.0F));

		PartDefinition fluid = partdefinition.addOrReplaceChild("fluid", CubeListBuilder.create().texOffs(-1, 29).addBox(-5.9F, -11.0F, -1.9F, 3.8F, 10.0F, 3.8F, new CubeDeformation(0.0F))
				.texOffs(-1, 29).addBox(2.1F, -11.0F, -1.9F, 3.8F, 10.0F, 3.8F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition top = partdefinition.addOrReplaceChild("top", CubeListBuilder.create().texOffs(34, 3).addBox(-5.0F, -15.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(16, 10).addBox(-6.0F, -16.0F, 1.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(6, 0).addBox(3.0F, -13.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(18, 27).addBox(-3.0F, -14.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition glass = partdefinition.addOrReplaceChild("glass", CubeListBuilder.create().texOffs(22, 31).addBox(3.0F, -11.0F, -2.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(16, 31).addBox(3.0F, -11.0F, 1.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(16, 31).addBox(-5.0F, -11.0F, 1.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(22, 31).addBox(-5.0F, -11.0F, -2.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = glass.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 31).addBox(-1.0F, -5.0F, -0.5F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, -6.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r2 = glass.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 31).addBox(0.5F, -5.0F, -1.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -6.0F, 1.5F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r3 = glass.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(16, 31).addBox(0.5F, -5.0F, -1.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -6.0F, 1.5F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r4 = glass.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(16, 31).addBox(-1.0F, -5.0F, -0.5F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -6.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, float p_103115_, float p_103116_, float p_103117_, float p_103118_) {

	}

	public void render(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float[] red, float[] green, float[] blue, float alpha) {
		chamber1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red[1], green[1], blue[1], 1.0F);
		chamber2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red[1], green[1], blue[1], 1.0F);
		glass.render(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
		fluid.render(poseStack, vertexConsumer, packedLight, packedOverlay, red[0], green[0], blue[0], alpha);
		top.render(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}