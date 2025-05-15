package org.polaris2023.mcmeta.extension.forge.neo;

import lombok.Builder;
import org.polaris2023.mcmeta.extension.forge.ForgeLikeDependency;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;

@Builder
public class NeoForgeDependency extends ForgeLikeDependency {
    private final URI referralUrl;
    private final String reason;
    private final Type type;

    public enum Type {
        required, optional, incompatible, discouraged
    }

    public URI referralUrl() {
        return referralUrl;
    }

    @Override
    public void write(BufferedWriter bw) throws IOException {
        super.write(bw);
        if (referralUrl != null) {
            bw.write("referralUrl=\"%s\"".formatted(referralUrl));
        }
        if (reason != null) {
            bw.write("reason=\"%s\"".formatted(reason));
        }
        bw.write("type=\"%s\"".formatted(type.name()));
    }
}
