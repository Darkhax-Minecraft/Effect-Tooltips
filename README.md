# [Effect Tooltips](https://www.curseforge.com/minecraft/mc-mods/effect-tooltips)
This mod adds a tooltip to status effects displayed in the player inventory. The tooltip contains useful information about the effect such as the name of the mod that added it to the game. The mod also provides an event based API that allows other mods to add new tooltip entries or modify the tooltip in other ways.

## Provided Information
- The name of the effect.
- The level/amplifier of the effect.
- The remaining duration of the effect.
- The name of the mod that added the effect.
- The registry ID of the effect (Only when f3+H debug mode is enabled)

## Maven Dependency
If you are using [Gradle](https://gradle.org) to manage your dependencies, add the following into your `build.gradle` file. Make sure to replace the version with the correct one. All versions can be viewed [here](https://maven.mcmoddev.com/net/darkhax/effecttooltips/).
```
repositories {

    maven { url 'https://maven.blamejared.com' }
}

dependencies {

    implementation fg.deobf(group: 'net.darkhax.effecttooltips', name: 'EffectTooltips-Forge-1.16.5', version: '1.0.1')
}
```

## Jar Signing

As of January 11th 2021 officially published builds will be signed. You can validate the integrity of these builds by comparing their signatures with the public fingerprints.

| Hash   | Fingerprint                                                        |
|--------|--------------------------------------------------------------------|
| MD5    | `12F89108EF8DCC223D6723275E87208F`                                 |
| SHA1   | `46D93AD2DC8ADED38A606D3C36A80CB33EFA69D1`                         |
| SHA256 | `EBC4B1678BF90CDBDC4F01B18E6164394C10850BA6C4C748F0FA95F2CB083AE5` |