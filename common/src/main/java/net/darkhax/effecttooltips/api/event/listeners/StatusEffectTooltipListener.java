package net.darkhax.effecttooltips.api.event.listeners;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * An event listener that allows mods to modify the contents of the status effect tooltip.
 * <p>
 * This listener only observes a single status effect. In the event that multiple overlapping effects are displayed this
 * listener will be notified once for each individual status effect. If the listener needs to act on the entire tooltip
 * a {@link StatusEffectTooltipListener} should be used instead.
 */
public interface StatusEffectTooltipListener {

    /**
     * This listener is notified when a specific status effect tooltip is being built.
     *
     * @param effect  The status effect that this tooltip is for.
     * @param entries The tooltip entries to display. This is a mutable list that you can extend. If multiple effects
     *                are hovered this will exclusively contain tooltip entries for the current effect. If the final
     *                tooltip list contains no entries the tooltip will not be rendered.
     * @param compact Indicates if the status effect UI is being rendered in compact mode. Under vanilla circumstances
     *                this is only true when there is not enough room to render the full length of the extended UI in
     *                the inventory screen. Other mods may manipulate this arbitrarily through unrelated APIs.
     * @param flag    The current render flags. Can be used to display additional info when debug tooltips are enabled
     *                (f3 + h).
     */
    void notify(MobEffectInstance effect, List<Component> entries, boolean compact, TooltipFlag flag);
}
