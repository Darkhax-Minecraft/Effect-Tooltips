package net.darkhax.effecttooltips.fabric;

import net.darkhax.effecttooltips.common.api.event.EventHelper;
import net.darkhax.effecttooltips.common.api.event.StatusEffectTooltipListener;
import net.darkhax.effecttooltips.common.api.event.TooltipLayout;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class EventHelperFabric implements EventHelper {

    @Override
    public void buildTooltipContents(MobEffectInstance effect, List<Component> entries, boolean compact, TooltipFlag flag) {
        EffectTooltipsFabric.BUILD_EFFECT_TOOLTIP.invoker().notify(effect, entries, compact, flag);
    }

    @Override
    public void addListener(TooltipLayout layout, StatusEffectTooltipListener listener) {
        EffectTooltipsFabric.BUILD_EFFECT_TOOLTIP.register(layout.id, listener);
    }
}