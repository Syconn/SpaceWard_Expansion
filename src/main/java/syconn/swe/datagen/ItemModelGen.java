package syconn.swe.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import syconn.swe.Main;
import syconn.swe.init.ModItems;

public class ItemModelGen extends ItemModelProvider {

    public ItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Main.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTexture(ModItems.SPACE_HELMET.get());
        singleTexture(ModItems.SPACE_CHESTPLATE.get());
        singleTexture(ModItems.SPACE_LEGGINGS.get());
        singleTexture(ModItems.SPACE_BOOTS.get());
        singleTexture(ModItems.WRENCH.get());
        singleTexture(ModItems.DIAMOND_UPGRADE.get());
        singleTexture(ModItems.GOLD_UPGRADE.get());
        singleTexture(ModItems.IRON_UPGRADE.get());
        singleTexture(ModItems.EMERALD_UPGRADE.get());
        singleTexture(ModItems.NETHERITE_UPGRADE.get());

        ItemModelBuilder model = getBuilder(ModItems.CANISTER.get().toString()).parent(generate()).texture("layer0", modLoc("item/canister"));

        for (int i = 1; i <= 5; i++){
            getBuilder(ModItems.CANISTER.get().toString() + i).parent(generate()).texture("layer1", modLoc("item/fluid_stage_" + (i + 1))).texture("layer0", modLoc("item/canister"));
            model.override().predicate(new ResourceLocation(Main.MODID, "stage"), i).model(generated("item/" + ModItems.CANISTER.get() + i)).end();
        }
        getBuilder(ModItems.CANISTER.get() + "full").parent(generate()).texture("layer1", modLoc("item/fluid_full")).texture("layer0", modLoc("item/canister"));
        model.override().predicate(new ResourceLocation(Main.MODID, "stage"), 6.0F).model(generated("item/" + ModItems.CANISTER.get() + "full"));
    }

    private ResourceLocation generated(){
        return new ResourceLocation("item/generated");
    }

    private ModelFile generated(String loc) {
        return new ModelFile.UncheckedModelFile(modLoc(loc));
    }

    private ModelFile.UncheckedModelFile generate(){
        return new ModelFile.UncheckedModelFile("item/generated");
    }

    public ItemModelBuilder singleTexture(Item item) {
        return super.singleTexture(item.toString(), generated(), "layer0", modLoc("item/" + item));
    }
}
