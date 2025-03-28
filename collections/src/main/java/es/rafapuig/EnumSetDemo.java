package es.rafapuig;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Set;

public class EnumSetDemo {

    enum DiasSemana {LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO};


    public static void main(String[] args) {
        new EnumSetDemo().run();
    }

    void run() {
        testAllOf();
        testOf();
        testComplementOf();
        testRangeOf();
        testNoneOf();
        testCopyOf();
    }

    @Test
    void testAllOf() {
        // Crear un conjunto de valores enumerados del tipo enum DiasSemana
        // El metodo allOf es un metodo factoría que crea el objeto Set
        // conteniendo todos los valores del tipo enum especificado
        EnumSet<DiasSemana> allDaysSet = EnumSet.allOf(DiasSemana.class);

        // El iterador de un EnumSet recorre los elementos en el mismo orden
        // en que fueron declarados en el tipo enum correspondiente
        PrintUtils.print(allDaysSet);
    }

    @Test
    void testOf() {
        // Crear un conjunto de valores enumerados del tipo enum DiasSemana
        // El metodo of es un metodo factoría que crea la instancia de Set
        // Esta sobrecargado para distinto numero de argumentos valores del tipo del enum
        EnumSet<DiasSemana> dias = EnumSet.of(DiasSemana.MIERCOLES, DiasSemana.SABADO, DiasSemana.VIERNES);

        // El iterador de un EnumSet recorre los elementos en el mismo orden
        // en que fueron declarados en el tipo enum correspondiente
        // No importa el orden en que fueron añadidos al conjunto
        PrintUtils.print(dias);
    }

    @Test
    void testComplementOf() {
        // Crear un conjunto de valores enumerados del tipo enum DiasSemana
        EnumSet<DiasSemana> weekendDays = EnumSet.of(DiasSemana.DOMINGO, DiasSemana.SABADO);

        // El iterador de un EnumSet recorre los elementos en el mismo orden
        // en que fueron declarados en el tipo enum correspondiente
        // No importa el orden en que fueron añadidos al conjunto
        PrintUtils.print(weekendDays);

        // El metodo complementOf es un metodo factoría que crea un Set
        // que contiene los elementos complementarios al conjunto proporcionado
        // Es decir, es resto de valores del tipo enumerado que no estan en el Set proporcionado
        EnumSet<DiasSemana> diasLaborables = EnumSet.complementOf(weekendDays);
        PrintUtils.print(diasLaborables);
    }

    @Test
    void testRangeOf() {
        // Crear un conjunto de valores enumerados del tipo enum DiasSemana
        // El metodo range es un metodo factoría que crea con los valores comprendidos
        // Entre el valor del argumento from y el valor de argumento to (inclusivo)
        EnumSet<DiasSemana> diasLaborales = EnumSet.range(DiasSemana.LUNES,DiasSemana.VIERNES);

        PrintUtils.print(diasLaborales);

        // Si obtenemos el complemento tendremos los dias del fin de semana
        EnumSet<DiasSemana> finDeSemana = EnumSet.complementOf(diasLaborales);
        PrintUtils.print(finDeSemana);
    }

    @Test
    void testNoneOf() {
        // Crear un conjunto de valores enumerados del tipo enum DiasSemana
        // El metodo noneOf es un metodo factoría que crea un Set vacío
        // Pero se le podrán añadir posteriormente valores del tipo enumerado especificado
        EnumSet<DiasSemana> days = EnumSet.noneOf(DiasSemana.class);

        PrintUtils.print(days);

        // Añadimos el viernes
        days.add(DiasSemana.VIERNES);
        PrintUtils.print(days);

        // Añadimos el lunes y el miércoles
        days.addAll(Set.of(DiasSemana.LUNES, DiasSemana.MIERCOLES));
        PrintUtils.print(days);

        // Añadimos el jueves y el domingo
        days.addAll(EnumSet.of(DiasSemana.JUEVES, DiasSemana.MARTES));
        PrintUtils.print(days);

        // Añadimos el fin de semana
        days.addAll(EnumSet.range(DiasSemana.SABADO, DiasSemana.DOMINGO));
        PrintUtils.print(days);
    }

    @Test
    void testCopyOf() {
        // Crear un conjunto de valores enumerados del tipo enum DiasSemana
        EnumSet<DiasSemana> dias = EnumSet.range(DiasSemana.LUNES, DiasSemana.JUEVES);

        PrintUtils.print(dias);

        EnumSet<DiasSemana> copy = EnumSet.copyOf(dias);
        PrintUtils.print(copy);

        // Añadimos un dia a la copia
        copy.add(DiasSemana.DOMINGO);
        PrintUtils.print(dias);
        PrintUtils.print(copy);
    }


}
