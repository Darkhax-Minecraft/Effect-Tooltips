package net.darkhax.effecttooltips.common.api;

import net.darkhax.effecttooltips.common.api.event.EventHelper;
import net.darkhax.effecttooltips.common.api.event.TooltipLayout;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;
import java.util.function.Function;

public class EffectTooltips {

    public static final String MOD_ID = "effecttooltips";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);
    public static final EventHelper EVENTS = load(EventHelper.class);

    public static void init(Function<String, MutableComponent> modNameResolver) {
        EffectTooltips.EVENTS.addListener(TooltipLayout.FOOTER, (effect, tooltip, isCompact, flag) -> {
            effect.getEffect().unwrapKey().ifPresent(key -> {
                tooltip.add(modNameResolver.apply(key.identifier().getNamespace()).withStyle(ChatFormatting.BLUE));
                if (flag.isAdvanced()) {
                    tooltip.add(Component.translatable(key.identifier().toString()).withStyle(ChatFormatting.DARK_GRAY));
                }
            });
        });
    }

    private static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        LOG.debug("Loaded '{}' of type '{}' for service '{}'.", loadedService, loadedService.getClass(), clazz);
        return loadedService;
    }
}