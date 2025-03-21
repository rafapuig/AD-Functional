package es.rafapuig.exercises.personas;

import formatting.ColumnInfo;
import formatting.TableFormatter;
import model.people.Empleado;

import java.text.DecimalFormat;

import static es.rafapuig.exercises.personas.PersonasTableFormatter.*;

public class EmpleadosTableFormatter extends TableFormatter<Empleado> {

    public static DecimalFormat formatter = new DecimalFormat("#,### €/mes");

    public static final ColumnInfo<Empleado> sueldoColumn = new ColumnInfo<>(
            "sueldo", 14, formatter, Empleado::getSueldo);

    public static final ColumnInfo<Empleado> contratoColumn = new ColumnInfo<>(
            "contrato", 10, null, Empleado::getHireDate);

    public static final ColumnInfo<Empleado> antiguedadColumn = new ColumnInfo<>(
            "antiguedad", 10, null, Empleado::getAntiguedad);

    public static final EmpleadosTableFormatter DEFAULT = new EmpleadosTableFormatter();

    public EmpleadosTableFormatter() {
        super(idColumn, nombreColumn, apellidosColumn, sexoColumn, nacimientoColumn, edadColumn, sueldoColumn, contratoColumn,antiguedadColumn);
    }


}
