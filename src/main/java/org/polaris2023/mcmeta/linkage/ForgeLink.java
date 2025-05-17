package org.polaris2023.mcmeta.linkage;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.Directory;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.language.jvm.tasks.ProcessResources;
import org.polaris2023.mcmeta.extension.McMetaSettings;
import org.polaris2023.mcmeta.extension.forge.ForgeLikeToml;
import org.polaris2023.mcmeta.extension.forge.ForgeModsToml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/12 11:51:09}
 */
public class ForgeLink {
    public static TaskProvider<Task> tasks(Project target) {
        return target.getTasks().register("generatedModTomlByForge", task -> {
            task.setGroup("mcmeta");
            Project project = task.getProject();
            McMetaSettings settings = project.getExtensions().getByType(McMetaSettings.class);
            ForgeLikeToml forgeLike = project.getExtensions().getByType(ForgeLikeToml.class);
            ForgeModsToml forge = project.getExtensions().getByType(ForgeModsToml.class);
            File outputFile = settings.generatedDir.get()
                    .dir("META-INF")
                    .file("mods.toml")
                    .getAsFile();
            try(FileWriter fileWriter = new FileWriter(outputFile);
            BufferedWriter bw = new BufferedWriter(fileWriter)) {
                forgeLike.write(bw);
                forge.write(bw);

            } catch (Exception e) {
                project.getLogger().error(e.getMessage());
            }
        });
    }

    public static void run(Project project, TaskProvider<Task> task) {
        project.getTasks().named("processResources", ProcessResources.class).configure(t ->
                t.dependsOn(task));
    }
}
