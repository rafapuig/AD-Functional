package es.rafapuig.exercises.model;

import java.util.HashMap;
import java.util.Map;
import static es.rafapuig.exercises.model.Asignaturas.*;

public class AsignaturasMapTest {

    public static void main(String[] args) {

        Map<String,Asignatura> asignaturaMap = new HashMap<>();

        asignaturaMap.put(pro.getCodigo(), pro);
        asignaturaMap.put(ad.getCodigo(), ad);
        asignaturaMap.put(pmdm.getCodigo(), pmdm);
        asignaturaMap.put(di.getCodigo(), di);

        System.out.println(asignaturaMap);
    }


}
