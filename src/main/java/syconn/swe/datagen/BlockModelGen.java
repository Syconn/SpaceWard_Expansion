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

import java.util.HashMap;
import java.util.Map;

import static net.minecraftforge.client.model.generators.VariantBlockStateBuilder.PartialBlockstate;

public class BlockModelGen extends BlockStateProvider {

    public BlockModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Main.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        itemModels().withExistingParent(BuiltInRegistries.BLOCK.getKey(ModItems.FLUID_PIPE.get()).getPath(), modLoc("block/fluid_pipe"));

        getVariantBuilder(ModItems.FLUID_PIPE.get()).forAllStates(state -> {
            boolean north = state.getValue(PipeBlock.NORTH);
            boolean south = state.getValue(PipeBlock.SOUTH);
            boolean west = state.getValue(PipeBlock.WEST);
            boolean east = state.getValue(PipeBlock.EAST);
            CanisterStorageType type = state.getValue(FluidBaseBlock.FLUID_TYPE);

            return ConfiguredModel.builder()
                    .modelFile(generated(getPipeModel(north, south, west, east) + (type == CanisterStorageType.EMPTY && getPipeSides(north, south, west, east) > 0 ? "_empty" : "")))
                    .rotationX(getPipeRotation(north, south, west, east))
                    .uvLock(false)
                    .build();
        });
    }

    private int getPipeRotation(boolean n, boolean s, boolean w, boolean e){
        int sides = getPipeSides(n, s, e, w);
        switch (sides) {
            case 1: {
                if (n) return 0;
                if (e) return 90;
                if (s) return 180;
                if (w) return 270;
            }
            case 2: {
                if ((n && e) || (n && s)) return 0;
                if (e && s) return 90;
                if ((s && w) || (w && e)) return 180;
                if (w && n) return 270;
            }
            case 3 : {
                if (n && e && s) return 0;
                if (e && s && w) return 90;
                if (s && w && n) return 180;
                if (w && n && e) return 270;
            }
        }
        return 0;
    }

    private String getPipeModel(boolean n, boolean s, boolean w, boolean e){
        int sides = getPipeSides(n, s, w, e);
        if (sides == 2) {
            if ((n && s) || (w && e)) return "fluid_pipe_darm";
            else return "fluid_pipe_larm";
        }
        return sides == 4 ? "fluid_pipe_qarm" : sides == 3 ? "fluid_pipe_tarm" : sides == 1 ? "fluid_pipe_sarm" : "fluid_pipe";
    }

    private int getPipeSides(boolean... s){
        int c = 0;
        for (boolean v : s) if (v) c++;
        return c;
    }

    private ModelFile generated(String loc) {
        return new ModelFile.UncheckedModelFile(modLoc("block/" + loc));
    }
}
