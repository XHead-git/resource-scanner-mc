package com.midnightbits.scanner.sonar;

import com.midnightbits.scanner.rt.core.Id;
import com.midnightbits.scanner.rt.math.V3i;
import com.midnightbits.scanner.utils.Clock;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record BlockEcho(V3i position, Id id, long pingTime) implements Comparable<BlockEcho> {

    public static BlockEcho echoFrom(V3i position, Id id) {
        return new BlockEcho(position, id, Clock.currentTimeMillis());
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        if (!(obj instanceof BlockEcho other)) {
            throw new ClassCastException();
        }
        return pingTime == other.pingTime && id.equals(other.id) && position.equals(other.position);
    }

    @Override
    public String toString() {
        final var builder = new StringBuilder();
        builder.append("new BlockEcho(new V3i(")
                .append(position.getX()).append(", ")
                .append(position.getY()).append(", ")
                .append(position.getZ()).append("), Id.of");
        if (Objects.equals(id.getNamespace(), Id.DEFAULT_NAMESPACE)) {
            builder.append("Vanilla(\"");
        } else {
            builder.append("(\"").append(id.getNamespace()).append("\", \"");
        }
        builder.append(id.getPath()).append("\"), ")
                .append(pingTime).append(")");
        return builder.toString();
    }

    @Override
    public int compareTo(@Nullable BlockEcho other) {
        if (other == null) {
            throw new NullPointerException();
        }
        var result = (int) (pingTime - other.pingTime);
        if (result != 0) {
            return result;
        }

        result = id.compareTo(other.id);
        if (result != 0)
            return result;

        return position.compareTo(other.position);
    }
}
