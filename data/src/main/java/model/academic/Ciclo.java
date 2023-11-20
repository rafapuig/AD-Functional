package model.academic;

import java.util.Objects;
import java.util.Set;

public record Ciclo(
     String abreviatura,
     String nombre
    )
{

    public Set<Modulo> getModulos() {
        return Modulos.MODULOS_BY_CICLO.get(this);
    }

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
