package net.darkhax.effecttooltips.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.darkhax.effecttooltips.EffectTooltipsCommon;
import net.darkhax.effecttooltips.api.event.EffectTooltips;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.TooltipFlag;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Mixin(EffectRenderingInventoryScreen.class)
public abstract class MixinEffectScreen extends AbstractContainerScreen {

    @Unique
    private final List<MobEffectInstance> hoveredEffects = new LinkedList<>();

    @Unique
    private int mouseX;

    @Unique
    private int mouseY;

    @Unique
    private boolean compactEffectRendering;

    @Unique
    private MobEffectInstance vanillaCompactEffect;

    @Shadow
    private Component getEffectName(MobEffectInstance effect) {

        throw new IllegalStateException("Mixin failed.");
    }

    @ModifyArg(method = "renderEffects(Lcom/mojang/blaze3d/vertex/PoseStack;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;renderTooltip(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/List;Ljava/util/Optional;II)V"))
    private List<Component> modifyCompactTooltip(List<Component> value) {

        // Vanilla already renders a tooltip when in the compact view. This mixin posts our event and modifies the tooltip entries for their tooltip.
        final TooltipFlag flag = Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL;

        // Make the vanilla tooltip list mutable.
        final List<Component> entries = new LinkedList<>(value);

        EffectTooltips.EVENTS.postEffectTooltip(vanillaCompactEffect, entries, true, flag);
        EffectTooltips.EVENTS.postEffectTooltip(List.of(vanillaCompactEffect), entries, true, flag);
        return entries;
    }

    @Inject(method = "renderEffects(Lcom/mojang/blaze3d/vertex/PoseStack;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;renderTooltip(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/List;Ljava/util/Optional;II)V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
    private void cacheVanillaCompactHoverEffect(PoseStack poseStack, int i, int j, CallbackInfo ci, int k, int l, Collection collection, boolean bl, int m, Iterable iterable, int n, MobEffectInstance mobEffectInstance, List list) {

        // Cache the hovered effect used by the vanilla compact tooltip.
        vanillaCompactEffect = mobEffectInstance;
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V", at = @At("HEAD"))
    private void onRenderHead(PoseStack stack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {

        // Cache the mouse position to avoid calculating it later.
        this.mouseX = mouseX;
        this.mouseY = mouseY;

        // Reset the hovered effect cache.
        this.hoveredEffects.clear();
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V", at = @At("RETURN"))
    private void onRenderReturn(PoseStack stack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {

        // Adds a new tooltip to the non-compact view of this GUI if there are some effects being hovered. Vanilla already renders a tooltip for the compact view.
        if (!this.hoveredEffects.isEmpty() && !this.compactEffectRendering) {

            final TooltipFlag flag = Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL;
            final List<Component> tooltips = new LinkedList<>();

            // Build the broader tooltip by posting events for each hovered effect individually.
            for (MobEffectInstance effect : this.hoveredEffects) {
                final List<Component> effectTooltip = new LinkedList<>();

                // Vanilla tooltip content parity.
                effectTooltip.add(this.getEffectName(effect));

                // Show effect category in tooltip if enabled in config
                if (EffectTooltipsCommon.config.showEffectCategory) {
                    MobEffectCategory effectCategory = effect.getEffect().getCategory();
                    effectTooltip.add(Component.translatable(StringUtils.capitalize(effectCategory.name().toLowerCase())).withStyle(effectCategory.getTooltipFormatting()));
                }

                effectTooltip.add(Component.translatable(MobEffectUtil.formatDuration(effect, 1.0F)));

                // Post individual effect tooltip.
                EffectTooltips.EVENTS.postEffectTooltip(effect, effectTooltip, this.compactEffectRendering, flag);

                // Add a blank line between individual entries.
                if (!tooltips.isEmpty()) {

                    tooltips.add(Component.literal(" "));
                }

                // Add the tooltips for the individual effect.
                if (!effectTooltip.isEmpty()) {

                    tooltips.addAll(effectTooltip);
                }
            }

            // Post a final event for the combined tooltips of all effects being hovered.
            EffectTooltips.EVENTS.postEffectTooltip(this.hoveredEffects, tooltips, this.compactEffectRendering, flag);

            if (!tooltips.isEmpty()) {

                this.renderTooltip(stack, tooltips, Optional.empty(), mouseX, mouseY);
            }
        }
    }

    @Inject(method = "renderBackgrounds(Lcom/mojang/blaze3d/vertex/PoseStack;IILjava/lang/Iterable;Z)V", at = @At("RETURN"))
    private void renderBackgrounds(PoseStack stack, int xPos, int yOffset, Iterable<MobEffectInstance> effects, boolean isExpanded, CallbackInfo ci) {

        // Detect if the effects are rendering in compact mode
        this.compactEffectRendering = !isExpanded;

        // Calculate which effects the mouse is hovering over.
        int yPos = this.topPos;

        for (MobEffectInstance effect : effects) {

            final int length = isExpanded ? 120 : 32;
            final int height = 32;

            // Check if the mouse is within the render area of the effect element.
            if (mouseX >= xPos && mouseY >= yPos && mouseX < xPos + length && mouseY < yPos + height) {

                this.hoveredEffects.add(effect);
            }

            yPos += yOffset;
        }
    }

    private MixinEffectScreen(AbstractContainerMenu $$0, Inventory $$1, Component $$2) {

        super($$0, $$1, $$2);
    }
}