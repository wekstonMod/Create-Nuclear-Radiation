package net.Wekston.createnuclear_radiation.foundation.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.Wekston.createnuclear_radiation.content.PlayerData.PlayerDataManager;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.GameType;
import net.nuclearteam.createnuclear.CNEffects;
import net.nuclearteam.createnuclear.foundation.events.overlay.IrradiatedOverlayRendererVision;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.nuclearteam.createnuclear.foundation.events.overlay.IrradiatedOverlayRendererVision.*;

@Mixin(value = IrradiatedOverlayRendererVision.class)
public class IrradiatedOverlayRendererVisionMixin {
    @Shadow
    private static float irradiatedVisionAlpha = 0.0F;
       @Inject(
            method = {"renderOverlay"},
            at = {@At("HEAD")},
            cancellable = true, remap = false
    )
    private static void renderOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        ci.cancel();
        Minecraft mc = Minecraft.getInstance();
        if (!mc.options.hideGui && mc.gameMode.getPlayerMode() != GameType.SPECTATOR) {
            LocalPlayer localPlayer = mc.player;
            RenderSystem.enableBlend();
            if (localPlayer.hasEffect(CNEffects.RADIATION.getDelegate()) || PlayerDataManager.gettingRadiation(localPlayer) > 7) {
                irradiatedVisionAlpha = Math.min(1.0F, irradiatedVisionAlpha + 0.01F);
            } else {
                irradiatedVisionAlpha = Math.max(0.0F, irradiatedVisionAlpha - 0.01F);
            }

            if (irradiatedVisionAlpha > 0.0F) {
                renderTextureOverlay(guiGraphics, IRRADIATED_VISION, irradiatedVisionAlpha, true);
            }

        }
    }
}
