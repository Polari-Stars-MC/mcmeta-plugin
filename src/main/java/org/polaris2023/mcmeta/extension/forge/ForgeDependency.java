package org.polaris2023.mcmeta.extension.forge;

import lombok.Builder;
import org.polaris2023.mcmeta.api.IWrite;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * @author baka4n
 */
@Builder
public record ForgeDependency(Boolean mandatory, ForgeLikeDependency like) implements IWrite {

    public static ForgeDependency.ForgeDependencyBuilder builder() {
        return new ForgeDependency.ForgeDependencyBuilder();
    }

    public Boolean mandatory() {
        return mandatory;
    }

    @Override
    public void write(BufferedWriter bw) throws IOException {
        like.write(bw);
        bw.write("mandatory=" + mandatory + "\n");
    }
}
