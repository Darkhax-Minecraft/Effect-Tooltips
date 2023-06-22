package net.darkhax.effecttooltips;

import net.darkhax.effecttooltips.api.event.IEventHelper;
import net.darkhax.effecttooltips.api.event.StatusEffectTooltipEvent;
import net.darkhax.effecttooltips.api.event.TooltipLayout;
import net.darkhax.effecttooltips.api.event.listeners.StatusEffectTooltipListener;
import net.darkhax.effecttooltips.api.event.listeners.StatusEffectsTooltipListener;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;

import java.util.List;
import java.util.function.Consumer;

public class EventHelperForge implements IEventHelper {

    @Override
    public void postEffectTooltip(MobEffectInstance effect, List<Component> entries, boolean compact, TooltipFlag flag) {

        if (MinecraftForge.EVENT_BUS.post(new StatusEffectTooltipEvent.IndividualEffect(effect, entries, compact, flag))) {

            entries.clear();
        }
    }

    @Override
    public void addStatusEffectTooltipListener(TooltipLayout layout, StatusEffectTooltipListener listener) {

        final Consumer<StatusEffectTooltipEvent.IndividualEffect> forgeListener = e -> {

            listener.notify(e.effect, e.tooltipEntries, e.isCompact, e.flag);
        };

        final EventPriority priority = layout == TooltipLayout.BODY ? EventPriority.NORMAL : layout == TooltipLayout.HEADER ? EventPriority.HIGH : EventPriority.LOW;
        MinecraftForge.EVENT_BUS.addListener(priority, false, StatusEffectTooltipEvent.IndividualEffect.class, forgeListener);
    }

    @Override
    public void postEffectTooltip(List<MobEffectInstance> effects, List<Component> entries, boolean compact, TooltipFlag flag) {

        if (MinecraftForge.EVENT_BUS.post(new StatusEffectTooltipEvent.AllEffects(effects, entries, compact, flag))) {

            entries.clear();
        }
    }

    @Override
    public void addStatusEffectsTooltipListener(StatusEffectsTooltipListener listener) {

        final Consumer<StatusEffectTooltipEvent.AllEffects> forgeListener = e -> {

            listener.notify(e.effects, e.tooltipEntries, e.isCompact, e.flag);
        };

        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, StatusEffectTooltipEvent.AllEffects.class, forgeListener);
    }
}
