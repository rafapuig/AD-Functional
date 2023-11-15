package model.academic;

import java.util.Optional;

public record Calificacion(
    Alumno alumno,
    Modulo modulo,
    Optional<Nota> nota
) {}
