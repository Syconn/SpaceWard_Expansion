package syconn.swe.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import syconn.swe.item.SpaceArmor;

public class RenderUtil {

    public static void overridePlayerScreen(PoseStack poseStack, AbstractContainerScreen<?> screen) {
        if(screen instanceof InventoryScreen inventory)
        {
            Player p = Minecraft.getInstance().player;
            if (p != null && SpaceArmor.hasFullKit(p)) {

                int left = inventory.getGuiLeft();
                int top = inventory.getGuiTop();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, AbstractContainerScreen.INVENTORY_LOCATION);
                Screen.blit(poseStack, left + 76, top + 43, 7, 7, 18, 18, 256, 256);
                RenderSystem.setShaderTexture(0, AbstractContainerScreen.INVENTORY_LOCATION);
                Screen.blit(poseStack, left + 76, top + 25, 7, 7, 18, 18, 256, 256);
            }
        }
//        else if(screen instanceof CreativeModeInventoryScreen inventory)
//        {
//            if(inventory.getSelectedTab() == CreativeModeTab.TAB_INVENTORY.getId())
//            {
//                int left = inventory.getGuiLeft();
//                int top = inventory.getGuiTop();
//                RenderSystem.setShader(GameRenderer::getPositionTexShader);
//                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//                RenderSystem.setShaderTexture(0, AbstractContainerScreen.INVENTORY_LOCATION);
//                Screen.blit(poseStack, left + 126, top + 19, 7, 7, 18, 18, 256, 256);
//            }
//        }
    }
}
