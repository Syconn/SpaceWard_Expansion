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
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Main.MODID, "tank"), "main");
	private final ModelPart chamber1;
	private final ModelPart chamber2;
	private final ModelPart fluid;
	private final ModelPart top;

	public TankModel(ModelPart root)
	{
		this(root, RenderType::entityCutoutNoCull);
	}

	public TankModel(ModelPart root, Function<ResourceLocation, RenderType> renderType) {
		super(renderType);
		this.chamber1 = root.getChild("chamber1");
		this.chamber2 = root.getChild("chamber2");
		this.fluid = root.getChild("fluid");
		this.top = root.getChild("top");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition chamber1 = partdefinition.addOrReplaceChild("chamber1", CubeListBuilder.create().texOffs(20, 0).addBox(-2.0F, -12.0F, 3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(20, 17).addBox(-2.0F, -1.0F, 3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(10, 0).addBox(-2.0F, -11.0F, 3.0F, 1.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(22, 31).addBox(-1.0F, -11.0F, 3.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 31).addBox(-1.0F, -11.0F, 6.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(1.0F, -11.0F, 3.0F, 1.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 24.0F, -5.0F));

		PartDefinition chamber2 = partdefinition.addOrReplaceChild("chamber2", CubeListBuilder.create().texOffs(20, 0).addBox(-2.0F, -12.0F, 3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(20, 17).addBox(-2.0F, -1.0F, 3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(10, 0).addBox(-2.0F, -11.0F, 3.0F, 1.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(22, 31).addBox(-1.0F, -11.0F, 3.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 31).addBox(-1.0F, -11.0F, 6.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(1.0F, -11.0F, 3.0F, 1.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 24.0F, -5.0F));

		PartDefinition fluid = partdefinition.addOrReplaceChild("fluid", CubeListBuilder.create().texOffs(-1, 29).addBox(-5.9F, -11.0F, -1.9F, 3.8F, 10.0F, 3.8F, new CubeDeformation(0.0F))
		.texOffs(-1, 29).addBox(2.1F, -11.0F, -1.9F, 3.8F, 10.0F, 3.8F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition top = partdefinition.addOrReplaceChild("top", CubeListBuilder.create().texOffs(34, 3).addBox(-5.0F, -15.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 10).addBox(-6.0F, -16.0F, 1.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(6, 0).addBox(3.0F, -13.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(18, 27).addBox(-3.0F, -14.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		chamber1.render(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
		chamber2.render(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
		fluid.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		top.render(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}