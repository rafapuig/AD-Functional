package model.academic;

import java.util.Objects;

public record Ciclo(
     String abreviatura,
     String nombre)
{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ciclo ciclo)) return false;
        return Objects.equals(abreviatura, ciclo.abreviatura);
    }

    @Override
    public int hashCode() {
        return Objects.hash(abreviatura);
    }
}
