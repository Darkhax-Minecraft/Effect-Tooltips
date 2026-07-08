package net.darkhax.effecttooltips.common.api.event;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * An event listener that is notified when the contents of a status effect tooltip are being built. This listener can
 * modify the contents of the tooltip.
 */
@FunctionalInterface
public interface StatusEffectTooltipListener {

    /**
     * Notifies the listener that a status effect tooltip is being built.
     *
     * @param effect  The status effect the tooltip is for.
     * @param entries The current tooltip entries.
     * @param compact If the status effect is being rendered in full-width or compact mode.
     * @param flag    The current render flags. Used for things like advanced debug tooltips.
     */
    void notify(MobEffectInstance effect, List<Component> entries, boolean compact, TooltipFlag flag);
}