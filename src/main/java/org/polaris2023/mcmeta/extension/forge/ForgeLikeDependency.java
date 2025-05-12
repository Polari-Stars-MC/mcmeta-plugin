package org.polaris2023.mcmeta.extension.forge;

import lombok.Builder;
import org.polaris2023.mcmeta.api.IWrite;

import java.io.BufferedWriter;
import java.io.IOException;
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
) implements IWrite {
    public static ForgeLikeDependencyBuilder builder() {
        return new ForgeLikeDependencyBuilder();
    }

    /**
     * @param bw
     * @throws IOException
     */
    @Override
    public void write(BufferedWriter bw) throws IOException {

        bw.write("modId=\"%s\"\n".formatted(modId()));
        if (mandatory() != null) bw.write("mandatory=\"%b\"\n".formatted(mandatory()));
        bw.write("versionRange=\"%s\"\n".formatted(versionRange()));
        if (ordering() != null) bw.write("ordering=\"%s\"\n".formatted(ordering().name()));
        if (side() != null) bw.write("side=\"%s\"\n".formatted(side().name()));

    }

    public enum Order {NONE, BEFORE, AFTER}
    public enum Side {BOTH, SERVER, CLIENT}
}
