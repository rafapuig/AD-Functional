package model.academic;

import java.util.Optional;

public record Calificacion(
    Alumno alumno,
    Modulo modulo,
    Optional<Nota> nota
) {

    public boolean isSuperado() {
        return nota().isPresent() && nota().get().getNumericValue() >= 5;
    }

}
