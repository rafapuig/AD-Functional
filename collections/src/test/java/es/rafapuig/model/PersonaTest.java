package es.rafapuig.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonaTest {

    @Test
    void testClone() {
        Persona original = Personas.aitorTilla;
        Persona clon = original.clone();

        System.out.println("Original: " + original);
        System.out.println("Clon:     " + clon);

        assertEquals(original, clon);
        assertEquals(original.getNombre(), clon.getNombre());
        assertEquals(original.getFechaNacimiento(), clon.getFechaNacimiento());

        // Cambio del nombre del clon
        clon.setNombre("Clon");
        System.out.println("Original: " + original);
        System.out.println("Clon:     " + clon);
        System.out.println(original.getNombre());
        System.out.println(clon.getNombre());

        assertNotEquals(original, clon);
        assertNotEquals(original.getNombre(), clon.getNombre());
        assertEquals(original.getFechaNacimiento(), clon.getFechaNacimiento());

        // Cambio de la fecha de nacimiento del Clon
        clon.setFechaNacimiento(LocalDate.of(2020, 1, 1));
        System.out.println("Original: " + original);
        System.out.println("Clon:     " + clon);
        System.out.println(original.getFechaNacimiento());
        System.out.println(clon.getFechaNacimiento());

        assertNotEquals(original, clon);
        assertNotEquals(original.getNombre(), clon.getNombre());
        assertNotEquals(original.getFechaNacimiento(), clon.getFechaNacimiento());
    }
}