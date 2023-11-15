package model.academic;

import java.util.List;
import java.util.Optional;

import static model.academic.Alumnos.*;
import static model.academic.Modulos.*;

public class Calificaciones {

    public static List<Calificacion> CALIFICACIONES_SAMPLE =
            List.of(
                    new Calificacion(ARMANDO, LMSGI, Optional.of(Nota.OCHO)),
                    new Calificacion(ARMANDO, SIN, Optional.of(Nota.NUEVE)),
                    new Calificacion(ARMANDO, BDA, Optional.of(Nota.OCHO)),
                    new Calificacion(ARMANDO, PRO, Optional.of(Nota.NUEVE)),
                    new Calificacion(ARMANDO, EDE, Optional.of(Nota.DIEZ)),
                    new Calificacion(ARMANDO, FOL, Optional.of(Nota.OCHO)),
                    new Calificacion(ARMANDO, CV003, Optional.of(Nota.OCHO)),

                    new Calificacion(BELEN, LMSGI, Optional.of(Nota.TRES)),
                    new Calificacion(BELEN, SIN, Optional.of(Nota.SIETE)),
                    new Calificacion(BELEN, BDA, Optional.of(Nota.CINCO)),
                    new Calificacion(BELEN, PRO, Optional.of(Nota.CINCO)),
                    new Calificacion(BELEN, EDE, Optional.of(Nota.OCHO)),
                    new Calificacion(BELEN, FOL, Optional.of(Nota.NUEVE)),
                    new Calificacion(BELEN, CV003, Optional.of(Nota.CINCO)),

                    new Calificacion(ESTHER, LMSGI, Optional.of(Nota.NE)),
                    new Calificacion(ESTHER, SIN, Optional.of(Nota.NE)),
                    new Calificacion(ESTHER, BDA, Optional.of(Nota.NE)),
                    new Calificacion(ESTHER, PRO, Optional.of(Nota.NE)),
                    new Calificacion(ESTHER, EDE, Optional.of(Nota.NE)),
                    new Calificacion(ESTHER, FOL, Optional.of(Nota.SIETE)),
                    new Calificacion(ESTHER, CV003, Optional.of(Nota.NE)),
                    new Calificacion(ESTHER, EIE, Optional.of(Nota.SIETE)),

                    new Calificacion(AMADOR, BDA, Optional.of(Nota.CINCO)),
                    new Calificacion(AMADOR, FOL, Optional.of(Nota.NUEVE)),
                    new Calificacion(AMADOR, CV003, Optional.of(Nota.CINCO))

            );

}
