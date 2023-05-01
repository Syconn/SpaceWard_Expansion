package syconn.swe.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import syconn.swe.util.Animator;

public class SpaceSuit implements ISpaceSuit {

    private static final String PARACHUTE_NBT = "parachute";
    private static final String CHUTE_NBT = "animchute";
    private static final String OXYGEN_NBT = "oxygen";

    private boolean parachute;
    private Animator chute = new Animator(100);
    private int oxygen = maxO2();

    @Override
    public boolean parachute() {
        return parachute;
    }

    @Override
    public Animator chuteAnim() {
        return chute;
    }

    @Override
    public void parachute(boolean p) {
        if (p) chute.start();
        parachute = p;
    }

    @Override
    public int O2() {
        return oxygen;
    }

    @Override
    public int maxO2() {
        return 300;
    }

    @Override
    public void setO2(int o2) {
        oxygen = Mth.clamp(o2, 0, maxO2());
    }

    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag t = new CompoundTag();
        t.putBoolean(PARACHUTE_NBT, parachute);
        t.put(CHUTE_NBT, chute.serializeNBT());
        t.putInt(OXYGEN_NBT, oxygen);
        return t;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        parachute = nbt.getBoolean(PARACHUTE_NBT);
        chute = new Animator(nbt.getCompound(CHUTE_NBT));
        oxygen = nbt.getInt(OXYGEN_NBT);
    }
}


