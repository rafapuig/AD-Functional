import es.rafapuig.exercises.cartas.*;
import es.rafapuig.exercises.cartas.filtering.NaipeFilter;
import model.cartas.Naipe;
import model.cartas.Palo;
import model.cartas.Valor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import tests.utils.VariableSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@DisplayName("Test Class for Naipes Collections")
public class NaipesTest {
    private static List<Naipe> mazo;

    @BeforeAll
    static void setUp() {
        mazo = Naipes.generateBaraja();
    }

    @Test
    @Tag("collection")
    @DisplayName("Get All ASES in a deck")
        //@Disabled("Ya esta requeteprobado que funciona")
    void testGetAses() {
        Set<Naipe> ases = Set.copyOf(Naipes.getAses(mazo));

        Set<Naipe> expected = Set.of( //Da igual el orden es un conjunto
                new Naipe(Palo.BASTOS, Valor.AS),
                new Naipe(Palo.COPAS, Valor.AS),
                new Naipe(Palo.ESPADAS, Valor.AS),
                new Naipe(Palo.OROS, Valor.AS));

        Assertions.assertAll("Verificando si estan todos los ases",
                //() -> Assertions.assertIterableEquals(expected, ases),
                () -> Assertions.assertEquals(expected, ases)
        );

        System.out.println(ases);
    }

    private static List<Arguments> provideNaipesForTestIsFigura() {
        return List.of(
                Arguments.of(new Naipe(Palo.OROS, Valor.REY), true),
                Arguments.of(new Naipe(Palo.BASTOS, Valor.CABALLO), true),
                Arguments.of(new Naipe(Palo.COPAS, Valor.AS), false),
                Arguments.of(new Naipe(Palo.ESPADAS, Valor.SOTA), true)
        );
    }

    @Tag("individual")
    @ParameterizedTest
    @MethodSource("provideNaipesForTestIsFigura")
    void testIsFigura(Naipe naipe, boolean expected) {
        boolean actual = Naipes.isFigura(naipe);
        Assertions.assertEquals(actual, expected);
    }

    static Stream<Arguments> arguments = Stream.of(
            Arguments.of(Naipes.BARAJA, Palo.OROS),
            Arguments.of(Naipes.BARAJA, Palo.COPAS),
            Arguments.of(List.of(
                            new Naipe(Palo.COPAS, Valor.AS),
                            new Naipe(Palo.BASTOS, Valor.AS)),
                    Palo.BASTOS
            )
    );

    @ParameterizedTest
    @VariableSource("arguments")
    void testGetNaipesFilteredByPalo(List<Naipe> mazo, Palo palo) {

        List<Naipe> result = Naipes.getNaipesFilteredByPalo(mazo, palo);

        Assertions.assertTrue(
                result.stream()
                        .allMatch(naipe -> naipe.getPalo() == palo));
    }


    //Dos filtros de test
    static NaipeFilter isCaballoFilter = naipe -> naipe.getValor() == Valor.CABALLO;
    static NaipeFilter isReyOros = naipe -> naipe.getPalo() == Palo.OROS && naipe.getValor() == Valor.REY;

    static Stream<Arguments> testGetNaipesFilterByArguments =
            Stream.of(
                    Arguments.of(isCaballoFilter, null),
                    Arguments.of(isReyOros,
                            List.of(new Naipe(Palo.OROS, Valor.REY))),
                    Arguments.of(isCaballoFilter,
                            List.of(
                                    new Naipe(Palo.COPAS, Valor.CABALLO),
                                    new Naipe(Palo.OROS, Valor.CABALLO),
                                    new Naipe(Palo.ESPADAS, Valor.CABALLO),
                                    new Naipe(Palo.BASTOS, Valor.CABALLO)
                            ))
            );

    @ParameterizedTest
    @VariableSource("testGetNaipesFilterByArguments")
    void testGetNaipesFilterBy(NaipeFilter filter, List<Naipe> expected) {

        List<Naipe> result = Naipes.getNaipesFilteredBy(mazo, filter);

        if (expected == null) {
            Assertions.assertTrue(result.stream().allMatch(isCaballoFilter::filter));
        } else {
            //Assertions.assertEquals(expected, result);
            //MatcherAssert.assertThat(result, Matchers.everyItem(Matchers.in(expected)));
            List<Naipe> differences = new ArrayList<>(result);
            expected.forEach(differences::remove);
            Assertions.assertTrue(differences.isEmpty());
        }

    }

}
