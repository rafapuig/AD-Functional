package model.education;

import java.util.List;

public class Teachers {

    public static Teacher RAFA_PUIG =
            new Teacher("Rafael", "Puig", "rafael.puig@iesabastos.org");

    public static Teacher BORIS =
            new Teacher("Boris", "Anaya", "boris.anaya@iesabastos.org");

    public static Teacher PAU =
            new Teacher("Pau", "Villanueva", "pau.villanueva@iesabastos.org");

    public static Teacher EDUARDO =
            new Teacher("Eduardo", "Gonzalez", "eduardo.gonzalez@iesabastos.org");

    public static List<Teacher> TEACHERS = List.of(RAFA_PUIG, BORIS, PAU, EDUARDO);

}
