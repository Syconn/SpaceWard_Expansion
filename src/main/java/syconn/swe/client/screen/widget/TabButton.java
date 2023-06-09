package syconn.swe.client.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import org.apache.commons.lang3.StringUtils;
import syconn.swe.Main;

public class TabButton extends ExtendedButton {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/tabs.png");
    private final State state;
    private final String name;
    private final Item item;
    private boolean selected;

    public TabButton(int x, int y, State state, String name, Item stack, boolean selected, OnPress onPress) {
        super(x, y - 26, 28, 28, Component.literal(name.toUpperCase().substring(0, 1)).withStyle(ChatFormatting.WHITE), onPress);
        this.state = state;
        this.name = name;
        this.item = stack;
        this.selected = selected;
    }

    @Override
    public void renderWidget(PoseStack mStack, int mouseX, int mouseY, float partial) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        Minecraft mc = Minecraft.getInstance();
        int x = getX();
        int y = getY();

        if (!selected){
            if (state == State.LEFT) blit(mStack, x, y, 0, 0, 28, 28);
            if (state == State.MIDDLE) blit(mStack, x, y, 28, 0, 28, 28);
            if (state == State.RIGHT) blit(mStack, x, y, 56, 0, 28, 28);
        } else {
            if (state == State.LEFT) blit(mStack, x, y - 2, 0, 30, 28, 32);
            if (state == State.MIDDLE) blit(mStack, x, y - 2, 28, 30, 28, 31);
            if (state == State.RIGHT) blit(mStack, x, y - 2, 56, 30, 28, 31);
        }

        if (item != null) mc.getItemRenderer().renderAndDecorateItem(mStack, new ItemStack(item), x + 6, y + 6);
        else {
            final FormattedText buttonText = mc.font.ellipsize(this.getMessage(), this.width - 6); // Remove 6 pixels so that the text is always contained within the button's borders
            drawCenteredString(mStack, mc.font, Language.getInstance().getVisualOrder(buttonText), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, getFGColor());
        }
        if (isMouseOver(mouseX, mouseY))
            drawString(mStack, mc.font, StringUtils.capitalize(name), x - 10, y - 10, 14737632);
    }

    public boolean isSelected() {
        return selected;
    }

    public String getName() {
        return name;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public enum State {
        LEFT,
        RIGHT,
        MIDDLE
    }
}
