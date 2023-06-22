package net.darkhax.effecttooltips.api.event;

import net.darkhax.effecttooltips.api.event.listeners.StatusEffectTooltipListener;
import net.darkhax.effecttooltips.api.event.listeners.StatusEffectsTooltipListener;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * This interface provides a modloader platform neutral way to interact with the events added by this mod. This is
 * optional and should only be used if you are developing a mod in a neutral environment.
 */
public interface IEventHelper {

    /**
     * Posts an event for gathering status effect tooltips. This event is scoped to one status effect and should be
     * fired multiple times if multiple effects are hovered. This is primarily intended for internal use but can also be
     * used by other mods to collect tooltip info.
     *
     * @param effect  The effect to gather tooltip entries for.
     * @param entries A mutable list of tooltip entries.
     * @param compact Is the screen rendering in compact mode?
     * @param flag    The current tooltip display flag.
     */
    void postEffectTooltip(MobEffectInstance effect, List<Component> entries, boolean compact, TooltipFlag flag);

    /**
     * Registers an event listener that will be notified when tooltip entries are gathered for a specific status
     * effect.
     *
     * @param listener The listener to register.
     */
    default void addStatusEffectTooltipListener(StatusEffectTooltipListener listener) {

        this.addStatusEffectTooltipListener(TooltipLayout.BODY, listener);
    }

    void addStatusEffectTooltipListener(TooltipLayout layout, StatusEffectTooltipListener listener);

    /**
     * Posts an event for gathering status effect tooltips. This event should be posted once per tooltip after an
     * individually scoped event has been posted for all hovered effects. This is primarily intended for internal use
     * but can also be used by other mods to collect tooltip info.
     *
     * @param effects A list of all hovered effects.
     * @param entries A mutable list of tooltip entries.
     * @param compact Is the screen rendering in compact mode?
     * @param flag    The current tooltip display flag.
     */
    void postEffectTooltip(List<MobEffectInstance> effects, List<Component> entries, boolean compact, TooltipFlag flag);

    /**
     * Registers an event listener that will be notified when tooltip entries are gathered.
     *
     * @param listener The listener to register.
     */
    void addStatusEffectsTooltipListener(StatusEffectsTooltipListener listener);
}