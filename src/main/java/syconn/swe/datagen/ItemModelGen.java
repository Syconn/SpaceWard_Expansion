package syconn.swe.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
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
    }

    private ResourceLocation generated(){
        return new ResourceLocation("item/generated");
    }

    public ItemModelBuilder singleTexture(Item item) {
        return super.singleTexture(item.toString(), generated(), "layer0", modLoc("item/" + item));
    }
}
