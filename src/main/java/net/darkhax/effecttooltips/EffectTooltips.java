package net.darkhax.effecttooltips;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import net.darkhax.effecttooltips.event.EffectTooltipEvent;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.network.FMLNetworkConstants;

@Mod(EffectTooltips.MOD_ID)
@EventBusSubscriber(value = Dist.CLIENT, modid = EffectTooltips.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public final class EffectTooltips {
    
    // Mod Constants
    public static final String MOD_ID = "effecttooltips";
    
    public EffectTooltips() {
        
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of( () -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onEffectTooltipHigh (EffectTooltipEvent.SpecificEffect event) {
        
        // Adds the name of the effect and the remaining duration to the top of the tooltip.
        final ITextComponent name = new TranslationTextComponent(event.getEffect().getDescriptionId());
        final ITextComponent level = new TranslationTextComponent("enchantment.level." + (event.getEffect().getAmplifier() + 1));
        final ITextComponent duration = new StringTextComponent(EffectUtils.formatDuration(event.getEffect(), 1.0F));
        event.getTooltips().add(new TranslationTextComponent("tooltip.effecttooltips.name", name, level, duration));
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onEffectTooltipLow (EffectTooltipEvent.SpecificEffect event) {
        
        // Adds the name of the mod that added the effect to the bottom of the tooltip.
        final String modId = event.getEffect().getEffect().getRegistryName().getNamespace();
        final ITextComponent modName = new StringTextComponent(ModList.get().getModContainerById(modId).map(modContainer -> modContainer.getModInfo().getDisplayName()).orElse(StringUtils.capitalize(modId))).withStyle(TextFormatting.BLUE);
        event.getTooltips().add(modName);
        
        // When advanced debug info is enabled the registry name will be appended to the end of
        // the tooltip.
        if (event.getTooltipFlags().isAdvanced()) {
            
            event.getTooltips().add(new StringTextComponent(event.getEffect().getEffect().getRegistryName().toString()).withStyle(TextFormatting.DARK_GRAY));
        }
    }
}