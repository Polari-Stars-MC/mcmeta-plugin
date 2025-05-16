package org.polaris2023.mcmeta.extension.forge.neo;

import org.gradle.api.Project;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.polaris2023.mcmeta.extension.forge.ModLike;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/12 22:30:55}
 */
public class NeoForgeMods extends ModLike {
    public final Property<String> enumExtensions;
    public final Property<String> featureFlags;
    public NeoForgeMods(Project project) {
        super(project);
        enumExtensions = project.getObjects().property(String.class);
        featureFlags = project.getObjects().property(String.class);
    }

    @Override
    public void write(BufferedWriter bw) throws IOException {
        super.write(bw);
        if (enumExtensions.isPresent()) bw.write("enumExtensions=\"%s\"".formatted(enumExtensions.get()));
        if (featureFlags.isPresent()) bw.write("featureFlags=\"%s\"".formatted(featureFlags.get()));
    }
}
