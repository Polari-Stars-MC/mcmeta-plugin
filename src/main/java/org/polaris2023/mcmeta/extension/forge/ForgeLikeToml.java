package org.polaris2023.mcmeta.extension.forge;

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
 * {@code @Date : 2025/05/12 09:37:54}
 * @apiNote NeoForge and Forge Same code
 */
public class ForgeLikeToml implements IWrite {
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
    @Override
    public void write(BufferedWriter bw) throws IOException {
        bw.write("modLoader=\"%s\"\n".formatted(modLoader.get()));
        bw.write("loaderVersion=\"%s\"\n".formatted(loaderVersion.get()));
        bw.write("license=\"%s\"\n".formatted(license.get()));
        if (showAsResourcePack.get()) bw.write("showAsResourcePack=\"%b\"\n".formatted(showAsResourcePack.get()));
        if (!services.get().isEmpty()) {
            bw.write("services=[\n");
            int i = 0;
            for (String service : services.get()) {
                bw.write("\t\"%s\"%s\n".formatted(service, i != services.get().size() - 1 ? ",": ""));
                i++;
            }
            bw.write("]\n");
        }
        if (!properties.get().isEmpty()) {
            bw.write("properties={\n");
            int i = 0;
            for (Map.Entry<String, String> e : properties.get().entrySet()) {
                bw.write("\t\"%s\"=\"%s\"%s\n".formatted(e.getKey(), e.getValue(), i != properties.get().size() - 1 ? ",": ""));
                i++;
            }
            bw.write("}\n");
        }
        if (issueTrackerURL.isPresent()) bw.write("issueTrackerURL=\"%s\"\n".formatted(issueTrackerURL.get()));



    }
}
