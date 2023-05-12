package syconn.swe.common.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import syconn.swe.Main;
import syconn.swe.common.data.types.DimensionSettings;

import java.util.Map;

public class DimSettingsManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    public DimSettingsManager() {
        super(GSON, "dim_settings");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> elementMap, ResourceManager p_10794_, ProfilerFiller p_10795_) {
        elementMap.forEach(((resourceLocation, jsonElement) -> {
            System.out.println(jsonElement.getAsJsonObject());
        }));
        System.out.println("applied");
    }

    public static DimensionSettings getSettings(){
        return null;
    }
}
