package es.rafapuig.stream;

import java.util.OptionalDouble;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;

public final class CollectorsEx {

    public static <T> Collector<T, ?, OptionalDouble> averagingWeighted(
            ToDoubleFunction<T> valueExtractor, ToLongFunction<T> weightExtractor) {
        class Fraction {
            double numerator = 0.0;
            long denominator = 0L;
        }

        return Collector.of(
                Fraction::new,
                (Fraction f, T elem) -> {
                    f.numerator += valueExtractor.applyAsDouble(elem) * weightExtractor.applyAsLong(elem);
                    f.denominator += weightExtractor.applyAsLong(elem);
                },
                (Fraction f1, Fraction f2) -> {
                    f1.numerator += f2.numerator;
                    f1.denominator += f2.denominator;
                    return f1;
                },
                f -> f.denominator != 0 ?
                        OptionalDouble.of(f.numerator / f.denominator) :
                        OptionalDouble.empty()
        );
    }

}
