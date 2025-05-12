package org.polaris2023.mcmeta.extension.forge;

import lombok.Builder;

import java.net.URI;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/12 11:14:03}
 */
@Builder
public record ForgeLikeDependency(
        String modId,
        Boolean mandatory,
        String versionRange,
        Order ordering,
        Side side,
        URI referralUrl
) {
    public static ForgeLikeDependencyBuilder builder() {
        return new ForgeLikeDependencyBuilder();
    }

    public enum Order {NONE, BEFORE, AFTER}
    public enum Side {BOTH, SERVER, CLIENT}
}
