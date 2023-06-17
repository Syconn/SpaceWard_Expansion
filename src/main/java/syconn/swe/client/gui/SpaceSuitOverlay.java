package syconn.swe.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import syconn.swe.common.data.DimSettingsManager;
import syconn.swe.init.ModCapabilities;
import syconn.swe.init.ModFluids;
import syconn.swe.item.Canister;
import syconn.swe.item.SpaceArmor;
import syconn.swe.util.data.SpaceSlot;

import static net.minecraft.client.gui.GuiComponent.blit;

public class SpaceSuitOverlay {

    private static final Minecraft minecraft = Minecraft.getInstance();

    public static IGuiOverlay O2_OVERLAY = (gui, poseStack, partialTick, width, height) -> {
        Player player = (Player) minecraft.getCameraEntity();
        if (player != null && !gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements() && displayOxygen(player)) {
            player.getCapability(ModCapabilities.SPACE_SUIT).ifPresent(iSpaceSuit -> {
                RenderSystem.enableBlend();
                int left = width / 2 + 91;
                int top = height - 49;
                int air = iSpaceSuit.O2();
                System.out.println(air);
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

    public static boolean displayOxygen(Player p){
        ItemStack stack = SpaceArmor.getGear(SpaceSlot.TANK, p);
        // TODO FIX THIS LATER
        if (stack.getItem() instanceof Canister && Canister.getType(stack).getFluidType() == ModFluids.O2_FLUID_TYPE.get() && Canister.getValue(stack) > 0) return false;
        return !DimSettingsManager.getSettings(p).breathable();
    }
}
