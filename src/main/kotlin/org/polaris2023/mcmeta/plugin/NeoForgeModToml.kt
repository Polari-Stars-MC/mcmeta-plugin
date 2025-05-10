package org.polaris2023.mcmeta.plugin

import org.gradle.api.Project
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property

class NeoForgeModToml(project: Project) {
    var modLoader: Property<String> = project.objects.property(String::class.java).convention("javafml")
    var loaderVersion: Property<String> = project.objects.property(String::class.java).convention("[1,)")
    var license: Property<String> = project.objects.property(String::class.java)
    var showAsResource: Property<Boolean> = project.objects.property(Boolean::class.java)
    var logoFile: Property<String> = project.objects.property(String::class.java)
    var updateJSONURL: Property<String> = project.objects.property(String::class.java)
//    var mods: Property<ModToml> = project.objects.property(ModToml::class.java).convention(ModToml(project))
    class ModToml(project: Project) {
        var modId: Property<String> = project.objects.property(String::class.java).convention("examplemod")
        var version: Property<String> = project.objects.property(String::class.java).convention("0.0.1")
        var displayName: Property<String> = project.objects.property(String::class.java).convention("")
        var authors: Property<String> = project.objects.property(String::class.java)
        var description: Property<String> = project.objects.property(String::class.java)
        var logoFile: Property<String> = project.objects.property(String::class.java)
        var credits: Property<String> = project.objects.property(String::class.java)
        var displayURL: Property<String> = project.objects.property(String::class.java)
        var contacts: MapProperty<String, String> = project.objects.mapProperty(String::class.java, String::class.java).convention(emptyMap())
    }
}