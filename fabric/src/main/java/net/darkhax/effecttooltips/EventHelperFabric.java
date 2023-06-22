package net.darkhax.effecttooltips;

import net.darkhax.effecttooltips.api.event.IEventHelper;
import net.darkhax.effecttooltips.api.event.TooltipLayout;
import net.darkhax.effecttooltips.api.event.listeners.StatusEffectTooltipListener;
import net.darkhax.effecttooltips.api.event.listeners.StatusEffectsTooltipListener;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class EventHelperFabric implements IEventHelper {

    @Override
    public void postEffectTooltip(MobEffectInstance effect, List<Component> entries, boolean compact, TooltipFlag flag) {

        EffectTooltipsFabric.INDIVIDUAL_EFFECT.invoker().notify(effect, entries, compact, flag);
    }

    @Override
    public void addStatusEffectTooltipListener(TooltipLayout layout, StatusEffectTooltipListener listener) {

        EffectTooltipsFabric.INDIVIDUAL_EFFECT.register(layout.id, listener);
    }

    @Override
    public void postEffectTooltip(List<MobEffectInstance> effects, List<Component> entries, boolean compact, TooltipFlag flag) {

        EffectTooltipsFabric.EFFECTS_TOOLTIP.invoker().notify(effects, entries, compact, flag);
    }

    @Override
    public void addStatusEffectsTooltipListener(StatusEffectsTooltipListener listener) {

        EffectTooltipsFabric.EFFECTS_TOOLTIP.register(listener);
    }
}