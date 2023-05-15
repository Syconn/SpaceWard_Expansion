package syconn.swe.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import syconn.swe.Main;

public class BlockModelGen extends BlockModelProvider {

    public BlockModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Main.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        cubeAll("test", new ResourceLocation(Main.MODID, "pipe_liquid"));
    }
}
