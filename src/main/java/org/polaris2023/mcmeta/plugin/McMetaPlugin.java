package org.polaris2023.mcmeta.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskProvider;
import org.polaris2023.mcmeta.extension.McMetaSettings;
import org.polaris2023.mcmeta.extension.forge.ForgeLikeToml;
import org.polaris2023.mcmeta.extension.forge.ForgeModsToml;
import org.polaris2023.mcmeta.extension.forge.neo.NeoForgeModsToml;
import org.polaris2023.mcmeta.linkage.ForgeLink;
import org.polaris2023.mcmeta.linkage.NeoForgeLink;

/**
 * @author baka4n
 */
public class McMetaPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        McMetaSettings mcMetaSettings = target.getExtensions().create("mcmetasettings", McMetaSettings.class, target);
        ForgeLikeToml forgeLike = target.getExtensions().create("forgelike",ForgeLikeToml.class, target);
        NeoForgeModsToml neoforge = target.getExtensions().create("neoforge", NeoForgeModsToml.class, target);
        ForgeModsToml forgeToml = target.getExtensions().create("forgeToml", ForgeModsToml.class, target);
        TaskProvider<Task> generatedModsTomlByNeoForge = NeoForgeLink.tasks(target);
        TaskProvider<Task> generatedModTomlByForge = ForgeLink.tasks(target);
        target.afterEvaluate(project -> {
            switch (mcMetaSettings.loaderType.get()) {
                case FABRIC -> {
                }
                case NEOFORGE -> {
                    NeoForgeLink.run(project, generatedModsTomlByNeoForge);
                }
                case FORGE -> {
                    ForgeLink.run(project, generatedModTomlByForge);
                }
                case QUILT -> {
                }
            }
        });
    }
}
