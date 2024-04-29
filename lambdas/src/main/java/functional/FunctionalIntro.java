package functional;

public class FunctionalIntro {

    @FunctionalInterface
    interface IntToInt {
        int apply(int x);

        //Función de orden superior
        static IntToInt compose(IntToInt f, IntToInt g) {
            return x -> f.apply(g.apply(x));
        }
    }

    @FunctionalInterface
    interface StringToInt {
        int apply(String x);
    }

    public static void main(String[] args) {
        demo1();
        withAnonymousTypes();
        withLambdas();
    }

    private static void demo1() {
        StringToInt textLength = new StringToInt() {
            @Override
            public int apply(String x) {
                return x.length();
            }
        };

        //StringToInt textLength = text -> text.length();

        System.out.println(textLength.apply("Hello World"));
    }

    private static void withAnonymousTypes() {
        IntToInt triple = new IntToInt() {
            @Override
            public int apply(int x) {
                return x * 3;
            }
        };

        IntToInt square = new IntToInt() {
            @Override
            public int apply(int x) {
                return x * x;
            }
        };

        System.out.println(triple.apply(5));
        System.out.println(square.apply(3));

        IntToInt tripleOfSquare = IntToInt.compose(triple, square);

        System.out.println(tripleOfSquare.apply(2));

        IntToInt squareOfTriple = IntToInt.compose(square, triple);

        System.out.println(squareOfTriple.apply(2));
    }

    private static void withLambdas() {

        IntToInt triple = x -> x * 3;

        IntToInt square = x -> x * x;

        System.out.println(triple.apply(5)); //15
        System.out.println(square.apply(3)); //9

        IntToInt tripleOfSquare = IntToInt.compose(triple, square);

        System.out.println(tripleOfSquare.apply(2)); //12

        IntToInt squareOfTriple = IntToInt.compose(square, triple);

        System.out.println(squareOfTriple.apply(2)); //36
    }
}
