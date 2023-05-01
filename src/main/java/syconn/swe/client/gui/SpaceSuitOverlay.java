package syconn.swe.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.common.ForgeMod;
import syconn.swe.capabilities.ISpaceSuit;
import syconn.swe.init.ModCapabilities;
import syconn.swe.worldgen.dimension.DimSettings;

import static net.minecraft.client.gui.GuiComponent.blit;

public class SpaceSuitOverlay {

    private static final Minecraft minecraft = Minecraft.getInstance();

    public static IGuiOverlay O2_OVERLAY = (gui, poseStack, partialTick, width, height) -> {
        Player player = (Player) minecraft.getCameraEntity();
        if (player != null && !gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements() && DimSettings.displayOxygen(player)) {
            player.getCapability(ModCapabilities.SPACE_SUIT).ifPresent(iSpaceSuit -> {
                RenderSystem.enableBlend();
                int left = width / 2 + 91;
                int top = height - 49;
                int air = iSpaceSuit.O2();
                int full = Mth.ceil((double) (air - 2) * 10.0D / (double) iSpaceSuit.maxO2());
                int partial = Mth.ceil((double) air * 10.0D / (double) iSpaceSuit.maxO2()) - full;
                for (int i = 0; i < full + partial; ++i)
                {
                    blit(poseStack, left - i * 8 - 9, top, (i < full ? 16 : 25), 18, 9, 9);
                }
                RenderSystem.disableBlend();
            });
        }
    };
}
