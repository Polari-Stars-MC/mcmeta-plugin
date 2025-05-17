package org.polaris2023.mcmeta.linkage;

import net.neoforged.moddevgradle.dsl.NeoForgeExtension;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.Directory;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskProvider;
import org.polaris2023.mcmeta.extension.forge.ForgeLikeToml;
import org.polaris2023.mcmeta.extension.forge.neo.NeoForgeModsToml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author baka4n
 */
public class NeoForgeLink {
    public static TaskProvider<Task> tasks(Project target) {
        return target.getTasks().register("generatedModsTomlByNeoForge", task -> {
            ForgeLikeToml forgeLike = task.getProject().getExtensions().getByType(ForgeLikeToml.class);
            NeoForgeModsToml neoforge = task.getProject().getExtensions().getByType(NeoForgeModsToml.class);
            task.setGroup("mcmeta");
            task.doLast(task1 -> {
                Project project = task1.getProject();
                Directory directory = project
                        .getLayout()
                        .getBuildDirectory()
                        .dir("generated/modMetaData")
                        .get();
                SourceSetContainer sourceSets = target.getExtensions().getByType(SourceSetContainer.class);
                SourceSet main = sourceSets.getByName("main");
                main.getAllJava().srcDir(directory);
                File outputFile = directory
                        .dir("META-INF")
                        .file("neoforge.mods.toml")
                        .getAsFile();
                outputFile.getParentFile().mkdirs();
                try(FileWriter fw = new FileWriter(outputFile, StandardCharsets.UTF_8);
                    BufferedWriter bw = new BufferedWriter(fw)
                ) {
                    forgeLike.write(bw);
                    neoforge.write(bw);
                } catch (IOException e) {
                    project.getLogger().error(e.getMessage());
                }
            });
        });
    }
    public static void run(Project project, TaskProvider<Task> taskProvider) {

        NeoForgeExtension byType = project.getExtensions().getByType(NeoForgeExtension.class);
        byType.ideSyncTask(taskProvider);
    }
}
