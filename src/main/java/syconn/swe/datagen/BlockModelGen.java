package syconn.swe.datagen;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import syconn.swe.Main;
import syconn.swe.block.FluidBaseBlock;
import syconn.swe.block.FluidPipe;
import syconn.swe.init.ModItems;
import syconn.swe.util.CanisterStorageType;
import syconn.swe.util.PipeModule;

import java.util.HashMap;
import java.util.Map;

import static net.minecraftforge.client.model.generators.VariantBlockStateBuilder.PartialBlockstate;

public class BlockModelGen extends BlockStateProvider {

    public BlockModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Main.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(ModItems.FLUID_PIPE.get()).forAllStates(state -> {
            PipeModule mod = new PipeModule(state);
            return ConfiguredModel.builder()
                    .modelFile(generated(mod.getModel()))
                    .rotationY(mod.getYRotation())
                    .uvLock(false)
                    .build();
        });

        itemModels().withExistingParent(BuiltInRegistries.BLOCK.getKey(ModItems.FLUID_PIPE.get()).getPath(), modLoc("block/fluid_pipe"));
    }

    private ModelFile generated(String loc) {
        return new ModelFile.UncheckedModelFile(modLoc("block/" + loc));
    }
}
