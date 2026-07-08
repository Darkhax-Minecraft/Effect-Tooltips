package net.darkhax.effecttooltips.neoforge.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.darkhax.effecttooltips.common.api.EffectTooltips;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EffectsInInventory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(EffectsInInventory.class)
public class MixinEffectsInInventory {

    @ModifyExpressionValue(method = "renderText", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/client/ClientHooks;getEffectTooltip(Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;Lnet/minecraft/world/effect/MobEffectInstance;Ljava/util/List;)Ljava/util/List;"))
    private List<Component> modifyEffectTooltip(List<Component> original, @Local(name = "effectText", argsOnly = true) Component effectText, @Local(name = "duration", argsOnly = true) Component duration, @Local(name = "effectInstance", argsOnly = true) MobEffectInstance effectInstance, @Local(name = "isCompact") boolean isCompact) {
        final List<Component> modified = new ArrayList<>(original);
        if (!isCompact && modified.isEmpty()) {
            modified.add(effectText);
            modified.add(duration);
        }
        EffectTooltips.EVENTS.buildTooltipContents(effectInstance, modified, isCompact, Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
        return modified;
    }
}