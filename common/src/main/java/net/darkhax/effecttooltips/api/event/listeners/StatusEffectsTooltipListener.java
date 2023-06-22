package net.darkhax.effecttooltips.api.event.listeners;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * An event listener that is notified just before status effect tooltips are displayed.
 * <p>
 * This listener will observe all hovered status effects at once. In the event that multiple overlapping status effects
 * are being displayed the listener will have access to all hovered effects and their combined tooltip list. If the
 * listener is intended to modify tooltips for individual status effects the {@link StatusEffectsTooltipListener} should
 * be used instead.
 */
public interface StatusEffectsTooltipListener {

    /**
     * This listener is notified when a status effect tooltip is being built.
     *
     * @param effects The status effects that are being hovered.
     * @param entries The tooltip entries to display. This is a mutable list that you can extend.
     * @param compact Indicates if the status effect UI is being rendered in compact mode. Under vanilla circumstances
     *                this is only true when there is not enough room to render the full length of the extended UI in
     *                the inventory screen. Other mods may manipulate this arbitrarily through unrelated APIs.
     * @param flag    The current render flags. Can be used to display additional info when debug tooltips are enabled
     *                (f3 + h).
     */
    void notify(List<MobEffectInstance> effects, List<Component> entries, boolean compact, TooltipFlag flag);
}