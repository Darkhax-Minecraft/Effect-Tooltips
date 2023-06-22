package net.darkhax.effecttooltips.api.event;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

public class StatusEffectTooltipEvent extends Event {

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

    /**
     * This event is fired on {@link MinecraftForge#EVENT_BUS} when tooltips are gathered for a specific status effect.
     * If multiple effects are hovered at once this event will be fired once for each effect. If cancelled no tooltip
     * entries will be added for this effect.
     */
    @Cancelable
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

    /**
     * This event is fired on {@link MinecraftForge#EVENT_BUS} when tooltips are gathered for the status effect screen.
     */
    @Cancelable
    public static class AllEffects extends StatusEffectTooltipEvent {

        public final List<MobEffectInstance> effects;

        public AllEffects(List<MobEffectInstance> effects, List<Component> entries, boolean compact, TooltipFlag flag) {

            super(entries, compact, flag);
            this.effects = effects;
        }
    }
}