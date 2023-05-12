package syconn.swe.common.data.types;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import syconn.swe.init.ModDim;
import syconn.swe.item.SpaceArmor;
import syconn.swe.worldgen.dimension.DimSettings;

public record DimensionSettings(int gravity, boolean breathable) {

    public boolean displayOxygen(Player p){
        return !(SpaceArmor.hasFullKit(p) || breathable());
    }
}
