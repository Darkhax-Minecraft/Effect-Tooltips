package net.darkhax.effecttooltips;

import net.darkhax.effecttooltips.api.event.IEventHelper;
import net.darkhax.effecttooltips.api.event.StatusEffectTooltipEvent;
import net.darkhax.effecttooltips.api.event.TooltipLayout;
import net.darkhax.effecttooltips.api.event.listeners.StatusEffectTooltipListener;
import net.darkhax.effecttooltips.api.event.listeners.StatusEffectsTooltipListener;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.common.NeoForge;

import java.util.List;
import java.util.function.Consumer;

public class EventHelperNeoForge implements IEventHelper {

    @Override
    public void postEffectTooltip(MobEffectInstance effect, List<Component> entries, boolean compact, TooltipFlag flag) {

        final StatusEffectTooltipEvent.IndividualEffect event = new StatusEffectTooltipEvent.IndividualEffect(effect, entries, compact, flag);
        NeoForge.EVENT_BUS.post(event);

        if (event.isCanceled()) {

            entries.clear();
        }
    }

    @Override
    public void addStatusEffectTooltipListener(TooltipLayout layout, StatusEffectTooltipListener listener) {

        final Consumer<StatusEffectTooltipEvent.IndividualEffect> forgeListener = e -> {

            listener.notify(e.effect, e.tooltipEntries, e.isCompact, e.flag);
        };

        final EventPriority priority = layout == TooltipLayout.BODY ? EventPriority.NORMAL : layout == TooltipLayout.HEADER ? EventPriority.HIGH : EventPriority.LOW;
        NeoForge.EVENT_BUS.addListener(priority, false, StatusEffectTooltipEvent.IndividualEffect.class, forgeListener);
    }

    @Override
    public void postEffectTooltip(List<MobEffectInstance> effects, List<Component> entries, boolean compact, TooltipFlag flag) {

        final StatusEffectTooltipEvent.AllEffects event = new StatusEffectTooltipEvent.AllEffects(effects, entries, compact, flag);
        NeoForge.EVENT_BUS.post(event);

        if (event.isCanceled()) {

            entries.clear();
        }
    }

    @Override
    public void addStatusEffectsTooltipListener(StatusEffectsTooltipListener listener) {

        final Consumer<StatusEffectTooltipEvent.AllEffects> forgeListener = e -> {

            listener.notify(e.effects, e.tooltipEntries, e.isCompact, e.flag);
        };

        NeoForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, StatusEffectTooltipEvent.AllEffects.class, forgeListener);
    }
}
