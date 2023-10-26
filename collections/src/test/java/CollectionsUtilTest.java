import es.rafapuig.exercises.CollectionsUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;


public class CollectionsUtilTest {

    static List<String> nombres1 = List.of("Emilio", "Rafa", "Ramon", "Raul", "Ramon");
    static List<String> nombres2 = List.of("Rafa", "Ramon", "Emilio", "Raul", "Ramon");
    static List<String> nombres3 = List.of( "Ramon", "Emilio", "Raul", "Ramon");
    static List<String> nombres4 = List.of("Rafa", "Emilio", "Raul", "Ramon");
    static List<String> nombres5 = List.of("Lucas", "Ramon", "Emilio", "Raul", "Ramon");


    static Stream<Arguments> testCollectionEqualsIgnoreOrder() {
        return Stream.of(
                Arguments.of(nombres1, nombres2, true),
                Arguments.of(nombres1, nombres3, false),
                Arguments.of(nombres1, nombres4, false),
                Arguments.of(nombres1, nombres5, false)
        );
    }

    @ParameterizedTest
    @MethodSource //("collectionEqualsIgnoreOrderArguments")
    <T> void testCollectionEqualsIgnoreOrder(Collection<T> one, Collection<T> other, boolean expected) {

        Assertions.assertEquals(expected, CollectionsUtil.collectionEqualsIgnoreOrder(one, other));

    }

    @ParameterizedTest
    @MethodSource("testCollectionEqualsIgnoreOrder")
    <T> void testCollectionEqualsIgnoreOrderStreams(Collection<T> one, Collection<T> other, boolean expected) {

        Assertions.assertEquals(expected,
                CollectionsUtil.collectionEqualsIgnoreOrderStreams(one, other));
    }

    @ParameterizedTest
    @MethodSource("testCollectionEqualsIgnoreOrder")
    <T> void testCollectionEqualsIgnoreOrderStreams2(Collection<T> one, Collection<T> other, boolean expected) {

        Assertions.assertEquals(expected,
                CollectionsUtil.collectionEqualsIgnoreOrderStreams2(one, other));
    }

    @ParameterizedTest
    @MethodSource("testCollectionEqualsIgnoreOrder")
    <T> void testCollectionEqualsIgnoreOrderStreams3(Collection<T> one, Collection<T> other, boolean expected) {

        Assertions.assertEquals(expected,
                CollectionsUtil.collectionEqualsIgnoreOrderStreams2(one, other));
    }

    @Test
    void testCollectionEqualsIgnoreOrderImperative() {

        Assertions.assertTrue(
                CollectionsUtil.collectionEqualsIgnoreOrderImperative(nombres1, nombres2));

        Assertions.assertFalse(
                CollectionsUtil.collectionEqualsIgnoreOrderImperative(nombres1, nombres3));

        Assertions.assertFalse(
                CollectionsUtil.collectionEqualsIgnoreOrderImperative(nombres1, nombres4));

        Assertions.assertFalse(
                CollectionsUtil.collectionEqualsIgnoreOrderImperative(nombres1, nombres5));
    }

    @Test
    void testCollectionEqualsAssertions() {
        assertThat(nombres1, Matchers.containsInAnyOrder(nombres2.toArray()));
        assertThat(nombres1, Matchers.not(Matchers.containsInAnyOrder(nombres3.toArray())));
    }
}
