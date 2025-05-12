package org.polaris2023.mcmeta.extension.forge;

import org.gradle.api.Project;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/12 09:37:54}
 * @apiNote NeoForge and Forge Same code
 */
public class ForgeLikeToml {
    public final Property<String> modLoader;
    public final Property<String> loaderVersion;
    public final Property<String> license;
    public final Property<Boolean> showAsResourcePack;
    public final ListProperty<String> services;
    public final MapProperty<String, String> properties;
    public final Property<URI> issueTrackerURL;

    public ForgeLikeToml(Project project) {
        modLoader = project.getObjects().property(String.class).convention("javafml");
        loaderVersion = project.getObjects().property(String.class).convention("[0,)");
        license = project.getObjects().property(String.class).convention("All rights reserved");
        showAsResourcePack = project.getObjects().property(Boolean.class).convention(false);
        services = project.getObjects().listProperty(String.class).convention(new ArrayList<>());
        properties = project.getObjects().mapProperty(String.class, String.class).convention(new HashMap<>());
        issueTrackerURL = project.getObjects().property(URI.class);
    }
}
