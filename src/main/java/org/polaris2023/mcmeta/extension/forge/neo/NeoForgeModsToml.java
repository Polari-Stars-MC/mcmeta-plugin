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
import org.polaris2023.mcmeta.extension.forge.ForgeLikeDependency;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@Accessors(fluent = true)
public class NeoForgeModsToml implements IDependencies {
    private final Project project;

    public final Property<Boolean> showAsDataPack;

    public final Property<String> logoFile;
    public final ListProperty<Mods> mods;
    public final MapProperty<String, ForgeLikeDependency[]> dependencies;
    public final ListProperty<String> accessTransformers;
    public final ListProperty<String> mixins;

    public Mods mods(Action<Mods> action) {
        Mods mods = new Mods(project);
        action.execute(mods);
        this.mods.get().add(mods);
        return mods;
    }


    public NeoForgeModsToml(Project project) {
        this.project = project;
        showAsDataPack = project.getObjects().property(Boolean.class).convention(false);

        logoFile = project.getObjects().property(String.class);

        mods = project.getObjects().listProperty(Mods.class).convention(new ArrayList<>());
        dependencies = project.getObjects().mapProperty(String.class, ForgeLikeDependency[].class).convention(new HashMap<>());
        accessTransformers = project.getObjects().listProperty(String.class).convention(new ArrayList<>());
        mixins = project.getObjects().listProperty(String.class).convention(new ArrayList<>());
    }

    public static class Mods {
        public final Property<String> modId;
        public final Property<String> version;
        public final Property<String> displayName;
        public final Property<String> authors;
        public final Property<String> description;
        public final Property<String> logoFile;
        public final Property<String> credits;
        public final Property<URI> displayURL;
        public final MapProperty<String, URI> contact;
        public Mods(Project project) {
            modId = project.getObjects().property(String.class).convention("examplemod");
            version = project.getObjects().property(String.class).convention("0.0.1");
            displayName = project.getObjects().property(String.class).convention("Example Mod");
            authors = project.getObjects().property(String.class);
            description = project.getObjects().property(String.class);
            logoFile = project.getObjects().property(String.class);
            credits = project.getObjects().property(String.class);
            displayURL = project.getObjects().property(URI.class);
            contact = project.getObjects().mapProperty(String.class, URI.class).convention(new HashMap<>());
        }
    }




}
