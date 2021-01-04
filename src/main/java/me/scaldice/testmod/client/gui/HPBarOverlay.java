package me.scaldice.testmod.client.gui;

import me.scaldice.testmod.TestMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiOverlayTest extends Gui {

    private final ResourceLocation bar = new ResourceLocation(TestMod.MODID, "/textures/gui/hpbar.png");
    private final int tex_width = 102, tex_height = 8, bar_width = 100, bar_height = 6;

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.renderEngine.bindTexture(bar);
            float oneUnit = (float)bar_width / mc.thePlayer.getMaxHealth();
            int currentWidth = (int) (bar_width / mc.thePlayer.getMaxHealth() * mc.thePlayer.getHealth());
            drawTexturedModalRect(0, 0, 0, 0, tex_width, tex_height);
            drawTexturedModalRect(1, 0, 1, tex_height, currentWidth, tex_height);
        }

    }
}