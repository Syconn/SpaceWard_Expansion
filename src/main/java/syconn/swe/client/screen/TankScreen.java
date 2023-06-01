package syconn.swe.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.FurnaceScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import syconn.swe.Main;
import syconn.swe.common.container.TankMenu;
import syconn.swe.util.ResourceUtil;

public class TankScreen extends AbstractContainerScreen<TankMenu> {

    private static final ResourceLocation BG = new ResourceLocation(Main.MODID, "textures/gui/tank.png");

    public TankScreen(TankMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Override
    protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) {
        drawString(p_97808_, font, menu.getBE().getFluidTank().getFluidAmount() + "mb", this.titleLabelX + 90, this.titleLabelY, 4210752);
    }

    @Override
    protected void renderBg(PoseStack pose, float p_97788_, int p_97789_, int p_97790_) {
        RenderSystem.setShaderTexture(0, BG);
        blit(pose, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

//        pose.pushPose();
//        pose.scale(0.5f, 0.5f, 0.5f);
//        RenderSystem.setShaderTexture(0, ResourceUtil.getBlockTexture(Blocks.WATER.defaultBlockState(), Direction.UP));
//        blit(pose, this.leftPos, this.topPos, 0, 0, 512, 512);
//        pose.popPose();

        System.out.println(Minecraft.getInstance().getBlockRenderer().getBlockModel(Blocks.WATER.defaultBlockState()).getParticleIcon());

        menu.getBE().getFluidTank().getFluidInTank(0).getFluid();

        // Fluid Bars
        RenderSystem.setShaderTexture(0, BG);
        blit(pose, leftPos + 34, topPos + 8, 176, 0, 6, 70);
    }

    @Override
    public void render(PoseStack p_97795_, int p_97796_, int p_97797_, float p_97798_) {
        super.render(p_97795_, p_97796_, p_97797_, p_97798_);
    }
}