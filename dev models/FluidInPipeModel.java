// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class FluidInPipeModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "fluidinpipemodel"), "main");
	private final ModelPart east;
	private final ModelPart west;
	private final ModelPart north;
	private final ModelPart south;

	public FluidInPipeModel(ModelPart root) {
		this.east = root.getChild("east");
		this.west = root.getChild("west");
		this.north = root.getChild("north");
		this.south = root.getChild("south");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition east = partdefinition.addOrReplaceChild("east", CubeListBuilder.create().texOffs(0, 0).addBox(-16.3F, -10.9F, 5.2F, 11.1F, 5.8F, 5.7F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition west = partdefinition.addOrReplaceChild("west", CubeListBuilder.create().texOffs(0, 0).addBox(-10.9F, -10.9F, 5.2F, 11.1F, 5.8F, 5.7F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition north = partdefinition.addOrReplaceChild("north", CubeListBuilder.create().texOffs(-1, 0).addBox(-10.9F, -10.9F, -0.2F, 5.7F, 5.8F, 11.1F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition south = partdefinition.addOrReplaceChild("south", CubeListBuilder.create().texOffs(-1, 0).addBox(-10.9F, -10.9F, 5.2F, 5.7F, 5.8F, 11.1F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		east.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		west.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		north.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		south.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}