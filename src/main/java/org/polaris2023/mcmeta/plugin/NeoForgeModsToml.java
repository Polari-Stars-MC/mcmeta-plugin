package org.polaris2023.mcmeta.plugin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.gradle.api.Project;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@Accessors(fluent = true)
public class NeoForgeModsToml {
    private final Project project;
    public final Property<String> modLoader;
    public final Property<String> loaderVersion;
    public final Property<String> license;
    public final Property<Boolean> showAsResourcePack;
    public final Property<Boolean> showAsDataPack;
    public final ListProperty<String> services;
    public final Property<String> logoFile;
    public final Property<URI> issueTrackerURL;
    public final MapProperty<String, String> properties;
    public final ListProperty<Mods> mods;
    public final MapProperty<String, Dependency> dependencies;
    public final ListProperty<String> accessTransformers;
    public final ListProperty<String> mixins;

    public Mods makeMods() {
        return new Mods(project);
    }

    public NeoForgeModsToml(Project project) {
        this.project = project;
        modLoader = project.getObjects().property(String.class).convention("javafml");
        loaderVersion = project.getObjects().property(String.class).convention("[0,)");
        license = project.getObjects().property(String.class).convention("All rights reserved");
        showAsResourcePack = project.getObjects().property(Boolean.class).convention(false);
        showAsDataPack = project.getObjects().property(Boolean.class).convention(false);
        services = project.getObjects().listProperty(String.class).convention(new ArrayList<>());
        logoFile = project.getObjects().property(String.class);
        issueTrackerURL = project.getObjects().property(URI.class);
        properties = project.getObjects().mapProperty(String.class, String.class);
        mods = project.getObjects().listProperty(Mods.class).convention(new ArrayList<>());
        dependencies = project.getObjects().mapProperty(String.class, Dependency.class).convention(new HashMap<>());
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

    public enum Order {NONE, BEFORE, AFTER}
    public enum Side {BOTH, SERVER, CLIENT}

    @Builder
    public record Dependency(
            String modId,
            Boolean mandatory,
            String versionRange,
            Order ordering,
            Side side

    ) {}


}
