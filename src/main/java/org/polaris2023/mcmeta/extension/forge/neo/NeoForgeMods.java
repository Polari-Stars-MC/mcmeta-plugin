package org.polaris2023.mcmeta.extension.forge.neo;

import org.gradle.api.Project;
import org.gradle.api.provider.MapProperty;
import org.polaris2023.mcmeta.extension.forge.ModLike;

import java.net.URI;
import java.util.HashMap;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/12 22:30:55}
 */
public class NeoForgeMods extends ModLike {
    public final MapProperty<String, URI> contact;

    public NeoForgeMods(Project project) {
        super(project);
        contact = project.getObjects().mapProperty(String.class, URI.class).convention(new HashMap<>());
    }
}
