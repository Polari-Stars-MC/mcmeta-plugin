package org.polaris2023.mcmeta.extension;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.gradle.api.Project;
import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;

/**
 * @author baka4n
 */
@Getter
@Accessors(fluent = true)
public class McMetaSettings {
    public final Property<Type> loaderType;
    public final DirectoryProperty generatedDir;
    public McMetaSettings(Project project) {
        loaderType = project.getObjects().property(Type.class).convention(Type.DEFAULT);
        generatedDir = project.getObjects().directoryProperty()
                .convention(project.getLayout().getBuildDirectory().dir("generated/modMetaData").get());
    }

    public enum Type { DEFAULT, FABRIC, NEOFORGE, FORGE, QUILT }
}
