package syconn.swe.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.resources.ResourceLocation;
import syconn.swe.Main;
import syconn.swe.common.crafting.DyedParachuteRecipe;
import syconn.swe.common.crafting.RefillingCanisterRecipe;

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
        registration.getCraftingCategory().addCategoryExtension(RefillingCanisterRecipe.class, CanisterRecipeWrapper::new);
    }
}
