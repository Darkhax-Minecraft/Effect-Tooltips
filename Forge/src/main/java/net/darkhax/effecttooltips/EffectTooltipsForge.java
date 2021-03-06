package net.darkhax.effecttooltips;

import net.darkhax.effecttooltips.api.event.EffectTooltips;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod(EffectTooltips.MOD_ID)
public class EffectTooltipsForge {

    public EffectTooltipsForge() {

        EffectTooltips.init((modId) -> Component.translatable(ModList.get().getModContainerById(modId).map(mod -> mod.getModInfo().getDisplayName()).orElse(modId)));
    }
}