package net.darkhax.effecttooltips;

import net.darkhax.effecttooltips.api.event.EffectTooltips;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;

@Mod(EffectTooltips.MOD_ID)
public class EffectTooltipsNeoForge {

    public EffectTooltipsNeoForge() {
        new EffectTooltipsCommon(FMLPaths.CONFIGDIR.get());

        EffectTooltips.init((modId) -> Component.translatable(ModList.get().getModContainerById(modId).map(mod -> mod.getModInfo().getDisplayName()).orElse(modId)));
    }
}