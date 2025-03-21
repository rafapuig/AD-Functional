package es.rafapuig.exercises.personas;

import model.people.Empleado;
import model.people.Empleados;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static es.rafapuig.exercises.personas.EmpleadosUtil.*;

public class EmpleadosUtilTest {

    public static void main(String[] args) {
        new EmpleadosUtilTest().run();
    }

    void run() {
        testFilterEmpleadosSueldoSuperior2000();
        testFilterEmpleadosAntiguedadSuperior10();
        testFilterEmpleadosContratadosDespuesAño2000();
        testFilterEmpleadosSueldoSuperior1500();

        testGetAllEmpleadosSortedByHireDate();
        testGetAllEmpleadosSortedBySueldo();
        testGetSueldoMedioHombresMujeres();
        testGetEmpleadoPeorPagado();
        testEmpleadoMejorPagadoPorSexo();
    }

    @Test
    void testPrintEmpleados() {
        printEmpleadosTable(Empleados.EMPLEADOS);
    }

    @Test
    void testFilterEmpleadosSueldoSuperior2000() {
        System.out.println("\nEmpleados con sueldo mayor a 2000 euros:");
        printLineSeparator();

        List<Empleado> empleados =
                EmpleadosUtil.filterSueldoSuperior2000(Empleados.EMPLEADOS);

        printEmpleadosTable(empleados);
    }

    @Test
    void testFilterEmpleadosAntiguedadSuperior10() {
        System.out.println("\nEmpleados con más de 10 años de antigüedad:");
        printLineSeparator();

        List<Empleado> empleados =
                EmpleadosUtil.filterAntiguedadSuperior10(Empleados.EMPLEADOS);

        printEmpleadosTable(empleados);
    }

    @Test
    void testFilterEmpleadosContratadosDespuesAño2000() {
        System.out.println("\nEmpleados contratados despues del 2000:");
        printLineSeparator();

        List<Empleado> empleados =
                EmpleadosUtil.filterBy(
                        Empleados.EMPLEADOS,
                        new EmpleadoPosteriorAño2000());

        empleados.sort(Comparator.comparing(Empleado::getHireDate));

        for (Empleado empleado : empleados) {
            System.out.println(empleado.getNombreCompleto()
                    + ", contratado el año " + empleado.getHireDate().getYear());
        }
    }


    @Test
    void testFilterEmpleadosSueldoSuperior1500() {
        System.out.println("\nEmpleados con sueldo superior a 1500 euros");
        printLineSeparator();

        List<Empleado> empleados =
                EmpleadosUtil.filterBy(
                        Empleados.EMPLEADOS,
                        new EmpleadosUtil.EmpleadoPredicate() {
                            @Override
                            public boolean test(Empleado empleado) {
                                return empleado.getSueldo() >= 1500;
                            }
                        });

        empleados.sort(Comparator.comparing(Empleado::getSueldo).reversed());

        printEmpleadosTable(empleados);

        System.out.println("\nTotal: " + empleados.size());
    }


    // TEMA FUNCIONES LAMBDA PROG. FUNCIONAL
    @Test
    void testFilterEmpleadosSueldoSuperior1500Lambda() {
        System.out.println("\nEmpleados con sueldo superior a 1500 euros");
        printLineSeparator();

        List<Empleado> empleados =
                EmpleadosUtil.filterBy(
                        Empleados.EMPLEADOS,
                        empleado -> empleado.getSueldo() >= 1500);

        empleados.sort(Comparator.comparing(Empleado::getSueldo).reversed());

        printEmpleadosTable(empleados);
        System.out.println("\nTotal: " + empleados.size());
    }




    //----------- TESTS DE ORDENACIÓN

    @Test
    void testGetAllEmpleadosSortedBySueldo() {
        System.out.println("\nTodos las empleados ordenados por sueldo:");
        printLineSeparator();

        List<Empleado> empleados = EmpleadosUtil.getAllEmpleadosSortedBySueldo(
                Empleados.EMPLEADOS); // pasamos la lista de empleados

        printEmpleadosTable(empleados);
    }


    @Test
    void testGetAllEmpleadosSortedByHireDate() {
        System.out.println("\nTodos las empleados ordenados por fecha de contratación:");
        printLineSeparator();

        List<Empleado> empleados = getAllEmpleadosSortedByHireDate(Empleados.EMPLEADOS);

        printEmpleadosTable(empleados);
    }




    @Test
    void testGetAllEmpleadosSortedByCriteriaSueldo1() {
        System.out.println("\nTodos las empleados ordenados por sueldo:");
        printLineSeparator();

        List<Empleado> empleados = EmpleadosUtil.getAllEmpleadosSortedByCriteria(
                Empleados.EMPLEADOS, // pasamos la lista de empleados
                EmpleadosUtil.COMPARING_BY_SUELDO); // y el criterio para ordenar en un comparador

        printEmpleadosTable(empleados);
    }


    @Test
    void testGetAllEmpleadosSortedByCriteriaSueldo2() {
        System.out.println("\nTodos las empleados ordenados por sueldo:");
        printLineSeparator();

        List<Empleado> empleados = EmpleadosUtil.getAllEmpleadosSortedByCriteria(
                Empleados.EMPLEADOS,
                new EmpleadosUtil.ComparingBySueldo());

        printEmpleadosTable(empleados);
    }

    @Test
    void testGetAllEmpleadosSortedByCriteriaSueldo3() {
        System.out.println("\nTodos las empleados ordenados por sueldo:");
        printLineSeparator();

        List<Empleado> empleados = EmpleadosUtil.getAllEmpleadosSortedByCriteria(
                Empleados.EMPLEADOS,
                new Comparator<Empleado>() {
                    @Override
                    public int compare(Empleado o1, Empleado o2) {
                        return Double.compare(o1.getSueldo(), o2.getSueldo());
                    }
                });

        printEmpleadosTable(empleados);
    }

    @Test
    void testGetAllEmpleadosSortedByCriteriaSueldo4() {
        System.out.println("\nTodos las empleados ordenados por sueldo:");
        printLineSeparator();

        List<Empleado> empleados = EmpleadosUtil.getAllEmpleadosSortedByCriteria(
                Empleados.EMPLEADOS,
                (o1, o2) -> Double.compare(o1.getSueldo(), o2.getSueldo()));

        printEmpleadosTable(empleados);
    }

    @Test
    void testGetAllEmpleadosSortedByCriteriaSueldo5() {
        System.out.println("\nTodos las empleados ordenados por sueldo:");
        printLineSeparator();

        List<Empleado> empleados = EmpleadosUtil.getAllEmpleadosSortedByCriteria(
                Empleados.EMPLEADOS,
                Comparator.comparingDouble(Empleado::getSueldo));

        printEmpleadosTable(empleados);
    }











    static void testGetSueldoMedioHombresMujeres() {
        System.out.println("\nSueldo medio empledos por Sexo:");
        System.out.println("------------------------------------------------------");

        System.out.println(
                getSueldoMedioHombresMujeresImperative(Empleados.EMPLEADOS));

        System.out.println(
                getSueldoMedioHombresMujeresFunctional(Empleados.EMPLEADOS));
    }


    static void testGetEmpleadoPeorPagado() {
        System.out.println("\nEmpleado peor pagado:");
        System.out.println("------------------------------------------------------");

        System.out.println(
                getEmpleadoPeorPagado(Empleados.EMPLEADOS));

        System.out.println(
                getEmpleadoMinimoSegun(Empleados.EMPLEADOS, COMPARING_BY_SUELDO));

        System.out.println(
                getEmpleadoMinimoSegun(Empleados.EMPLEADOS, Comparator.comparing(Empleado::getSueldo)));

        System.out.println(
                getEmpleadoPeorPagadoFunctional(Empleados.EMPLEADOS));
    }

    static void testEmpleadoMejorPagadoPorSexo() {
        System.out.println("\nEmpleado mejor pagado por sexos:");
        System.out.println("------------------------------------------------------");

        System.out.println(
                getEmpleadoMejorPagadoPorSexo(Empleados.EMPLEADOS));

        System.out.println(
                getEmpleadoMejorPagadoPorSexoFunctional(Empleados.EMPLEADOS));

        System.out.println(
                getEmpleadoMejorPagadoPorSexoFunctionalMerge(Empleados.EMPLEADOS));

        System.out.println(
                getEmpleadoMejorPagadoPorSexoFunctionalCompute(Empleados.EMPLEADOS));
    }

}
