package net.darkhax.effecttooltips.neoforge;

import net.darkhax.effecttooltips.common.api.event.EventHelper;
import net.darkhax.effecttooltips.neoforge.api.event.StatusEffectTooltipEvent;
import net.darkhax.effecttooltips.common.api.event.TooltipLayout;
import net.darkhax.effecttooltips.common.api.event.StatusEffectTooltipListener;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.common.NeoForge;

import java.util.List;
import java.util.function.Consumer;

public class EventHelperNeoForge implements EventHelper {

    @Override
    public void buildTooltipContents(MobEffectInstance effect, List<Component> entries, boolean compact, TooltipFlag flag) {
        final StatusEffectTooltipEvent.IndividualEffect event = new StatusEffectTooltipEvent.IndividualEffect(effect, entries, compact, flag);
        NeoForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            entries.clear();
        }
    }

    @Override
    public void addListener(TooltipLayout layout, StatusEffectTooltipListener listener) {
        final Consumer<StatusEffectTooltipEvent.IndividualEffect> forgeListener = e -> listener.notify(e.effect, e.tooltipEntries, e.isCompact, e.flag);
        final EventPriority priority = layout == TooltipLayout.BODY ? EventPriority.NORMAL : layout == TooltipLayout.HEADER ? EventPriority.HIGH : EventPriority.LOW;
        NeoForge.EVENT_BUS.addListener(priority, false, StatusEffectTooltipEvent.IndividualEffect.class, forgeListener);
    }
}