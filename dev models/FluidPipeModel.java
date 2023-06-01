// Made with Blockbench 4.7.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class FluidPipeModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "fluidpipemodel"), "main");
	private final ModelPart top;
	private final ModelPart bot;
	private final ModelPart top_fluid;
	private final ModelPart bot_fluid;
	private final ModelPart Exporter;
	private final ModelPart Importer;
	private final ModelPart bb_main;

	public FluidPipeModel(ModelPart root) {
		this.top = root.getChild("top");
		this.bot = root.getChild("bot");
		this.top_fluid = root.getChild("top_fluid");
		this.bot_fluid = root.getChild("bot_fluid");
		this.Exporter = root.getChild("Exporter");
		this.Importer = root.getChild("Importer");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition top = partdefinition.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 20).addBox(-11.0F, -16.0F, 5.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bot = partdefinition.addOrReplaceChild("bot", CubeListBuilder.create().texOffs(0, 20).addBox(-11.0F, -5.0F, 5.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition top_fluid = partdefinition.addOrReplaceChild("top_fluid", CubeListBuilder.create().texOffs(0, 0).addBox(-10.8F, -15.9F, 5.2F, 5.5F, 8.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bot_fluid = partdefinition.addOrReplaceChild("bot_fluid", CubeListBuilder.create().texOffs(1, 0).addBox(-10.8F, -8.9F, 5.2F, 5.5F, 8.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Exporter = partdefinition.addOrReplaceChild("Exporter", CubeListBuilder.create().texOffs(38, 39).addBox(-12.0F, -11.0F, 5.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(33, 34).addBox(-16.0F, -13.0F, 3.0F, 2.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(35, 36).addBox(-14.0F, -12.0F, 4.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Importer = partdefinition.addOrReplaceChild("Importer", CubeListBuilder.create().texOffs(35, 33).addBox(-2.75F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(35, 33).addBox(0.25F, -5.0F, -5.0F, 2.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(35, 33).addBox(-0.75F, -4.0F, -4.0F, 1.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.25F, 16.0F, 8.0F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(24, 0).addBox(-11.0F, -11.0F, 5.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		top.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		top_fluid.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bot_fluid.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Exporter.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Importer.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}