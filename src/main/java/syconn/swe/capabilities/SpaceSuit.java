package syconn.swe.capabilities;

import net.minecraft.nbt.CompoundTag;

public class SpaceSuit implements ISpaceSuit {

    private static final String PARACHUTE_NBT = "parachute";

    private boolean parachute;

    @Override
    public boolean parachute() {
        return parachute;
    }

    @Override
    public void parachute(boolean p) {
        parachute = p;
    }

    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag t = new CompoundTag();
        t.putBoolean(PARACHUTE_NBT, parachute);
        return t;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        parachute(nbt.getBoolean(PARACHUTE_NBT));
    }
}


