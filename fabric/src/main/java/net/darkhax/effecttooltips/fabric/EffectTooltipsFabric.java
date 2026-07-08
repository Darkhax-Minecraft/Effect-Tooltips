package net.darkhax.effecttooltips.fabric;

import net.darkhax.effecttooltips.common.api.EffectTooltips;
import net.darkhax.effecttooltips.common.api.event.StatusEffectTooltipListener;
import net.darkhax.effecttooltips.common.api.event.TooltipLayout;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;

public class EffectTooltipsFabric implements ModInitializer {

    /**
     * An event that is fired when tooltip entries for a status effect are being built.
     */
    public static final Event<StatusEffectTooltipListener> BUILD_EFFECT_TOOLTIP = EventFactory.createWithPhases(StatusEffectTooltipListener.class, callbacks -> (effect, entries, compact, flag) -> {
        for (StatusEffectTooltipListener callback : callbacks) {
            callback.notify(effect, entries, compact, flag);
        }
    }, TooltipLayout.HEADER.id, TooltipLayout.BODY.id, TooltipLayout.FOOTER.id, Event.DEFAULT_PHASE);

    @Override
    public void onInitialize() {
        EffectTooltips.init((modId) -> Component.translatable(FabricLoader.getInstance().getModContainer(modId).map(mod -> mod.getMetadata().getName()).orElse(modId)));
    }
}