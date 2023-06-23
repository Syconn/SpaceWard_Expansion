package syconn.swe.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.resources.ResourceLocation;
import syconn.swe.Main;
import syconn.swe.common.crafting.DyedParachuteRecipe;

@JeiPlugin
public class SpaceJEI implements IModPlugin
{
    @Override
    public ResourceLocation getPluginUid()
    {
        return new ResourceLocation(Main.MODID, "crafting");
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        registration.getCraftingCategory().addCategoryExtension(DyedParachuteRecipe.class, ParachuteRecipeWrapper::new);
    }
}
