package org.polaris2023.mcmeta.plugin;

import net.neoforged.moddevgradle.dsl.NeoForgeExtension;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author baka4n
 */
public class NeoForgeLink {
    public static void run(Project project, TaskProvider<Task> taskProvider) {
        NeoForgeExtension byType = project.getExtensions().getByType(NeoForgeExtension.class);
        byType.ideSyncTask(taskProvider);
    }
}
