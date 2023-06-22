package net.darkhax.effecttooltips.api.event;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;
import java.util.function.Function;

public class EffectTooltips {

    public static final String MOD_ID = "effecttooltips";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);
    public static final IEventHelper EVENTS = load(IEventHelper.class);

    public static void init(Function<String, MutableComponent> modNameResolver) {

        EffectTooltips.EVENTS.addStatusEffectTooltipListener(TooltipLayout.FOOTER, (effect, tooltip, isCompact, flag) -> {

            final ResourceLocation effectId = BuiltInRegistries.MOB_EFFECT.getKey(effect.getEffect());

            if (effectId != null) {

                tooltip.add(modNameResolver.apply(effectId.getNamespace()).withStyle(ChatFormatting.BLUE));

                if (flag.isAdvanced()) {

                    tooltip.add(Component.translatable(effectId.toString()));
                }
            }
        });
    }

    private static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        LOG.debug("Loaded '{}' of type '{}' for service '{}'.", loadedService, loadedService.getClass(), clazz);
        return loadedService;
    }
}