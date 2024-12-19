import model.people.Empleado;
import model.people.Empleados;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorsDemo {

    public static void main(String[] args) {

        System.out.println(getEmpleadosList());
        System.out.println(getEmpleadosLinkedList());
        System.out.println(getEmpleadosUnmodifiableList());

        System.out.println(getEmpleadosSet());

        System.out.println(countEmpleados_v1());
        System.out.println(countEmpleados_v2());

        System.out.println(getEmpleadosSueldoStats());

        System.out.println(getEmpleadosByIDMap());
        System.out.println(getInicialNombreEmpleadosCantidad());
        System.out.println(getInicialNombreEmpleadosCantidadOrdered());
    }

    private static List<Empleado> getEmpleadosList() {

        List<Empleado> empleadoList = Stream
                .of(Empleados.ARMANDO, Empleados.AITOR, Empleados.BELEN)
                .collect(Collectors.toList());

        System.out.println(empleadoList.getClass().getCanonicalName()); //ArrayList
        return empleadoList;
    }

    @Test
    void testGetEmpleadosList() {
        List<Empleado> empleadoList = getEmpleadosList();
        System.out.println(empleadoList.getClass().getCanonicalName());
        System.out.println(empleadoList);
    }


    private static List<Empleado> getEmpleadosLinkedList() {

        List<Empleado> empleadoList = Stream
                .of(Empleados.ARMANDO, Empleados.AITOR, Empleados.BELEN)
                .collect(Collectors
                        .toCollection(LinkedList::new)); // Collection supplier

        System.out.println(empleadoList.getClass().getCanonicalName()); //LinkedList
        return empleadoList;
    }

    @Test
    void testGetEmpleadosLinkedList() {
        List<Empleado> empleadoList = getEmpleadosLinkedList();
        System.out.println(empleadoList.getClass().getCanonicalName());
        System.out.println(empleadoList);
    }


    private static List<Empleado> getEmpleadosUnmodifiableList() {

        List<Empleado> empleadoList = Stream
                .of(Empleados.ARMANDO, Empleados.AITOR, Empleados.BELEN)
                .collect(Collectors
                        .toUnmodifiableList()); // Collection supplier

        System.out.println(empleadoList.getClass().getCanonicalName());
        return empleadoList;
    }

    @Test
    //@DisplayName("Test Obtener Empleados en una lista no modificable")
    void testGetEmpleadosUnmodifiableList() {
        List<Empleado> empleadoList = getEmpleadosUnmodifiableList();
        System.out.println(empleadoList.getClass().getCanonicalName());
        System.out.println(empleadoList);
    }


    private static Set<Empleado> getEmpleadosSet() {

        Set<Empleado> empleadoSet = Stream
                .of(Empleados.ARMANDO, Empleados.AITOR, Empleados.BELEN)
                .collect(Collectors.toSet());

        System.out.println(empleadoSet.getClass().getCanonicalName()); // HashSet
        return empleadoSet;
    }



    private static Long countEmpleados_v1() {
        return Empleados.EMPLEADOS.stream()
                .collect(Collectors.counting());
    }

    private static Long countEmpleados_v2() {
        return Empleados.EMPLEADOS.stream()
                .collect(Collectors.reducing(0L, e -> 1L, Long::sum));
    }

    private static DoubleSummaryStatistics getEmpleadosSueldoStats() {
        return Empleados.EMPLEADOS.stream()
                .collect(Collectors
                        .summarizingDouble(Empleado::getSueldo));
    }

    private static Optional<Empleado> getEmpleadoMinSueldo_v1() {
        return Empleados.EMPLEADOS.stream()
                .collect(Collectors
                        .minBy(Comparator
                                .comparingDouble(Empleado::getSueldo)));
    }

    private static Optional<Empleado> getEmpleadoMinSueldo_v2() {
        return Empleados.EMPLEADOS.stream()
                .collect(Collectors
                        .reducing(BinaryOperator
                                .minBy(Comparator
                                        .comparingDouble(Empleado::getSueldo))));
    }

    // La versión de sobrecarga del método toMap con dos parámetros solamente la deberíamos de usar
    // cuando no esperamos que el valor de las claves no se vaya a repetir durante el proceso
    // Para detectar que ha habido duplicidad de clave toMap lanza una excepcion del tipo IllegalStateException
    private static Map<Long,Empleado> getEmpleadosByIDMap() {
        Map<Long, Empleado> map = Empleados.EMPLEADOS.stream()
                .collect(Collectors.toMap( //Solo si no hay duplicados
                        Empleado::getId,
                        empleado -> empleado
                ));

        System.out.println(map.getClass().getCanonicalName());
        return map;
    }


    /**
     * Genera un Mapa cuyas claves de las entradas se obtienen a partir de la letra inicial del nombre del
     * empleado y los valores reflejan el número de empleados cuyo nombre empieza por esa letra
     * @return
     */
    // La versión de sobrecarga del método toMap con un tercer parámetro si permite valores de clave
    // duplicados. Precisamente el tercer argumento es un BinaryOperator para combinar el valor actual
    // asociado a la clave con el nuevo valor que queremos asociar cuando se procesa un elemento del stream
    // el cual genera un valor de clave ya existente (duplicado)
    private static Map<String,Long> getInicialNombreEmpleadosCantidad() {
        return Empleados.EMPLEADOS.stream()
                .collect(Collectors.toMap( //Solo si no hay duplicados
                        empleado -> empleado.getNombre().substring(0,1), // Clave de la antrada del mapa
                        empleado -> 1L, // Valor para nuevas entradas del mapa
                        (oldCount, newCount) -> oldCount + newCount // Valor para entradas con ya clave existente
                ));

    }

    private static Map<String,Long> getInicialNombreEmpleadosCantidadOrdered() {
        return Empleados.EMPLEADOS.stream()
                .collect(Collectors.toMap( //Solo si no hay duplicados
                        empleado -> empleado.getNombre().substring(0,1),
                        empleado -> 1L,
                        (oldCount, newCount) -> oldCount + newCount,
                        TreeMap::new
                ));

    }
}
