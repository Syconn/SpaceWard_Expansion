package syconn.swe.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.item.crafting.TippedArrowRecipe;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import syconn.swe.Main;
import syconn.swe.common.crafting.DyedParachuteRecipe;
import syconn.swe.common.crafting.RefillingCanisterRecipe;

public class ModRecipeSerializer {

    public static final DeferredRegister<RecipeSerializer<?>> REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Main.MODID);

    public static final RegistryObject<SimpleCraftingRecipeSerializer<DyedParachuteRecipe>> PARACHUTE_RECIPE = REGISTER.register("parachute_recipe", () -> new SimpleCraftingRecipeSerializer<>(DyedParachuteRecipe::new));

    public static final RegistryObject<SimpleCraftingRecipeSerializer<RefillingCanisterRecipe>> REFILLING_CANISTER = REGISTER.register("refilling_canister_recipe", () -> new SimpleCraftingRecipeSerializer<>(RefillingCanisterRecipe::new));
}
