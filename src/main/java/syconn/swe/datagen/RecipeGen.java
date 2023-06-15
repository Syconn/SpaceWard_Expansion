package syconn.swe.datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import syconn.swe.init.ModItems;
import syconn.swe.init.ModTags;

import java.util.function.Consumer;

public class RecipeGen extends RecipeProvider {

    public RecipeGen(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NETHERITE_UPGRADE.get())
                .pattern(" n ")
                .pattern("nrn")
                .pattern(" n ")
                .define('n', Items.NETHERITE_INGOT)
                .define('r', Items.REDSTONE)
                .unlockedBy("has_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(Items.NETHERITE_INGOT).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_UPGRADE.get())
                .pattern(" n ")
                .pattern("nrn")
                .pattern(" n ")
                .define('n', Items.DIAMOND)
                .define('r', Items.REDSTONE)
                .unlockedBy("has_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(Items.DIAMOND).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EMERALD_UPGRADE.get())
                .pattern(" n ")
                .pattern("nrn")
                .pattern(" n ")
                .define('n', Items.EMERALD)
                .define('r', Items.REDSTONE)
                .unlockedBy("has_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(Items.EMERALD).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLD_UPGRADE.get())
                .pattern(" n ")
                .pattern("nrn")
                .pattern(" n ")
                .define('n', Items.GOLD_INGOT)
                .define('r', Items.REDSTONE)
                .unlockedBy("has_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(Items.GOLD_INGOT).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_UPGRADE.get())
                .pattern(" n ")
                .pattern("nrn")
                .pattern(" n ")
                .define('n', Items.IRON_INGOT)
                .define('r', Items.REDSTONE)
                .unlockedBy("has_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(Items.IRON_INGOT).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModItems.FLUID_PIPE.get(), 16)
                .pattern("nnn")
                .pattern("nrn")
                .pattern("nnn")
                .define('n', Tags.Items.GLASS_PANES)
                .define('r', Items.BUCKET)
                .unlockedBy("has_bucket", inventoryTrigger(ItemPredicate.Builder.item().of(Items.BUCKET).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModItems.FLUID_TANK.get())
                .pattern("bbb")
                .pattern("nrn")
                .pattern("bbb")
                .define('n', ModTags.GLASS)
                .define('r', Items.BUCKET)
                .define('b', Items.IRON_BLOCK)
                .unlockedBy("has_bucket", inventoryTrigger(ItemPredicate.Builder.item().of(Items.BUCKET).build()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.WRENCH.get())
                .requires(Items.IRON_INGOT, 2)
                .requires(Items.GOLD_INGOT)
                .unlockedBy("has_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(Items.IRON_INGOT).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.CANISTER.get(), 16)
                .pattern("nnn")
                .pattern("nrn")
                .pattern("nnn")
                .define('n', Items.IRON_INGOT)
                .define('r', Items.BUCKET)
                .unlockedBy("has_bucket", inventoryTrigger(ItemPredicate.Builder.item().of(Items.BUCKET).build()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.PARACHUTE.get(), 1)
                .pattern("www")
                .pattern("s s")
                .pattern(" s ")
                .define('w', ItemTags.WOOL)
                .define('s', Items.STRING)
                .unlockedBy("has_wool", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.WOOL).build()))
                .save(consumer);
    }
}
