package net.darkhax.effecttooltips.fabric.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.darkhax.effecttooltips.common.api.EffectTooltips;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectsInInventory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Mixin(EffectsInInventory.class)
public class MixinEffectsInInventory {

    @Unique
    private MobEffectInstance effectTooltips$currentEffect;

    @Final
    @Shadow
    private AbstractContainerScreen<?> screen;

    @Inject(method = "extractEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/EffectsInInventory;extractText(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/Font;IIIIII)V"))
    private void captureCurrentEffect(GuiGraphicsExtractor graphics, Collection<MobEffectInstance> activeEffects, int x0, int yStep, int mouseX, int mouseY, int maxWidth, CallbackInfo ci, @Local(name = "effect") MobEffectInstance effect) {
        effectTooltips$currentEffect = effect;
    }

    @ModifyExpressionValue(method = "extractText", at = @At(value = "INVOKE", target = "Ljava/util/List;of(Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/List;"))
    private List<Component> modifyCompactTooltipEntries(List<Component> original) {
        final ArrayList<Component> tooltipEntries = new ArrayList<>(original);
        EffectTooltips.EVENTS.buildTooltipContents(effectTooltips$currentEffect, tooltipEntries, true, Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
        return tooltipEntries;
    }

    @Inject(method = "extractText", at = @At("RETURN"))
    private void addNonCompactTooltip(GuiGraphicsExtractor graphics, Component effectText, Component duration, Font font, int x0, int y0, int textureWidth, int yStep, int mouseX, int mouseY, CallbackInfo ci, @Local(name = "isCompact") boolean isCompact) {
        if (!isCompact && mouseX >= x0 && mouseX <= x0 + textureWidth && mouseY >= y0 && mouseY <= y0 + yStep) {
            final ArrayList<Component> tooltipEntries = new ArrayList<>();
            tooltipEntries.add(effectText);
            tooltipEntries.add(duration);
            EffectTooltips.EVENTS.buildTooltipContents(effectTooltips$currentEffect, tooltipEntries, false, Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
            graphics.setTooltipForNextFrame(this.screen.getFont(), tooltipEntries, Optional.empty(), mouseX, mouseY);
        }
    }
}