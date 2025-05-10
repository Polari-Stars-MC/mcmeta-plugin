plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
    kotlin("jvm").version("1.9.0")
    id("io.freefair.lombok").version("8.13.1")
}

val projGroup: String by rootProject
val projVersion: String by rootProject

group = projGroup
version = projVersion

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("stdlib"))
}

gradlePlugin {
    plugins {
        create("mcmetaPlugin") {

            id = "io.github.Polari-Stars-MC.mcmeta-plugin"
            implementationClass = "org.polaris2023.mcmeta.plugin.McMetaPlugin"

        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}