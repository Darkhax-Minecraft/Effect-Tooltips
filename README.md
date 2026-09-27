<!-- name-start -->
# EffectTooltips [![CurseForge Project](https://img.shields.io/curseforge/dt/520367?logo=curseforge&label=CurseForge&style=flat-square&labelColor=2D2D2D&color=555555)](https://www.curseforge.com/minecraft/mc-mods/effect-tooltips) [![Modrinth Project](https://img.shields.io/modrinth/dt/qc0LobOe?logo=modrinth&label=Modrinth&style=flat-square&labelColor=2D2D2D&color=555555)](https://modrinth.com/mod/effect-tooltips) [![Maven Project](https://img.shields.io/maven-metadata/v?style=flat-square&logoColor=D31A38&labelColor=2D2D2D&color=555555&label=Latest&logo=gradle&metadataUrl=https%3A%2F%2Fmaven.blamejared.com%2Fnet%2Fdarkhax%2Feffecttooltips%2Feffecttooltips-common-26.3%2Fmaven-metadata.xml)](https://maven.blamejared.com/net/darkhax/effecttooltips)
<!-- name-end -->

<!-- description-start -->
This is the official GitHub repo for the EffectTooltips mod. Adds tooltips when you hover over status effects. You can download this mod from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/effect-tooltips) or [Modrinth](https://modrinth.com/mod/effect-tooltips). Please report issues [here](https://github.com/Darkhax-Minecraft/Effect-Tooltips/issues).
<!-- description-end -->

<!-- maven-start -->
## Maven Dependency
This project is available on the [BlameJared Maven](https://maven.blamejared.com).

If you are using [Gradle](https://gradle.org) you can add the mod as a dependency by adding the following
to your `build.gradle` file.

```groovy
repositories {
    maven {
        url 'https://maven.blamejared.com'
    }
}

dependencies {
     // NeoForge
     implementation group: 'net.darkhax.effecttooltips', name: 'effecttooltips-neoforge-26.3', version: '26.3.0.0'
     // Fabric
     implementation group: 'net.darkhax.effecttooltips', name: 'effecttooltips-fabric-26.3', version: '26.3.0.0'
     // Common / MultiLoader / Vanilla / No Loader
     implementation group: 'net.darkhax.effecttooltips', name: 'effecttooltips-common-26.3', version: '26.3.0.0'
}
```

<!-- maven-end -->

<!-- sponsor-start -->
## Sponsors
[![](https://assets.blamejared.com/nodecraft/darkhax.jpg)](https://nodecraft.com/r/darkhax)    
EffectTooltips is proudly sponsored by Nodecraft! Play your favorite games with your friends using their high
performance game servers! Use code **[DARKHAX](https://nodecraft.com/r/darkhax)** for 30% off your first
month of service!

<!-- sponsor-end -->