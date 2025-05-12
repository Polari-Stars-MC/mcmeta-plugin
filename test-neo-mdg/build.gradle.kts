import org.polaris2023.mcmeta.plugin.McMetaSettings
import org.polaris2023.mcmeta.plugin.NeoForgeModsToml

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("io.github.polari-stars-mc:mcmeta-plugin:0.0.1-fix")
    }
}

plugins {
    java
    id("net.neoforged.moddev").version("2.0.88")
    //id("io.github.Polari-Stars-MC.mcmeta-plugin").version("0.0.1")
}

apply(plugin = "io.github.Polari-Stars-MC.mcmeta-plugin")


neoForge {
    version = "21.1.169"
    parchment {
        minecraftVersion = "1.21.1"
        mappingsVersion = "2024.11.17"
    }
}

configure<McMetaSettings> {
    this.loaderType = McMetaSettings.Type.NEOFORGE
}

configure<NeoForgeModsToml> {

}

//neoforge {
//}