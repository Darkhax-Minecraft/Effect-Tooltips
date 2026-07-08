package net.darkhax.effecttooltips.common.api.event;

import net.darkhax.effecttooltips.common.api.EffectTooltips;
import net.minecraft.resources.Identifier;

/**
 * Represents the different sections of the effect tooltip and determines the ordering of tooltip entries.
 */
public enum TooltipLayout {

    /**
     * The header contains the name of the effect, the effect duration, and any info added by other mods that are not
     * using this API.
     */
    HEADER("header"),

    /**
     * The body is empty by default. Mods adding info should generally add it here.
     */
    BODY("body"),

    /**
     * The footer contains the name of the mod that adds the effect. When debug tooltips are enabled it will also
     * include the registry ID.
     */
    FOOTER("footer");

    public final Identifier id;

    TooltipLayout(String id) {
        this.id = Identifier.fromNamespaceAndPath(EffectTooltips.MOD_ID, id);
    }
}
