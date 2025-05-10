package org.polaris2023.mcmeta.plugin;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;

@Getter
@Accessors(fluent = true)
public class McMetaSettings {
    public final Property<Type> loaderType;
    public McMetaSettings(Project project) {
        loaderType = project.getObjects().property(Type.class).convention(Type.DEFAULT);
    }

    public enum Type { DEFAULT, FABRIC, NEOFORGE, FORGE, QUILT }
}
