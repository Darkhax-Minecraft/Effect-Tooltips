package net.darkhax.effecttooltips;

import java.nio.file.Path;

public class EffectTooltipsCommon {

    public static ConfigSchema config;

    public EffectTooltipsCommon(Path configPath) {
        this.config = ConfigSchema.load(configPath.resolve(Constants.MOD_ID + ".json").toFile());
    }
}