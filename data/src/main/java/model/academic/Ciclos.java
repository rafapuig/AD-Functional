package model.academic;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Ciclos {
    public static Ciclo DAM =
            new Ciclo("DAM", "Desarrollo de Aplicaciones Multiplataforma");

    public static Ciclo DAW =
            new Ciclo("DAW", "Desarrollo de Aplicaciones Web");

    public static Ciclo ASIR =
            new Ciclo("ASIR", "Administración de Sistemas Informáticos");


    public static Set<Ciclo> CICLOS = Set.of(DAM, DAW, ASIR);

    public static Map<String, Ciclo> CICLOS_BY_ABREVIATURA =
            Map.of(DAM.abreviatura(), DAM,
                    DAW.abreviatura(), DAW,
                    ASIR.abreviatura(), ASIR);

    public static Map<String, Ciclo> CICLOS_MAP =
            CICLOS.stream()
                    .collect(Collectors.toMap(
                            Ciclo::abreviatura,
                            Function.identity()
                    ));
}
