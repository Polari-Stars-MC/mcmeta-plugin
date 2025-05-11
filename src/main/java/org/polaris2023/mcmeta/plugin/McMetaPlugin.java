package org.polaris2023.mcmeta.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskProvider;

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
public class McMetaPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        McMetaSettings mcMetaSettings = target.getExtensions().create("mcmeta-settings", McMetaSettings.class, target);
        NeoForgeModsToml neoforge = target.getExtensions().create("neoforge", NeoForgeModsToml.class, target);
        TaskProvider<Task> generatedModsTomlByNeoForge =
                target.getTasks().register("generatedModsTomlByNeoForge",
                task -> {
                    task.setGroup("mcmeta");
                    task.doLast(task1 -> {
                        Project project = task1.getProject();
                        File outputFile = project
                                .getLayout()
                                .getBuildDirectory()
                                .dir("generated/resources/META-INF")
                                .get()
                                .file("neoforge.mods.toml")
                                .getAsFile();
                        outputFile.getParentFile().mkdirs();
                        try(FileWriter fw = new FileWriter(outputFile, StandardCharsets.UTF_8);
                            BufferedWriter bw = new BufferedWriter(fw)
                        ) {
                            bw.write("modLoader=\"%s\"\n".formatted(neoforge.modLoader.get()));
                            bw.write("loaderVersion=\"%s\"\n".formatted(neoforge.loaderVersion.get()));
                            bw.write("license=\"%s\"\n".formatted(neoforge.license.get()));
                            if (neoforge.showAsResourcePack.get()) bw.write("showAsResourcePack=\"%b\"\n".formatted(neoforge.showAsResourcePack.get()));
                            if (neoforge.showAsDataPack.get()) bw.write("showAsDataPack=\"%b\"\n".formatted(neoforge.showAsDataPack.get()));
                            if (!neoforge.services.get().isEmpty()) {
                                bw.write("services=[\n");
                                int i = 0;
                                for (String service : neoforge.services.get()) {
                                    bw.write("\t\"%s\"%s\n".formatted(service, i != neoforge.services.get().size() - 1 ? ",": ""));
                                    i++;
                                }
                                bw.write("]\n");
                            }
                            if (!neoforge.properties.get().isEmpty()) {
                                bw.write("properties={\n");
                                int i = 0;
                                for (Map.Entry<String, String> e : neoforge.properties.get().entrySet()) {
                                    bw.write("\t\"%s\"=\"%s\"%s\n".formatted(e.getKey(), e.getValue(), i != neoforge.properties.get().size() - 1 ? ",": ""));
                                    i++;
                                }
                                bw.write("}\n");
                            }
                            if (neoforge.logoFile.isPresent()) bw.write("logoFile=\"%s\"\n".formatted(neoforge.logoFile.get()));
                            if (neoforge.issueTrackerURL.isPresent()) bw.write("issueTrackerURL=\"%s\"\n".formatted(neoforge.issueTrackerURL.get()));
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

                            for (Map.Entry<String, NeoForgeModsToml.Dependency> entry : neoforge.dependencies.get().entrySet()) {
                                bw.write("[[dependencies.%s]]\n".formatted(entry.getValue()));
                                var dependency = entry.getValue();
                                bw.write("modId=\"%s\"\n".formatted(dependency.modId()));
                                if (dependency.mandatory() != null) bw.write("mandatory=\"%b\"\n".formatted(dependency.mandatory()));
                                bw.write("versionRange=\"%s\"\n".formatted(dependency.versionRange()));
                                if (dependency.ordering() != null) bw.write("ordering=\"%s\"\n".formatted(dependency.ordering().name()));
                                if (dependency.side() != null) bw.write("side=\"%s\"\n".formatted(dependency.side().name()));
                            }
                        } catch (IOException e) {
                            project.getLogger().error(e.getMessage());
                        }
                    });
                });

        target.afterEvaluate(project -> {
            switch (mcMetaSettings.loaderType.get()) {
                case FABRIC -> {
                }
                case NEOFORGE -> {
                    NeoForgeLink.run(project, generatedModsTomlByNeoForge);
                }
                case FORGE -> {

                }
                case QUILT -> {
                }
            }
        });
    }
}
