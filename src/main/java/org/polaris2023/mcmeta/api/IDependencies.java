package org.polaris2023.mcmeta.api;

import org.gradle.api.Action;
import org.gradle.api.provider.MapProperty;
import org.polaris2023.mcmeta.extension.forge.ForgeLikeDependency;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author baka4n
 * {@code @Date : 2025/05/12 11:16:23}
 */
public interface IDependencies<T extends ForgeLikeDependency> extends IProject {
    MapProperty<String, T[]> dependencies();
    default T dependency(String modid, T t) {
        Map<String, T[]> stringMap = dependencies().get();

        if (!stringMap.containsKey(modid)) {

        } else {
            int length = stringMap.get(modid).length;
            T[] dependencies1 = Arrays.copyOf(stringMap.get(modid), length + 1);
            dependencies1[length] = t;
            stringMap.put(modid, dependencies1);
        }
        return t;
    }
    default void writeDependencies(BufferedWriter bw) throws IOException {

    }

}
