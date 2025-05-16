package org.polaris2023.mcmeta.extension.forge;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.polaris2023.mcmeta.api.IWrite;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/12 09:37:32}
 */
@Getter
@Accessors(fluent = true)
@Setter
public class ForgeModsToml implements IWrite {
    public final Project project;
    public final Property<Boolean> clientSideOnly;
    public final ListProperty<Mods> mods;
    public final MapProperty<String, ForgeDependency[]> dependencies;
    public ForgeModsToml(Project project) {
        this.project = project;
        clientSideOnly = project.getObjects().property(Boolean.class).convention(false);
        mods = project.getObjects().listProperty(Mods.class).convention(new ArrayList<>());
        dependencies = project.getObjects().mapProperty(String.class, ForgeDependency[].class).convention(new HashMap<>());
    }

    public Mods mods(Action<Mods> action) {
        Mods mods = new Mods(project);
        action.execute(mods);
        this.mods.add(mods);
        return mods;
    }

    /**
     * @param bw
     * @throws IOException
     */
    @Override
    public void write(BufferedWriter bw) throws IOException {
        if (clientSideOnly.get()) bw.write("clientSideOnly=true\n");

        for (ForgeModsToml.Mods mods : mods.get()) {
            mods.write(bw);
        }
        for (var entry : dependencies().get().entrySet()) {
            ForgeDependency[] value = entry.getValue();
            String key = entry.getKey();
            for (ForgeDependency dependency : value) {
                bw.write("[[dependencies.%s]]\n".formatted(key));
                dependency.write(bw);
            }
        }

    }

    public static class Mods extends ModLike {





        public final Property<String> displayTest;
        public Mods(Project project) {
            super(project);
            displayTest = project.getObjects().property(String.class);
        }

        @Override
        public void write(BufferedWriter bw) throws IOException {
            super.write(bw);


            if (displayTest.isPresent()) bw.write("displayTest=\"%s\"\n".formatted(displayTest.get()));
        }
    }

}
