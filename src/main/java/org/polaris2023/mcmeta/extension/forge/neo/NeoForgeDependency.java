package org.polaris2023.mcmeta.extension.forge.neo;

import lombok.Builder;
import org.polaris2023.mcmeta.api.IWrite;
import org.polaris2023.mcmeta.extension.forge.ForgeLikeDependency;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;

/**
 * @author baka4n
 */
@Builder
public record NeoForgeDependency(URI referralUrl, String reason, Type type, ForgeLikeDependency like) implements IWrite {

    public enum Type {
        required, optional, incompatible, discouraged
    }

    public static NeoForgeDependency.NeoForgeDependencyBuilder builder() {
        return new NeoForgeDependency.NeoForgeDependencyBuilder();
    }

    @Override
    public void write(BufferedWriter bw) throws IOException {
        like.write(bw);
        if (referralUrl != null) {
            bw.write("referralUrl=\"%s\"\n".formatted(referralUrl));
        }
        if (reason != null) {
            bw.write("reason=\"%s\"\n".formatted(reason));
        }
        bw.write("type=\"%s\"\n".formatted(type.name()));
    }
}
