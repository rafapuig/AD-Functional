import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.LongPredicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

public class StreamsNumerics {

    /**
     * Devuelve un predicado para comprobar que el número es divisible por un factor
     * @param number número para el que se quiere probar si el factor lo divide
     * @return Un predicado que aplicado a un Long (el factor) comprueba si ese factor es divisor del número
     */
    static LongPredicate isDivisor(long number) {
        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        return factor -> number % factor == 0;
    }

    /**
     * Devuelve true si el numero proporcionado es primo o false si no
     * Un numero se considera primo si no tiene divisores
     * sin contar el 1 y el propio número
     * @param number numero del que se quiere saber si es primo
     * @return un boolean que indica si es primo o no el número
     */
    static boolean isPrime(long number) {
        return LongStream.rangeClosed(2, (long) Math.sqrt(number))
                .noneMatch(isDivisor(number));
    }

    static boolean isPrimeOptimized(long number) {

        //System.out.println("Checking if " + number + " is prime...");

        if (number <= maxCachedPrime && primes.contains(number)) {
            //System.out.println("Founded " + number + " in cache");
            return true;
        }

        //System.out.println("These are the actual primes:");
        //System.out.println(primes);

        boolean hasPrimeDivisor = primes.stream()
                .skip(1)
                .mapToLong(Long::longValue)
                .takeWhile(factor -> factor <= Math.sqrt(number))
                .anyMatch(isDivisor(number));

        if (hasPrimeDivisor) {
            //System.out.println("Found a prime cached divisor for " + number);
            return false;
        }

        long seed = primes.stream()
                .dropWhile(n -> n <= 2)
                .mapToLong(Long::longValue)
                .max()
                .orElse(1L) + 1;


        //System.out.println("Starting checking factors with: " + seed);

        long last = (long) Math.sqrt(number);

        return LongStream
                .iterate(seed,
                        factor -> factor <= last,
                        factor -> factor + 1)
                //.parallel()
                //.peek(System.out::println)
                .noneMatch(isDivisor(number));
    }

    static Set<Long> primes; // = new LinkedHashSet<>();
    static long maxCachedPrime;


    /**
     *
     */
    //@BeforeAll
    static Set<Long> generatePrimes() {
        System.out.println("Generating primes set...");

        primes = LongStream.range(1, 10_000_000L)
                //.peek(n -> System.out.println("Testing prime: " + n))
                .filter(StreamsNumerics::isPrime)
                //.peek(n -> System.out.println("Adding prime: " + n))
                //.forEach(n -> primes.add(n));
                .boxed()
                .collect(Collectors.toCollection(LinkedHashSet::new));


       maxCachedPrime = primes.stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L);

        System.out.println("Primes set generated.");

        //System.out.println(primes);

        return primes;
    }

    @Test
    void testGenerate() {
        generatePrimes();
        System.out.println(primes);
    }

    @ParameterizedTest
    @CsvSource(value = {"31,true", "17,true", "25,false"})
    void testPrimo(long number, boolean expected) {
        if(expected) {
            assertTrue(isPrime(number), "El numero " + number + " es primo");
        } else {
            assertFalse(isPrime(number), "El numero " + number + " NO es primo");
        }
    }

    @Test
    void testPrimo() {
        //System.out.println(isPrime(34471));
        System.out.println(isPrime(16_038_329L));

        System.out.println(552857238959L / 34471);
        System.out.println(isPrime(552_857_238_959L / 34_471));
        System.out.println(isPrime(552857238959L));

        assertTrue(isPrime(31), "El numero 31 es primo");
        assertTrue(isPrime(17), "El numero 17 es primo");
        assertFalse(isPrime(25), "El 25 no es primo");
    }

    @ParameterizedTest
    @ValueSource(longs = {
            16_038_329L,
            3_657_500_101L,
            53_982_894_593_057L,
            222_334_565_193_649L})
    void testPrimeTime(long number) {
        //long initial = System.nanoTime();
        System.out.println("Starting....");
        Instant before = Instant.now();
        boolean isPrimeNumber = isPrime(number);
        Instant after = Instant.now();
        System.out.println("Is " +
                NumberFormat.getNumberInstance().format(number) +
                " prime? " + isPrimeNumber);


        //long finalTime = System.nanoTime();
        //long delta = finalTime - initial;

        Duration duration = Duration.between(before, after);
        System.out.println(duration.getSeconds() + " s : " +
                NumberFormat.getNumberInstance().format(duration.getNano()) +
                " ns");
    }

}
