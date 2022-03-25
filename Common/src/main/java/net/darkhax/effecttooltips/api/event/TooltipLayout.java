package net.darkhax.effecttooltips.api.event;

import net.minecraft.resources.ResourceLocation;

public enum TooltipLayout {

    /**
     * The header contains the name of the effect, the effect duration, and any information inserted by other 3rd party
     * APIs.
     */
    HEADER("header"),

    /**
     * The body is empty by default. Mods adding additional information should do so here.
     */
    BODY("body"),

    /**
     * The footer will contain the name of the mod that owns the effect. When debug tooltips are enabled it will also
     * contain the registry id.
     */
    FOOTER("footer");

    public final ResourceLocation id;

    TooltipLayout(String id) {

        this.id = new ResourceLocation(EffectTooltips.MOD_ID, id);
    }
}
