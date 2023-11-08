package model.academic;

import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static model.academic.Ciclos.*;
import static model.academic.Curso.*;

public class Modulos {
    public static Modulo PRO =
            new Modulo(DAM, "PRO", "Programación",
                    PRIMERO, 8 * 32, OptionalInt.of(8));

    public static Modulo BDA =
            new Modulo.Builder(DAM, PRIMERO, "BDA",
                    "Bases de datos")
                    .setHorasSemanales(5).build();

    public static Modulo ADA =
            new Modulo.Builder(DAM, SEGUNDO, "ADA",
                    "Acceso a datos")
                    .setHorasSemanales(5).build();

    public static Modulo PMDM =
            new Modulo.Builder(DAM, SEGUNDO, "PMDM",
                    "Programación Multimedia y dispositivos móviles")
                    .setHorasSemanales(5).build();

    public static Modulo FCT =
            new Modulo.Builder(DAM, SEGUNDO, "FCT",
                    "Formación en centro de trabajo")
                    .setHorasTotales(400).build();

    public static Modulo PDAM =
            new Modulo.Builder(DAM, SEGUNDO, "PDAM",
                    "Proyecto de " + DAM.nombre())
                    .setHorasTotales(400).build();

    public static Set<Modulo> MODULOS = Set.of(PRO, BDA, ADA, PMDM, FCT, PDAM);

    public record ModuloKEY(Ciclo ciclo, String abreviatura) {
        public static ModuloKEY of(Modulo modulo) {
            return new ModuloKEY(modulo.ciclo(), modulo.abreviatura());
        }
    }

    public static Map<ModuloKEY, Modulo> MODULOS_MAP =
            MODULOS.stream()
                    .collect(Collectors.toMap(
                            ModuloKEY::of,
                            Function.identity()
                    ));

}
