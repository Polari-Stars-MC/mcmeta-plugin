package org.polaris2023.mcmeta.extension.forge;

import lombok.Builder;
import org.polaris2023.mcmeta.api.IWrite;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;

/**
 * @author baka4n
 * {@code @Date : 2025/05/12 11:14:03}
 */
@Builder
public class ForgeLikeDependency implements IWrite {
    private final String modId;
    private final String versionRange;
    private final Order ordering;
    private final Side side;

    public static ForgeLikeDependency.ForgeLikeDependencyBuilder builder() {
        return new ForgeLikeDependency.ForgeLikeDependencyBuilder();
    }

    public ForgeLikeDependency(String modId, String versionRange, Order ordering, Side side) {
        this.modId = modId;
        this.versionRange = versionRange;
        this.ordering = ordering;
        this.side = side;
    }

    /**
     * @param bw
     * @throws IOException
     */
    @Override
    public void write(BufferedWriter bw) throws IOException {
        bw.write("modId=\"%s\"\n".formatted(modId()));
        bw.write("versionRange=\"%s\"\n".formatted(versionRange()));
        if (ordering() != null) bw.write("ordering=\"%s\"\n".formatted(ordering().name()));
        if (side() != null) bw.write("side=\"%s\"\n".formatted(side().name()));

    }

    public String modId() {
        return modId;
    }

    public String versionRange() {
        return versionRange;
    }

    public Order ordering() {
        return ordering;
    }

    public Side side() {
        return side;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ForgeLikeDependency) obj;
        return Objects.equals(this.modId, that.modId) &&
                Objects.equals(this.versionRange, that.versionRange) &&
                Objects.equals(this.ordering, that.ordering) &&
                Objects.equals(this.side, that.side);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modId, versionRange, ordering, side);
    }

    @Override
    public String toString() {
        return "ForgeLikeDependency[" +
                "modId=" + modId + ", " +
                "versionRange=" + versionRange + ", " +
                "ordering=" + ordering + ", " +
                "side=" + side + ']';
    }


    public enum Order {NONE, BEFORE, AFTER}

    public enum Side {BOTH, SERVER, CLIENT}
}
