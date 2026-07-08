package net.darkhax.effecttooltips.common.api.event;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * A modloader agnostic helper for registering status effect tooltip events.
 * <p>
 * * <p><b>Example:</b>
 * * <pre>{@code
 *  * EffectTooltips.EVENTS.addListener((effect, tooltip, compact, flag) -> {
 *  *     tooltip.add(Component.literal("Custom tooltip line"));
 *  * });
 *  * }</pre>
 */
public interface EventHelper {

    /**
     * Posts an event for gathering status effect tooltip entries.
     *
     * @param effect  The effect to gather entries for.
     * @param entries The list of entries to populate.
     * @param compact Is the status effect being rendered in compact mode?
     * @param flag    The current flags for rendering tooltips.
     */
    void buildTooltipContents(MobEffectInstance effect, List<Component> entries, boolean compact, TooltipFlag flag);

    /**
     * Registers an event listener that will be notified when the status effect tooltip entries are built.
     *
     * @param listener The event listener to register.
     */
    default void addListener(StatusEffectTooltipListener listener) {
        this.addListener(TooltipLayout.BODY, listener);
    }

    /**
     * Registers an event listener that will be notified when the status effect tooltip entries are built.
     *
     * @param layout   Determines the ordering and priority of the event listener.
     * @param listener The event listener to register.
     */
    void addListener(TooltipLayout layout, StatusEffectTooltipListener listener);
}