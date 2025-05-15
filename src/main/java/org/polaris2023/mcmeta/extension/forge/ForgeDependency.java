package org.polaris2023.mcmeta.extension.forge;

import lombok.Builder;

import java.io.BufferedWriter;
import java.io.IOException;

@Builder
public class ForgeDependency extends ForgeLikeDependency {
    private final Boolean mandatory;

    public Boolean mandatory() {
        return mandatory;
    }

    @Override
    public void write(BufferedWriter bw) throws IOException {
        super.write(bw);
        bw.write("mandatory=" + mandatory);
    }
}
