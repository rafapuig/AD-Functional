package model.academic;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.StringJoiner;

public record Modulo(
        Ciclo ciclo,
        String abreviatura,
        String nombre,
        Curso curso,
        int horas,
        OptionalInt horasSemanales

) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Modulo modulo)) return false;
        return Objects.equals(ciclo, modulo.ciclo) && Objects.equals(abreviatura, modulo.abreviatura);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ciclo, abreviatura);
    }

    public static class Builder{
        Integer horasSemanales;
        Integer horasTotal;
        Curso curso;
        Ciclo ciclo;
        String abreviatura;
        String nombre;

        public Builder(Ciclo ciclo, Curso curso, String abreviatura, String nombre) {
            this.ciclo = ciclo;
            this.curso = curso;
            this.abreviatura = abreviatura;
            this.nombre = nombre;
        }

        Builder setHorasSemanales(int horasSemanales) {
            this.horasSemanales = horasSemanales;
            if(curso.equals(Curso.PRIMERO)) {
                horasTotal = horasSemanales * 32;
            } else if (curso.equals(Curso.SEGUNDO)) {
                horasTotal = horasSemanales * 20;
            }
            return this;
        }

        Builder setHorasTotales(int horasTotales) {
            this.horasTotal = horasTotales;
            horasSemanales = null;
            return this;
        }

        public Modulo build() {
            OptionalInt horasSemanales =
                    this.horasSemanales == null ?
                            OptionalInt.empty() :
                            OptionalInt.of(this.horasSemanales);

            return new Modulo(ciclo, abreviatura, nombre, curso, horasTotal, horasSemanales);
        }


    }

}
