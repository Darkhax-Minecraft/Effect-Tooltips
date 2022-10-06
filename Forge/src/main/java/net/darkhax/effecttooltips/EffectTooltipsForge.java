package net.darkhax.effecttooltips;

import net.darkhax.effecttooltips.api.event.EffectTooltips;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(EffectTooltips.MOD_ID)
public class EffectTooltipsForge {

    public EffectTooltipsForge() {
        new EffectTooltipsCommon(FMLPaths.CONFIGDIR.get());

        EffectTooltips.init((modId) -> Component.translatable(ModList.get().getModContainerById(modId).map(mod -> mod.getModInfo().getDisplayName()).orElse(modId)));
    }
}