package org.polaris2023.mcmeta.extension.forge.neo;

import org.gradle.api.Project;
import org.gradle.api.provider.MapProperty;
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
    public final MapProperty<String, URI> contact;

    public NeoForgeMods(Project project) {
        super(project);
        contact = project.getObjects().mapProperty(String.class, URI.class).convention(new HashMap<>());
    }

    @Override
    public void write(BufferedWriter bw) throws IOException {
        super.write(bw);
        if (!contact.get().isEmpty()) {
            bw.write("[mods.contact]\n");
            for (Map.Entry<String, URI> entry : contact.get().entrySet()) {
                bw.write("%s, \"%s\"\n".formatted(entry.getKey(), entry.getValue()));
            }
        }
    }
}
