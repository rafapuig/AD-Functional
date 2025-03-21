package es.rafapuig.exercises.personas;

import formatting.ColumnInfo;
import formatting.TableFormatter;
import model.people.Persona;

public class PersonasTableFormatter extends TableFormatter<Persona> {

    public static final ColumnInfo<Persona> idColumn = new ColumnInfo<>(
      "id", 3, null, Persona::getId);

    public static final ColumnInfo<Persona> nombreColumn = new ColumnInfo<>(
            "nombre", 12, null, Persona::getNombre);

    public static final ColumnInfo<Persona> apellidosColumn = new ColumnInfo<>(
            "apellidos", 15, null, Persona::getApellidos);

    public static final ColumnInfo<Persona> sexoColumn = new ColumnInfo<>(
            "sexo", 7, null, persona ->
            persona.getSexo().toString().toLowerCase());

    public static final ColumnInfo<Persona> nacimientoColumn = new ColumnInfo<>(
            "nacimiento", 10, null, Persona::getNacimiento);

    public static final ColumnInfo<Persona> edadColumn = new ColumnInfo<>(
            "edad", 4, null, Persona::getEdad);


    public static final PersonasTableFormatter DEFAULT = new PersonasTableFormatter();

    public PersonasTableFormatter() {
        super(idColumn, nombreColumn, apellidosColumn, sexoColumn, nacimientoColumn, edadColumn);
    }

}
