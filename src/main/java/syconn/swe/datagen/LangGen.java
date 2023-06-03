package syconn.swe.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import syconn.swe.Main;
import syconn.swe.init.ModItems;

public class LangGen extends LanguageProvider {

    public LangGen(PackOutput output) {
        super(output, Main.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModItems.PARACHUTE.get(), "Parachute");
        add(ModItems.SPACE_HELMET.get(), "Space Helmet");
        add(ModItems.SPACE_BOOTS.get(), "Space Boots");
        add(ModItems.SPACE_CHESTPLATE.get(), "Space Chestplate");
        add(ModItems.SPACE_LEGGINGS.get(), "Space Leggings");
        add(ModItems.CANISTER.get(), "Canister");
        add(ModItems.WRENCH.get(), "Wrench");

        add(ModItems.FLUID_PIPE.get(), "Fluid Pipe");
        add(ModItems.FLUID_TANK.get(), "Fluid Tank");

        add("itemGroup.space", "Spaceward Expansion");
    }
}
