package org.polaris2023.mcmeta.plugin;

import net.neoforged.moddevgradle.dsl.NeoForgeExtension;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.Directory;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskProvider;
import org.polaris2023.mcmeta.extension.forge.ForgeLikeDependency;
import org.polaris2023.mcmeta.extension.forge.ForgeLikeToml;
import org.polaris2023.mcmeta.extension.forge.neo.NeoForgeModsToml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author baka4n
 */
public class NeoForgeLink {
    public static TaskProvider<Task> tasks(Project target) {
        return target.getTasks().register("generatedModsTomlByNeoForge",
                task -> {
                    ForgeLikeToml forgeLike = task.getProject().getExtensions().getByType(ForgeLikeToml.class);
                    NeoForgeModsToml neoforge = task.getProject().getExtensions().getByType(NeoForgeModsToml.class);
                    task.setGroup("mcmeta");
                    task.doLast(task1 -> {
                        Project project = task1.getProject();
                        Directory directory = project
                                .getLayout()
                                .getBuildDirectory()
                                .dir("generated/resources")
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
                            bw.write("modLoader=\"%s\"\n".formatted(forgeLike.modLoader.get()));
                            bw.write("loaderVersion=\"%s\"\n".formatted(forgeLike.loaderVersion.get()));
                            bw.write("license=\"%s\"\n".formatted(forgeLike.license.get()));
                            if (forgeLike.showAsResourcePack.get()) bw.write("showAsResourcePack=\"%b\"\n".formatted(forgeLike.showAsResourcePack.get()));
                            if (neoforge.showAsDataPack.get()) bw.write("showAsDataPack=\"%b\"\n".formatted(neoforge.showAsDataPack.get()));
                            if (!forgeLike.services.get().isEmpty()) {
                                bw.write("services=[\n");
                                int i = 0;
                                for (String service : forgeLike.services.get()) {
                                    bw.write("\t\"%s\"%s\n".formatted(service, i != forgeLike.services.get().size() - 1 ? ",": ""));
                                    i++;
                                }
                                bw.write("]\n");
                            }
                            if (!forgeLike.properties.get().isEmpty()) {
                                bw.write("properties={\n");
                                int i = 0;
                                for (Map.Entry<String, String> e : forgeLike.properties.get().entrySet()) {
                                    bw.write("\t\"%s\"=\"%s\"%s\n".formatted(e.getKey(), e.getValue(), i != forgeLike.properties.get().size() - 1 ? ",": ""));
                                    i++;
                                }
                                bw.write("}\n");
                            }
                            if (neoforge.logoFile.isPresent()) bw.write("logoFile=\"%s\"\n".formatted(neoforge.logoFile.get()));
                            if (forgeLike.issueTrackerURL.isPresent()) bw.write("issueTrackerURL=\"%s\"\n".formatted(forgeLike.issueTrackerURL.get()));
                            bw.write("[[mods]]\n");
                            var modList = neoforge.mods;
                            for (NeoForgeModsToml.Mods mods : modList.get()) {
                                bw.write("modId=\"%s\"\n".formatted(mods.modId.get()));
                                bw.write("version=\"%s\"\n".formatted(mods.version.get()));
                                bw.write("displayName=\"%s\"\n".formatted(mods.displayName.get()));
                                if (mods.description.isPresent()) bw.write("authors=```\n%s\n```\n".formatted(mods.description.get()));
                                if (mods.authors.isPresent()) bw.write("authors=\"%s\"\n".formatted(mods.authors.get()));
                                if (mods.logoFile.isPresent()) bw.write("logoFile=\"%s\"\n".formatted(mods.logoFile.get()));
                                if (mods.credits.isPresent()) bw.write("credits=\"%s\"\n".formatted(mods.credits.get()));
                                if (mods.displayURL.isPresent()) bw.write("displayURL=\"%s\"\n".formatted(mods.displayURL.get()));
                                if (!mods.contact.get().isEmpty()) {
                                    bw.write("[mods.contact]\n");
                                    for (Map.Entry<String, URI> entry : mods.contact.get().entrySet()) {
                                        bw.write("%s, \"%s\"\n".formatted(entry.getKey(), entry.getValue()));
                                    }
                                }
                            }
                            if (!neoforge.accessTransformers.get().isEmpty()) {
                                for (String accessTransformers : neoforge.accessTransformers.get()) {
                                    bw.write("[[accessTransformers]]\n");
                                    bw.write("file=\"%s\"\n".formatted(accessTransformers));
                                }
                            }
                            if (!neoforge.mixins.get().isEmpty()) {
                                for (String mixins : neoforge.mixins.get()) {
                                    bw.write("[[mixins]]\n");
                                    bw.write("config=\"%s\"\n".formatted(mixins));
                                }
                            }

                            for (var entry : neoforge.dependencies.get().entrySet()) {
                                ForgeLikeDependency[] value = entry.getValue();
                                String key = entry.getKey();
                                for (ForgeLikeDependency dependency : value) {
                                    bw.write("[[dependencies.%s]]\n".formatted(key));
                                    bw.write("modId=\"%s\"\n".formatted(dependency.modId()));
                                    if (dependency.mandatory() != null) bw.write("mandatory=\"%b\"\n".formatted(dependency.mandatory()));
                                    bw.write("versionRange=\"%s\"\n".formatted(dependency.versionRange()));
                                    if (dependency.ordering() != null) bw.write("ordering=\"%s\"\n".formatted(dependency.ordering().name()));
                                    if (dependency.side() != null) bw.write("side=\"%s\"\n".formatted(dependency.side().name()));
                                }

                            }
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
