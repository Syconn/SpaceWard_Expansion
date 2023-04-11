package syconn.swe.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public interface ISpaceSuit extends INBTSerializable<CompoundTag> {

    boolean parachute();
    void parachute(boolean p);
}
