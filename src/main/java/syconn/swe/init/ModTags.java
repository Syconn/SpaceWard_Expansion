package syconn.swe.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import syconn.swe.Main;

public class ModTags {

    public static final TagKey<Item> GLASS = ItemTags.create(new ResourceLocation(Main.MODID, "glass"));
}
