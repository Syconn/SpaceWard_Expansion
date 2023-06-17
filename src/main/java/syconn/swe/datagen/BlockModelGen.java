package syconn.swe.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import syconn.swe.Main;
import syconn.swe.init.ModInit;
import syconn.swe.util.data.PipeModule;

public class BlockModelGen extends BlockStateProvider {

    public BlockModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Main.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(ModInit.FLUID_PIPE.get()).forAllStates(state -> {
            PipeModule mod = new PipeModule(state);
            return ConfiguredModel.builder()
                    .modelFile(generated(mod.getModel()))
                    .rotationY(mod.getYRotation())
                    .uvLock(false)
                    .build();
        });
        simpleBlock(ModInit.FLUID_TANK.get(), generated("fluid_tank"));

        itemModels().withExistingParent(BuiltInRegistries.BLOCK.getKey(ModInit.FLUID_PIPE.get()).getPath(), modLoc("block/fluid_pipe"));
        itemModels().withExistingParent(BuiltInRegistries.BLOCK.getKey(ModInit.FLUID_TANK.get()).getPath(), modLoc("block/fluid_tank"));
    }

    private ModelFile generated(String loc) {
        return new ModelFile.UncheckedModelFile(modLoc("block/" + loc));
    }
}
