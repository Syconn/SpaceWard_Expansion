package syconn.swe.worldgen.dimension;

import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.level.Level;
import syconn.swe.init.ModDim;
import syconn.swe.item.SpaceArmor;

public enum DimSettings {

    MC(0.08, true),
    MOON(0.014, false);

    private final double g;
    private final boolean air;

    DimSettings(double g, boolean a) {
        this.g = g;
        this.air = a;
    }

    public double getG() {
        return g;
    }

    public boolean breathable(){
        return air;
    }

    public static DimSettings get(ResourceKey<Level> l){
        if (l.equals(ModDim.MOON_KEY)) return MOON;
        else return MC;
    }

    public static boolean displayOxygen(Player p){
        return !(SpaceArmor.hasFullKit(p) || get(p.getLevel().dimension()).breathable());
    }
}
