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

    public static class Mods {
        public final Property<String> modId;
        public final Property<String> namespace;
        public final Property<String> version;
        public final Property<String> displayName;
        public final Property<String> description;
        public final Property<String> logoFile;
        public final Property<Boolean> logoBlur;
        public final Property<URI> updateJSONURL;
        public final MapProperty<String, String> features;
        public final MapProperty<String, String> modproperties;
        public final Property<URI> modUrl;
        public final Property<String> credits;
        public final Property<String> authors;
        public final Property<URI> displayURL;
        public final Property<String> displayTest;
        public Mods(Project project) {
            modId = project.getObjects().property(String.class).convention("examplemod");
            namespace = project.getObjects().property(String.class);
            version = project.getObjects().property(String.class).convention("0.0.1");
            displayName = project.getObjects().property(String.class).convention("Example Mod");
            description = project.getObjects().property(String.class).convention("This is Example Mod");
            logoFile = project.getObjects().property(String.class);
            logoBlur = project.getObjects().property(Boolean.class).convention(true);
            updateJSONURL = project.getObjects().property(URI.class);
            features = project.getObjects().mapProperty(String.class, String.class).convention(new HashMap<>());
            modproperties = project.getObjects().mapProperty(String.class, String.class).convention(new HashMap<>());
            modUrl = project.getObjects().property(URI.class);
            credits = project.getObjects().property(String.class);
            authors = project.getObjects().property(String.class);
            displayURL = project.getObjects().property(URI.class);
            displayTest = project.getObjects().property(String.class);
        }
    }

}
