package syconn.swe.util;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public class ResourceUtil {

    public static ResourceLocation getBlockTexture(BlockState state, Direction dir){
        RandomSource randomsource = RandomSource.create();
        TextureAtlasSprite sprite = Minecraft.getInstance().getBlockRenderer().getBlockModel(state).getParticleIcon();

        return Minecraft.getInstance().getTextureManager().register("duddsfsdfsdf", new DynamicTexture(sprite.contents().getOriginalImage()));
    }
}
