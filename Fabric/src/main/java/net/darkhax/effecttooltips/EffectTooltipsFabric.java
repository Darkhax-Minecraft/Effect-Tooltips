package net.darkhax.effecttooltips;

import net.darkhax.effecttooltips.api.event.EffectTooltips;
import net.darkhax.effecttooltips.api.event.TooltipLayout;
import net.darkhax.effecttooltips.api.event.listeners.StatusEffectTooltipListener;
import net.darkhax.effecttooltips.api.event.listeners.StatusEffectsTooltipListener;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.TextComponent;

public class EffectTooltipsFabric implements ModInitializer {

    /**
     * An event that is fired when tooltips are being built for an individual status effect. In some circumstances
     * multiple tooltips can be hovered at once, in these circumstances these listeners will be notified once for each
     * hovered effect and will only have context for that individual status effect. If your listener wants context to
     * all hovered effects and all tooltip entries it should use {@link #EFFECTS_TOOLTIP}.
     */
    public static final Event<StatusEffectTooltipListener> INDIVIDUAL_EFFECT = EventFactory.createWithPhases(StatusEffectTooltipListener.class, callbacks -> (effect, entries, compact, flag) -> {
        for (StatusEffectTooltipListener callback : callbacks) {
            callback.notify(effect, entries, compact, flag);
        }
    }, TooltipLayout.HEADER.id, TooltipLayout.BODY.id, TooltipLayout.FOOTER.id, Event.DEFAULT_PHASE);

    /**
     * An event that is fired when tooltips are being built for hovered status effects. The listener will be given all
     * tooltip entries as a unified list. Listeners that want to provide information for individual effects should use
     * the {@link #INDIVIDUAL_EFFECT} listener.
     */
    public static final Event<StatusEffectsTooltipListener> EFFECTS_TOOLTIP = EventFactory.createArrayBacked(StatusEffectsTooltipListener.class, callbacks -> (effects, entries, compact, flag) -> {
        for (StatusEffectsTooltipListener callback : callbacks) {
            callback.notify(effects, entries, compact, flag);
        }
    });

    @Override
    public void onInitialize() {

        EffectTooltips.init((modId) -> new TextComponent(FabricLoader.getInstance().getModContainer(modId).map(mod -> mod.getMetadata().getName()).orElse(modId)));
    }
}