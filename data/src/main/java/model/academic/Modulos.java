package model.academic;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static model.academic.Ciclos.*;
import static model.academic.Curso.*;

public class Modulos {
    public static Modulo PRO =
            new Modulo(DAM, "PRO", "Programación",
                    PRIMERO, 8 * 32, OptionalInt.of(8));
    public static Modulo DAW_PRO =
            new Modulo.Builder(PRO).setCiclo(DAW).build();

    public static Modulo CV003 =
            new Modulo.Builder(DAM, PRIMERO, "CV003",
                    "Inglés Técnico I-S")
                    .setHorasSemanales(3).build();

    public static Modulo DAW_CV003 = new Modulo.Builder(CV003)
            .setCiclo(DAW).build();
    public static Modulo FOL =
            new Modulo.Builder(DAM, PRIMERO, "FOL",
                    "Formación y Orientación Laboral")
                    .setHorasSemanales(3).build();

    public static Modulo DAW_FOL = new Modulo.Builder(FOL)
            .setCiclo(DAW).build();

    public static Modulo LMSGI =
            new Modulo.Builder(DAM, PRIMERO, "LMSGI",
                    "Lenguajes de marcas y sistemas de gestión de información")
                    .setHorasSemanales(3).build();

    public static Modulo DAW_LMSGI = new Modulo.Builder(LMSGI)
            .setCiclo(DAW).build();

    public static Modulo EDE =
            new Modulo.Builder(DAM, PRIMERO, "ED",
                    "Entornos de Desarrollo")
                    .setHorasSemanales(3).build();

    public static Modulo DAW_EDE = new Modulo.Builder(EDE)
            .setCiclo(DAW).build();

    public static Modulo SIN =
            new Modulo.Builder(DAM, PRIMERO, "SIN",
                    "Sistemas Informáticos")
                    .setHorasSemanales(5).build();

    public static Modulo DAW_SIN = new Modulo.Builder(SIN)
            .setCiclo(DAW).build();

    public static Modulo BDA =
            new Modulo.Builder(DAM, PRIMERO, "BDA",
                    "Bases de datos")
                    .setHorasSemanales(5).build();

    public static Modulo DAW_BDA = new Modulo.Builder(BDA)
            .setCiclo(DAW).build();

    public static Modulo ADA =
            new Modulo.Builder(DAM, SEGUNDO, "ADA",
                    "Acceso a datos")
                    .setHorasSemanales(6).build();
    public static Modulo DIN =
            new Modulo.Builder(DAM, SEGUNDO, "DIN",
                    "Desarrollo de interfaces")
                    .setHorasSemanales(6).build();

    public static Modulo PMDM =
            new Modulo.Builder(DAM, SEGUNDO, "PMDM",
                    "Programación Multimedia y dispositivos móviles")
                    .setHorasSemanales(5).build();

    public static Modulo PSP =
            new Modulo.Builder(DAM, SEGUNDO, "PSP",
                    "Programación de servicios y procesos")
                    .setHorasSemanales(3).build();

    public static Modulo SGE =
            new Modulo.Builder(DAM, SEGUNDO, "SGE",
                    "Sistemas de gestión empresarial")
                    .setHorasSemanales(5).build();

    public static Modulo EIE =
            new Modulo.Builder(DAM, SEGUNDO, "EIE",
                    "Empresa e iniciativa emprendedora")
                    .setHorasSemanales(3).build();

    public static Modulo DAW_EIE =
            new Modulo.Builder(EIE).setCiclo(DAW).build();

    public static Modulo CV004 =
            new Modulo.Builder(DAM, SEGUNDO, "CV004",
                    "Inglés Técnico II-S")
                    .setHorasSemanales(2).build();

    public static Modulo DAW_CV004 =
            new Modulo.Builder(CV004).setCiclo(DAW).build();

    public static Modulo FCT =
            new Modulo.Builder(DAM, SEGUNDO, "FCT",
                    "Formación en centro de trabajo")
                    .setHorasTotales(400).build();

    public static Modulo DAW_FCT =
            new Modulo.Builder(FCT).setCiclo(DAW).build();

    public static Modulo PDAM =
            new Modulo.Builder(DAM, SEGUNDO, "PDAM",
                    "Proyecto de " + DAM.nombre())
                    .setHorasTotales(40).build();

    public static Modulo PDAW =
            new Modulo.Builder(DAW, SEGUNDO, "PDAW",
                    "Proyecto de " + DAW.nombre())
                    .setHorasTotales(40).build();

    public static Modulo DWEC =
            new Modulo.Builder(DAW, SEGUNDO, "DWEC",
                    "Desarrollo web en entorno cliente")
                    .setHorasSemanales(7).build();

    public static Modulo DIW =
            new Modulo.Builder(DAW, SEGUNDO, "DIW",
                    "Diseño de interfaces web")
                    .setHorasSemanales(6).build();

    public static Modulo DAW_DAW =
            new Modulo.Builder(DAW, SEGUNDO, "DAW",
                    "Despliegue de aplicaciones web")
                    .setHorasSemanales(4).build();

    public static Modulo DWES =
            new Modulo.Builder(DAW, SEGUNDO, "DWES",
                    "Desarrollo web en entorno servidor")
                    .setHorasSemanales(8).build();


    public static Set<Modulo> MODULOS = Set.of(
            LMSGI, SIN, BDA, PRO, EDE, FOL, CV003,
            ADA, DIN, PMDM, PSP, SGE, EIE, CV004, FCT, PDAM,
            DAW_LMSGI, DAW_SIN, DAW_BDA, DAW_PRO, DAW_EDE, DAW_FOL, DAW_CV003,
            DWEC, DIW, DAW_DAW, DWES, DAW_EIE, DAW_CV004, DAW_FCT, PDAW);

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

    public static Map<Ciclo, Set<Modulo>> MODULOS_BY_CICLO =
            MODULOS.stream()
                    .collect(Collectors.groupingBy(
                            Modulo::ciclo,
                            Collectors.toSet()
                    ));
}
