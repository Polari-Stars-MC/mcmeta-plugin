package org.polaris2023.mcmeta.extension.forge;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.polaris2023.mcmeta.api.IWrite;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/12 21:57:21}
 */
public class ModLike implements IWrite {
    public final Property<String> modId;
    public final Property<String> version;
    public final Property<String> displayName;
    public final Property<String> description;
    public final Property<String> authors;
    public final Property<String> logoFile;
    public final Property<String> credits;
    public final Property<URI> displayURL;

    public ModLike(Project project) {
        modId = project.getObjects().property(String.class).convention("examplemod");
        version = project.getObjects().property(String.class).convention("0.0.1");
        displayName = project.getObjects().property(String.class).convention("Example Mod");
        description = project.getObjects().property(String.class);
        authors = project.getObjects().property(String.class);
        logoFile = project.getObjects().property(String.class);
        credits = project.getObjects().property(String.class);
        displayURL = project.getObjects().property(URI.class);
    }

    /**
     * @param bw write in file
     * @throws IOException error to write
     */
    @Override
    public void write(BufferedWriter bw) throws IOException {
        bw.write("[[mods]]\n");
        bw.write("modId=\"%s\"\n".formatted(modId.get()));
        bw.write("version=\"%s\"\n".formatted(version.get()));
        bw.write("displayName=\"%s\"\n".formatted(displayName.get()));
        if (description.isPresent()) bw.write("description=‘’‘\n%s\n’‘’\n".formatted(description.get()));
        if (authors.isPresent()) bw.write("authors=\"%s\"\n".formatted(authors.get()));
        if (logoFile.isPresent()) bw.write("logoFile=\"%s\"\n".formatted(logoFile.get()));
        if (credits.isPresent()) bw.write("credits=\"%s\"\n".formatted(credits.get()));
        if (displayURL.isPresent()) bw.write("displayURL=\"%s\"\n".formatted(displayURL.get()));



    }
}
