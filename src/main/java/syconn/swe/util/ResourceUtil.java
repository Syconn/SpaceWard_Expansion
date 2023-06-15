package syconn.swe.util;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ResourceUtil {

    private static Minecraft mc = Minecraft.getInstance();

    public static ResourceLocation getFluidTexture(FluidState fluid){
        ResourceLocation loc = IClientFluidTypeExtensions.of(fluid).getFlowingTexture();
        return new ResourceLocation(loc.getNamespace(), "textures/" + loc.getPath() + ".png");
    }

    public static ResourceLocation getFluidTexture(Fluid fluid){
        ResourceLocation loc = IClientFluidTypeExtensions.of(fluid).getFlowingTexture();
        return new ResourceLocation(loc.getNamespace(), "textures/" + loc.getPath() + ".png");
    }

    public static int getColor(BlockPos pos){
        Level l = Minecraft.getInstance().level;
        if (l.getBlockEntity(pos) != null){
//            System.out.println(l.getBlockEntity(pos).getCapability(ForgeCapabilities.FLUID_HANDLER).map(handler -> handler.getFluidInTank(0).getFluid()));
        }
        return DyeColor.LIGHT_BLUE.getFireworkColor();
    }

    public static int getColor(Fluid fluid){
        if (fluid != null && fluid != Fluids.EMPTY) {
            int i = IClientFluidTypeExtensions.of(fluid).getTintColor();
            if (i != -1) return i;
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(IClientFluidTypeExtensions.of(fluid).getStillTexture());
            NativeImage input = sprite.contents().getOriginalImage();
            return -input.getPixelRGBA(0, 3);
        }
        return -1;
    }

    public static int getColorCorrected(Fluid fluid){
        return ColorUtil.getClosetColor(getColor(fluid)).getMaterialColor().col;
    }

    public static NativeImage createFluidBlockTexture(Fluid fluid){
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(IClientFluidTypeExtensions.of(fluid).getStillTexture());
        NativeImage input = sprite.contents().getOriginalImage();
        NativeImage result = new NativeImage(64, 64, false);
        for (int t = 1; t < 3; t++) {
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 15; y++) {
                    result.setPixelRGBA(16 * t + x , 1 + y, input.getPixelRGBA(x, y));
                }
            }
        }
        for (int t = 0; t < 4; t++) {
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 15; y++) {
                    result.setPixelRGBA( 16 * t + x, 16 + y, input.getPixelRGBA(x, y));
                }
            }
        }
        return result;
    }

    public static NativeImage createFluidGuiTexture(Fluid fluid){
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(IClientFluidTypeExtensions.of(fluid).getStillTexture());
        NativeImage input = sprite.contents().getOriginalImage();
        NativeImage result = new NativeImage(80, 80, false);
        for (int w = 0; w < 5; w++) {
            for (int h = 0; h < 5; h++) {
                for (int x = 0; x < 16; x++) {
                    for (int y = 0; y < 16; y++) {
                        result.setPixelRGBA(w * 16 + x, h * 16 + y, input.getPixelRGBA(x, y));
                    }
                }
            }
        }
        return result;
    }
}
