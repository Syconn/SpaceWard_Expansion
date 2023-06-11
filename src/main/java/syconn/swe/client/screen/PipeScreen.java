package syconn.swe.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import syconn.swe.Main;
import syconn.swe.client.screen.widget.TabButton;
import syconn.swe.common.be.PipeBlockEntity;
import syconn.swe.common.container.PipeMenu;
import syconn.swe.util.data.FluidPointSystem;

public class PipeScreen extends AbstractContainerScreen<PipeMenu> {

    private final ResourceLocation BACKGROUND = new ResourceLocation(Main.MODID, "textures/gui/fluid_pipe.png");

    private TabButton[] tabs;
    private final FluidPointSystem system = menu.getBE().getSystem();
    private final FluidPointSystem.FluidPoint fluidPoint;

    public PipeScreen(PipeMenu menu, Inventory p_97742_, Component p_97743_) {
        super(menu, p_97742_, p_97743_);
        fluidPoint = menu.getBE().selectedTab();
    }

    @Override
    protected void init() {
        super.init();
        int sz = system.getPoints().size();
        tabs = new TabButton[sz];
        System.out.println(fluidPoint);
        for (int i = 0; i < sz; i++) {
            TabButton.State state = i == 0 ? TabButton.State.LEFT : i == 5 ? TabButton.State.RIGHT : TabButton.State.MIDDLE;
            String name = system.getPoints().get(i).d().getName();
            tabs[i] = addRenderableWidget(new TabButton(leftPos + (28 + 5) * i, topPos, state, i, name, null, system.getPoints().get(i).equals(fluidPoint), this::tabClicked));
        }
    }

    @Override
    public void render(PoseStack p_97795_, int p_97796_, int p_97797_, float p_97798_) {
        this.renderBackground(p_97795_);
        super.render(p_97795_, p_97796_, p_97797_, p_97798_);
        renderTooltip(p_97795_, p_97796_, p_97797_);
    }

    @Override
    protected void renderBg(PoseStack pose, float p_97788_, int p_97789_, int p_97790_) {
        RenderSystem.setShaderTexture(0, BACKGROUND);
        blit(pose, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        if (menu.getBE().selectedTab() != null) {
            blit(pose, this.leftPos + 151, this.topPos + 12, 176, 8, 18, 18);
            blit(pose, this.leftPos + 151, this.topPos + 34, 176, 8, 18, 18);
            blit(pose, this.leftPos + 151, this.topPos + 56, 176, 8, 18, 18);
        }
    }

    private void tabClicked(Button b){
        if (b instanceof TabButton tab) {

        }
    }
}
