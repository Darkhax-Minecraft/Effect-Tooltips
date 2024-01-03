package net.darkhax.effecttooltips.api.event;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

import java.util.List;

public class StatusEffectTooltipEvent extends Event implements ICancellableEvent {

    /**
     * A mutable list of tooltip entries.
     */
    public final List<Component> tooltipEntries;

    /**
     * Is the status effect screen rendering in compact mode?
     */
    public final boolean isCompact;

    /**
     * The current tooltip flags.
     */
    public final TooltipFlag flag;

    private StatusEffectTooltipEvent(List<Component> entries, boolean compact, TooltipFlag flag) {

        this.tooltipEntries = entries;
        this.isCompact = compact;
        this.flag = flag;
    }

    public static class IndividualEffect extends StatusEffectTooltipEvent {

        /**
         * The effect to gather tooltips for.
         */
        public final MobEffectInstance effect;

        public IndividualEffect(MobEffectInstance effect, List<Component> entries, boolean compact, TooltipFlag flag) {

            super(entries, compact, flag);
            this.effect = effect;
        }
    }

    public static class AllEffects extends StatusEffectTooltipEvent {

        public final List<MobEffectInstance> effects;

        public AllEffects(List<MobEffectInstance> effects, List<Component> entries, boolean compact, TooltipFlag flag) {

            super(entries, compact, flag);
            this.effects = effects;
        }
    }
}