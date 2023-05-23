package syconn.swe.util;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;

public enum CanisterStorageType implements StringRepresentable {

    WATER("water", 0, DyeColor.LIGHT_BLUE.getFireworkColor()),
    LAVA("lava", 1, DyeColor.ORANGE.getFireworkColor()),
    O2("o2", 2, DyeColor.WHITE.getFireworkColor()),
    EMPTY("empty", 3, -1);

    final int type;
    final int color;
    final String name;

    CanisterStorageType(String name, int type, int color) {
        this.type = type;
        this.color = color;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public boolean isEmpty(){
        return this == EMPTY;
    }

    public static CanisterStorageType get(int i){
        for (CanisterStorageType type : values()){
            if (type.type == i) return type;
        } return null;
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
