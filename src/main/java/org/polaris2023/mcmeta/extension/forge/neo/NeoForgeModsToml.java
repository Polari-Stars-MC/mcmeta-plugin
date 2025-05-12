package org.polaris2023.mcmeta.extension.forge.neo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.polaris2023.mcmeta.api.IDependencies;
import org.polaris2023.mcmeta.api.IWrite;
import org.polaris2023.mcmeta.extension.forge.ForgeLikeDependency;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Accessors(fluent = true)
public class NeoForgeModsToml implements IDependencies, IWrite {
    private final Project project;

    public final Property<Boolean> showAsDataPack;

    public final Property<String> logoFile;
    public final ListProperty<NeoForgeMods> mods;
    public final MapProperty<String, ForgeLikeDependency[]> dependencies;
    public final ListProperty<String> accessTransformers;
    public final ListProperty<String> mixins;

    public NeoForgeMods mods(Action<NeoForgeMods> action) {
        NeoForgeMods mods = new NeoForgeMods(project);
        action.execute(mods);
        this.mods.get().add(mods);
        return mods;
    }

    public void writeDependencies(BufferedWriter bw) throws IOException {
        for (var entry : dependencies.get().entrySet()) {
            ForgeLikeDependency[] value = entry.getValue();
            String key = entry.getKey();
            for (ForgeLikeDependency dependency : value) {
                bw.write("[[dependencies.%s]]\n".formatted(key));
                dependency.write(bw);
            }
        }
    }


    public NeoForgeModsToml(Project project) {
        this.project = project;
        showAsDataPack = project.getObjects().property(Boolean.class).convention(false);

        logoFile = project.getObjects().property(String.class);

        mods = project.getObjects().listProperty(NeoForgeMods.class).convention(new ArrayList<>());
        dependencies = project.getObjects().mapProperty(String.class, ForgeLikeDependency[].class).convention(new HashMap<>());
        accessTransformers = project.getObjects().listProperty(String.class).convention(new ArrayList<>());
        mixins = project.getObjects().listProperty(String.class).convention(new ArrayList<>());
    }


    /**
     * @param bw
     * @throws IOException
     */
    @Override
    public void write(BufferedWriter bw) throws IOException {
        if (showAsDataPack.get()) bw.write("showAsDataPack=\"%b\"\n".formatted(showAsDataPack.get()));
        if (logoFile.isPresent()) bw.write("logoFile=\"%s\"\n".formatted(logoFile.get()));
        for (NeoForgeMods mods : mods.get()) {
            bw.write("[[mods]]\n");
            mods.write(bw);
            if (!mods.contact.get().isEmpty()) {
                bw.write("[mods.contact]\n");
                for (Map.Entry<String, URI> entry : mods.contact.get().entrySet()) {
                    bw.write("%s, \"%s\"\n".formatted(entry.getKey(), entry.getValue()));
                }
            }
        }

        if (!accessTransformers.get().isEmpty()) {
            for (String accessTransformers : accessTransformers.get()) {
                bw.write("[[accessTransformers]]\n");
                bw.write("file=\"%s\"\n".formatted(accessTransformers));
            }
        }
        if (!mixins.get().isEmpty()) {
            for (String mixins : mixins.get()) {
                bw.write("[[mixins]]\n");
                bw.write("config=\"%s\"\n".formatted(mixins));
            }
        }
        writeDependencies(bw);

    }
}
