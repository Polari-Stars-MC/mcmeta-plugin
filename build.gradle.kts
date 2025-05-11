plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    kotlin("jvm").version("1.9.0")
    id("io.freefair.lombok").version("8.13.1")
    id("io.github.jeadyx.sonatype-uploader").version("2.8")
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
    compileOnly("net.neoforged:moddev-gradle:2.0.88")
}

gradlePlugin {
    plugins {
        create("mcmetaPlugin") {

            id = "io.github.Polari-Stars-MC.mcmeta-plugin"
            implementationClass = "org.polaris2023.mcmeta.plugin.McMetaPlugin"

        }
    }
}

tasks.javadoc {
    isFailOnError = false
    this.options.encoding = "UTF-8"
}

sonatypeUploader {
    tokenName = properties["central.sonatype.token.name"].toString()
    tokenPasswd = properties["central.sonatype.token.passwd"].toString()
    signing = Action {
        this.keyId = properties["signing.key.id"].toString()
        this.keyPasswd = properties["signing.key.passwd"].toString()
        this.secretKeyPath = properties["signing.secret.key"].toString()
    }
    pom = Action {
        name.set("MCMeta Plugin")
        description.set("Minecraft metadata generation")
        url.set("https://github.com/Polari-Stars-MC/mcmeta-plugin")
        licenses {
            license {
                name.set("LGPL-2.1")
                url = "https://www.gnu.org/licenses/old-licenses/lgpl-2.1.en.html"
            }
        }
        developers {
            developer {
                id.set("baka4n")
                name.set("baka4n")
                email.set("474899581@qq.com")
            }
        }
        scm {
            connection = "scm:git:git://github.com/Polaris-Stars-MC/mcmeta-plugin.git"
            developerConnection = "scm:git:ssh://github.com/Polaris-Stars-MC/mcmeta-plugin.git"
            url = "https://github.com/Polaris-Stars-MC/mcmeta-plugin"
        }
    }
}