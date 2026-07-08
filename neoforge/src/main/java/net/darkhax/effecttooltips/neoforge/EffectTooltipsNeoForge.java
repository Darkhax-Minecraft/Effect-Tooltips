package net.darkhax.effecttooltips.neoforge;

import net.darkhax.effecttooltips.common.api.EffectTooltips;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;

@Mod(EffectTooltips.MOD_ID)
public class EffectTooltipsNeoForge {

    public EffectTooltipsNeoForge() {
        EffectTooltips.init((modId) -> Component.translatable(ModList.get().getModContainerById(modId).map(mod -> mod.getModInfo().getDisplayName()).orElse(modId)));
    }
}