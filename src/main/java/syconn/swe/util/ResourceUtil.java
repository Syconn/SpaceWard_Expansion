package syconn.swe.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class ResourceUtil {

    public static ResourceLocation getFluidTexture(FluidState fluid){
        ResourceLocation loc = IClientFluidTypeExtensions.of(fluid).getFlowingTexture();
        return new ResourceLocation(loc.getNamespace(), "textures/" + loc.getPath() + ".png");
    }
}
