package syconn.swe.util;

import net.minecraft.world.item.DyeColor;

public enum CanisterStorageType {

    WATER(0, DyeColor.LIGHT_BLUE.getFireworkColor()),
    LAVA(1, DyeColor.ORANGE.getFireworkColor()),
    O2(2, DyeColor.WHITE.getFireworkColor()),
    EMPTY(3, -1);

    final int type;
    final int color;

    CanisterStorageType(int type, int color) {
        this.type = type;
        this.color = color;
    }

    public int getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public static CanisterStorageType get(int i){
        for (CanisterStorageType type : values()){
            if (type.type == i) return type;
        } return null;
    }
}
