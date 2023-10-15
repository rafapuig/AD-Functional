package es.rafapuig.exercises.personas;

import model.people.Empleado;
import model.people.Empleados;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import static es.rafapuig.exercises.personas.EmpleadosUtil.*;

public class EmpleadosUtilTest {

    public static void main(String[] args) {
        testGetAllEmpleadosSortedByHireDate();
        testGetAllEmpleadosSortedBySueldo();
        testFilterEmpleadosAntiguedadSuperior10();
        testFilterEmpleadosSueldoSuperior2000();
        testFilterEmpleadosContratadosDespuesAño2000();
        testFilterEmpleadosSueldoSuperior1500();
        testGetSueldoMedioHombresMujeres();
        testGetEmpleadoPeorPagado();
        testEmpleadoMejorPagadoPorSexo();
    }


    static void testGetAllEmpleadosSortedByHireDate() {
        System.out.println("\nTodos las empleados ordenados por fecha de contratación:");
        System.out.println("----------------------------------------------------------");
        List<Empleado> empleados = PersonasUtil.getAllEmpleadosSortedByHireDate();
        for (Empleado e : empleados) {
            long antiguedad = ChronoUnit.YEARS.between(
                    e.getHireDate(),
                    LocalDate.now());

            System.out.println(e.getNombreCompleto()
                    + ": contratado el " + e.getHireDate()
                    + ", " + antiguedad + " años de antigüedad, "
                    + "sueldo " + e.getSueldo() + " € / mes");
        }
    }


    static void testGetAllEmpleadosSortedBySueldo() {
        System.out.println("\nTodos las empleados ordenados por sueldo:");
        System.out.println("----------------------------------------------------------");

        List<Empleado> empleados = EmpleadosUtil.getAllEmpleadosSortedByCriteria(
                Empleados.EMPLEADOS,
                EmpleadosUtil.COMPARING_BY_SUEDO);

        for (Empleado e : empleados) {
            System.out.println(e.getNombreCompleto()
                    + ", sueldo " + e.getSueldo() + " € / mes");
        }
    }

    static void testGetAllEmpleadosSortedBySueldo2() {
        System.out.println("\nTodos las empleados ordenados por sueldo:");
        System.out.println("----------------------------------------------------------");

        List<Empleado> empleados = EmpleadosUtil.getAllEmpleadosSortedByCriteria(
                Empleados.EMPLEADOS,
                new EmpleadosUtil.ComparingBySueldo());

        for (Empleado e : empleados) {
            System.out.println(e.getNombreCompleto()
                    + ", sueldo " + e.getSueldo() + " € / mes");
        }
    }

    static void testGetAllEmpleadosSortedBySueldo3() {
        System.out.println("\nTodos las empleados ordenados por sueldo:");
        System.out.println("----------------------------------------------------------");

        List<Empleado> empleados = EmpleadosUtil.getAllEmpleadosSortedByCriteria(
                Empleados.EMPLEADOS,
                new Comparator<Empleado>() {
                    @Override
                    public int compare(Empleado o1, Empleado o2) {
                        return Double.compare(o1.getSueldo(), o2.getSueldo());
                    }
                });

        for (Empleado e : empleados) {
            System.out.println(e.getNombreCompleto()
                    + ", sueldo " + e.getSueldo() + " € / mes");
        }
    }

    static void testGetAllEmpleadosSortedBySueldo4() {
        System.out.println("\nTodos las empleados ordenados por sueldo:");
        System.out.println("----------------------------------------------------------");

        List<Empleado> empleados = EmpleadosUtil.getAllEmpleadosSortedByCriteria(
                Empleados.EMPLEADOS,
                (o1, o2) -> Double.compare(o1.getSueldo(), o2.getSueldo()));

        for (Empleado e : empleados) {
            System.out.println(e.getNombreCompleto()
                    + ", sueldo " + e.getSueldo() + " € / mes");
        }
    }

    static void testGetAllEmpleadosSortedBySueldo5() {
        System.out.println("\nTodos las empleados ordenados por sueldo:");
        System.out.println("----------------------------------------------------------");

        List<Empleado> empleados = EmpleadosUtil.getAllEmpleadosSortedByCriteria(
                Empleados.EMPLEADOS,
                Comparator.comparingDouble(Empleado::getSueldo));

        for (Empleado e : empleados) {
            System.out.println(e.getNombreCompleto()
                    + ", sueldo " + e.getSueldo() + " € / mes");
        }
    }


    static void testFilterEmpleadosAntiguedadSuperior10() {
        System.out.println("\nEmpleados con mas de 10 años de antiguedad:");
        System.out.println("----------------------------------------------------------");

        List<Empleado> empleados =
                EmpleadosUtil.filterAntiguedadSuperior10(Empleados.EMPLEADOS);

        for(Empleado empleado : empleados) {
            System.out.println(empleado.getNombreCompleto()
                    + ", contratado el " + empleado.getHireDate()
                    + ", antiguedad " + empleado.getAntiguedad() + " años.");
        }
    }

    static void testFilterEmpleadosSueldoSuperior2000() {
        System.out.println("\nEmpleados con sueldo mayor a 2000 euros:");
        System.out.println("----------------------------------------------------------");

        List<Empleado> empleados =
                EmpleadosUtil.filterSueldoSuperior2000(Empleados.EMPLEADOS);

        for(Empleado empleado : empleados) {
            System.out.println(empleado.getNombreCompleto()
                    + ", sueldo "
                    + String.format("%.0f",empleado.getSueldo()) + " €/mes");
        }
    }

    static void testFilterEmpleadosContratadosDespuesAño2000() {
        System.out.println("\nEmpleados contratados despues del 2000:");
        System.out.println("----------------------------------------------------------");

        List<Empleado> empleados =
                EmpleadosUtil.filterByCriteria(
                        Empleados.EMPLEADOS,
                        new EmpleadosUtil.EmpleadoAnteriorAño2000());

        empleados.sort(Comparator.comparing(Empleado::getHireDate));

        for(Empleado empleado : empleados) {
            System.out.println(empleado.getNombreCompleto()
                    + ", contratado el año " + empleado.getHireDate().getYear());
        }
    }

    static void testFilterEmpleadosSueldoSuperior1500() {
        System.out.println("\nEmpleados con sueldo superior a 1500 euros");
        System.out.println("----------------------------------------------------------");

        List<Empleado> empleados =
                EmpleadosUtil.filterByCriteria(
                        Empleados.EMPLEADOS,
                        new EmpleadosUtil.EmpleadoPredicate() {
                            @Override
                            public boolean test(Empleado empleado) {
                                return empleado.getSueldo() >= 1500;
                            }
                        });

        empleados.sort(Comparator.comparing(Empleado::getSueldo).reversed());

        for(Empleado empleado : empleados) {
            System.out.println(empleado.getNombreCompleto()
                    + ", sueldo "
                    + String.format("%.0f",empleado.getSueldo()) + " €/mes");
        }
        System.out.println("\nTotal: " + empleados.size());
    }

    static void testFilterEmpleadosSueldoSuperior1500Lambda() {
        System.out.println("\nEmpleados con sueldo superior a 1500 euros");
        System.out.println("----------------------------------------------------------");

        List<Empleado> empleados =
                EmpleadosUtil.filterByCriteria(
                        Empleados.EMPLEADOS,
                        empleado -> empleado.getSueldo() >= 1500);

        empleados.sort(Comparator.comparing(Empleado::getSueldo).reversed());

        for(Empleado empleado : empleados) {
            System.out.println(empleado.getNombreCompleto()
                    + ", sueldo "
                    + String.format("%.0f",empleado.getSueldo()) + " €/mes");
        }
        System.out.println("\nTotal: " + empleados.size());
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
