package org.polaris2023.mcmeta.api;

import org.gradle.api.Action;
import org.gradle.api.provider.MapProperty;
import org.polaris2023.mcmeta.extension.forge.ForgeLikeDependency;

import java.util.Arrays;
import java.util.Map;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/12 11:16:23}
 */
public interface IDependencies extends IProject {
    MapProperty<String, ForgeLikeDependency[]> dependencies();
    default ForgeLikeDependency dependency(String modid, Action<ForgeLikeDependency.ForgeLikeDependencyBuilder> action) {
        Map<String, ForgeLikeDependency[]> stringMap = dependencies().get();
        ForgeLikeDependency.ForgeLikeDependencyBuilder builder = ForgeLikeDependency.builder();
        action.execute(builder);
        ForgeLikeDependency build = builder.build();
        if (!stringMap.containsKey(modid)) {
            stringMap.put(modid, new ForgeLikeDependency[]{build});
        } else {
            int length = stringMap.get(modid).length;
            ForgeLikeDependency[] dependencies1 = Arrays.copyOf(stringMap.get(modid), length + 1);
            dependencies1[length] = build;
            stringMap.put(modid, dependencies1);
        }
        return build;
    }

}
