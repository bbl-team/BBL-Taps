package com.benbenlaw.taps.screen;

import com.benbenlaw.taps.Taps;
import com.benbenlaw.taps.screen.SapTableMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class SapTableScreen extends AbstractContainerScreen<SapTableMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Taps.MOD_ID, "textures/gui/sap_table_gui.png");

    public SapTableScreen(SapTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);

    //    if(menu.isCrafting()) {
    //        guiGraphics.blit(TEXTURE, x + 85, y + 41, 176, 0, 8, menu.getScaledProgress());
    //    }

        if (menu.isCrafting()) {
            int l = this.menu.getScaledProgress()   ;
            guiGraphics.blit(TEXTURE, x + 33, y + 35, 176, 14, menu.getScaledProgress() + 1, 16);
        }


    }




}
