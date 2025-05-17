package org.polaris2023.mcmeta.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
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
        target.afterEvaluate(project -> {
            SourceSetContainer sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
            SourceSet main = sourceSets.getByName("main");
            main.getAllJava().srcDir(
                    project.getLayout().getBuildDirectory()
                            .dir("generated/modMetaData").get().getAsFile().getAbsolutePath()
            );
            switch (mcMetaSettings.loaderType.get()) {
                case FABRIC -> {
                }
                case NEOFORGE -> {
                    NeoForgeLink.run(project, NeoForgeLink.tasks(target));
                }
                case FORGE -> {
                    ForgeLink.run(project, ForgeLink.tasks(target));
                }
                case QUILT -> {
                }
            }
        });
    }
}
