package org.polaris2023.mcmeta.extension.forge;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.polaris2023.mcmeta.api.IDependencies;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/12 09:37:32}
 */
@Getter
@Accessors(fluent = true)
@Setter
public class ForgeModsToml implements IDependencies {
    public final Project project;
    public final Property<Boolean> clientSideOnly;
    public final ListProperty<Mods> mods;
    public final MapProperty<String, ForgeLikeDependency[]> dependencies;
    public ForgeModsToml(Project project) {
        this.project = project;
        clientSideOnly = project.getObjects().property(Boolean.class).convention(false);
        mods = project.getObjects().listProperty(Mods.class).convention(new ArrayList<>());
        dependencies = project.getObjects().mapProperty(String.class, ForgeLikeDependency[].class).convention(new HashMap<>());
    }

    public Mods mods(Action<Mods> action) {
        Mods mods = new Mods(project);
        action.execute(mods);
        this.mods.add(mods);
        return mods;
    }

    public static class Mods extends ModLike {

        public final Property<String> namespace;
        public final Property<Boolean> logoBlur;
        public final Property<URI> updateJSONURL;
        public final MapProperty<String, String> features;
        public final MapProperty<String, String> modproperties;
        public final Property<URI> modUrl;
        public final Property<String> displayTest;
        public Mods(Project project) {
            super(project);
            namespace = project.getObjects().property(String.class).convention(modId.get());
            logoBlur = project.getObjects().property(Boolean.class).convention(true);
            updateJSONURL = project.getObjects().property(URI.class);
            features = project.getObjects().mapProperty(String.class, String.class).convention(new HashMap<>());
            modproperties = project.getObjects().mapProperty(String.class, String.class).convention(new HashMap<>());
            modUrl = project.getObjects().property(URI.class);
            displayTest = project.getObjects().property(String.class);
        }
    }

}
