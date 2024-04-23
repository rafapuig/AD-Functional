package model.tennis;

import java.util.Objects;
import java.util.StringJoiner;

public class TennisPlayer {
    String name;

    public TennisPlayer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TennisPlayer.class.getSimpleName() + "[", "]")
                .add("'" + name + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TennisPlayer that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
