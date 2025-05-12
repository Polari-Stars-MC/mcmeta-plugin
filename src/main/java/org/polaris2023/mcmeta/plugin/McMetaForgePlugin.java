package org.polaris2023.mcmeta.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskProvider;
import org.polaris2023.mcmeta.extension.forge.ForgeLikeToml;
import org.polaris2023.mcmeta.extension.forge.ForgeModsToml;
import org.polaris2023.mcmeta.extension.forge.neo.NeoForgeModsToml;
import org.polaris2023.mcmeta.linkage.ForgeLink;
import org.polaris2023.mcmeta.linkage.NeoForgeLink;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/13 00:51:18}
 */
public class McMetaForgePlugin implements Plugin<Project> {

    /**
     * @param target The target object
     */
    @Override
    public void apply(Project target) {
        ForgeLikeToml forgeLike = target.getExtensions().create("forgelike",ForgeLikeToml.class, target);
        ForgeModsToml forgeToml = target.getExtensions().create("forgeToml", ForgeModsToml.class, target);
        TaskProvider<Task> tasks = ForgeLink.tasks(target);
        target.afterEvaluate(project -> {
            ForgeLink.run(project, tasks);
        });
    }
}
