import lambda.Adder;
import lambda.Joiner;
import lambda.LambdaUtilOverloaded;
import lambda.LambdaUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

// Una expresión lambda es una función anónima,
// es un valor o dato y, por tanto, en Java debe tener un tipo
// el cual se hará corresponder con el tipo de una interfaz funcional
// (cuyo método abstracto es compatible por su firma con la de expresión lambda)
// y se obtendrá por el contexto


public class LambdaDemo {

    public static void main(String[] args) {
        anonymousVsLambdas();
        targetTyping();
        targetTypingMethodCall();
        targetTypingOverloadedMethodCall();
        lambdasBridging();
        testCheckIfStartsWith();
    }

    static void anonymousVsLambdas() {

        //clase local
        class X {

        }

        @FunctionalInterface
        interface HelloFunctional {
            String sayHello(String name); // El método único abstracto SAM
        }

        //Implementación de la interfaz mediante un tipo anónimo (más la instanciación)
        var helloAnonymous = new HelloFunctional() {
            @Override
            public String sayHello(String name) {
                return "Hola, " + name + "!(anonymous)";
            }
        };

        //Una expresión lambda es una nueva instancia cuyo tipo es el de la interfaz funcional
        HelloFunctional helloLambda = name -> "Hola, " + name + "!(lambda)";

        //Invocar el método SAM provoca que se ejecute el método SAM (o la expresión lambda en su caso)
        String result1 = helloAnonymous.sayHello("Fulanito");
        String result2 = helloLambda.sayHello("Menganito");

        System.out.println(result1);
        System.out.println(result2);

        //Expresión lambda con cuerpo de la función definido mediante un bloque
        HelloFunctional other1 =
                (final String name) -> {
                    return "Hola, " + name + "!(lambda)";
                };

        System.out.println(other1.sayHello("Zutanito"));
    }

    static void targetTyping() {
        // (x + y) -> x + y

        // TIPOS DE EXPRESIONES EN JAVA
        // Standalone expression
        // (se sabe el tipo sin necesidad del contexto en que se usa)
        // new String("Hola");
        // "Hola"
        // new ArrayList<String>();

        //Poly expression
        // El tipo varía según el contexto (lo determina el compilador)
        // new ArrayList<>();
        ArrayList<Long> idList = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();

        //El contexto en el que se usa una expresión lambda espera un tipo (target type)
        // Inferir el target type se conoce como target typing
        //Ejemplo con la expresión lambda: (x, y) -> x + y

        Adder adder = (x, y) -> x + y; //Target type: Adder (inferido por el compilador)
        //Como si x e y fueran de tipo double
        Adder adder1 = (double x, double y) -> x + y;

        Joiner joiner = (x, y) -> x + y; //Target type: Joiner

        double sum1 = adder.add(12.45, 36.75);
        double sum2 = adder.add(10, 15); //Casting de int a double

        String text = joiner.join("Hola", "lambda");

        System.out.println("sum1 = " + sum1);
        System.out.println("sum2 = " + sum2);
        System.out.println("text = " + text);
    }

    // Una expresión lambda es un valor, por tanto...
    // Se puede pasar una expresión lambda como argumento en la llamada a un método
    static void targetTypingMethodCall() {
        //La misma expresión lambda usada como argumento de llamada a método
        LambdaUtil.adderTest((x, y) -> x + y); //Llamada al método adderTest
        LambdaUtil.joinerTest((x, y) -> x + y); //Llamada al método joinerTest

        LambdaUtil.joinerTest((x, y) -> x + " " + y);
        LambdaUtil.joinerTest((x, y) -> {
                    StringBuilder sbx = new StringBuilder(x);
                    StringBuilder sby = new StringBuilder(y);
                    sby.reverse().append(",").append(sbx.reverse());
                    return sby.toString();
                }
        );
    }

    static void targetTypingOverloadedMethodCall() {

        //Falla porque la lambda se corresponde a la vez con varias versiones del método test
        //LambdaUtilOverloaded.test((x, y) -> x + y);

        //Solución 1: explicitar el tipo de los parámetros de la expresión lambda
        LambdaUtilOverloaded.test((double x, double y) -> x + y);
        LambdaUtilOverloaded.test((String x, String y) -> x + y);


        //Solución 2: Casting de la propia expresión lambda
        LambdaUtilOverloaded.test((Adder) (x, y) -> x + y);
        LambdaUtilOverloaded.test((Joiner) (x, y) -> x + y);

        //Solución 3: No usar la expresión directamente, asignar a una variable y pasar la variable
        Adder adder = (x, y) -> x + y;
        LambdaUtilOverloaded.test(adder);

        Joiner joiner = (x, y) -> x + y;
        LambdaUtilOverloaded.test(joiner);
    }


    static void lambdasBridging() {
        //Una expresión lambda es un valor, concretamente, una instancia de una interfaz funcional
        //Crear una nueva instancia requiere que el tipo esté definido en el lado izquierdo de la asignación
        Predicate<String> isNull = text -> text == null;
        Predicate<String> isNull1 = Objects::isNull; //Mediante referencia a método Objets.isNull(Object)

        //var isNull = text -> text == null; //NO COMPILA pq no se puede inferir el tipo
        //La firma del método SAM de la interfaz Predicate<String> se puede inferir:
        //boolean test(String input)
        // Una función en la que la entrada es un String y la salida es un boolean
        // (String) -> boolean

        interface LikePredicate<T> {
            boolean test(T value); //El SAM es idéntico a la de Predicate<T>
        }

        LikePredicate<String> isNull2 = value -> value == null;
        //Predicate<String> isNull3 =  isNull2; //NO COMPILA
        //Predicate<String> isNull4 = (Predicate<String>) isNull2; //COMPILA pero provocará una ClassCastException

        Predicate<String> isNull5 = isNull2::test; // Puentear mediante una referencia a método

        //Debido a esta incompatibilidad, no debemos definir nuevas interfaces funcionales
        //debemos hacer uso de las interfaces funcionales disponibles en el paquete java.util.function

        class Filters {
            static List<String> filter1(List<String> values, Predicate<String> predicate) {
                List<String> result = new ArrayList<>();
                for (String value : values) {
                    if (predicate.test(value)) {
                        result.add(value);
                    }
                }
                return result;
            }

            static List<String> filter2(List<String> values, LikePredicate<String> predicate) {
                return filter1(values, predicate::test); //Puentear
            }

            static List<String> filter3(List<String> values, LikePredicate<String> predicate) {
                return values.stream()
                        .filter(predicate::test) //Puentear
                        .toList();
            }

            //Este método devuelve una función lambda (una instancia de Predicate<Integer>)
            //(Los métodos que devuelven instancias de expresiones lambda / interfaces funcionales
            // son denominados métodos de orden superior - High Order Method)
            static Predicate<Integer> isGreaterThan(int value) {
                //Lexical scoping, captura del parámetro value por la expresión lambda
                return compareValue -> compareValue > value;
            }
        }

        var values = Arrays.asList("a", null, "c");
        //Lambdas creadas ad hoc como argumentos de llamada, no sufren la incompatibilidad
        //El compilador infiere correctamente a partir de la firma del método
        var result1 = Filters.filter1(values, value -> value == null);
        var result2 = Filters.filter2(values, value -> value == null);

        //De nuevo tendremos que puentear (mediante una referencia a método, en el método SAM de la interfaz)
        //(El método isGreaterThan devuelve un valor de tipo Predicate y lo pretendemos asignar a una variable
        //de tipo LikePredicate), la única manera es puentear mediante la referencia al método SAM
        LikePredicate<Integer> isGreaterThan10 = Filters.isGreaterThan(10)::test;

        //Probar si 9 es mayor que 20 invocando el método SAM para que se ejecute la lambda
        Filters.isGreaterThan(20).test(9);

        //Para ejecutar la expresión lambda tenemos que llamar al SAM de la interfaz funcional
        isGreaterThan10.test(19);
    }

    //El método tiene como parámetro de entrada un valor de tipo interfaz funcional
    //Se considera, por tanto, un método de orden superior
    //Cuando sea llamada, se podrá pasar como argumento una expresión lambda
    //La expresión lambda representará una función (String) -> boolean para ser compatible con el
    //Método SAM de la interfaz Predicate<String>
    static boolean checkIf(String value, Predicate<String> checkCriteria) {
        return checkCriteria.test(value);
    }

    //Closure (una expresión lambda que hace referencia a una variable dentro de su ámbito / scope)
    static Predicate<String> checkIfStartsWith(String letter) {
        return name -> name.startsWith(letter); // El método devuelve una expresión lambda de tipo Predicate
    }

    static void testCheckIfStartsWith() {

        System.out.println(
                checkIfStartsWith("R").test("Rafael"));
        System.out.println(
                checkIfStartsWith("P").test("Rafael"));

        //Función creada dentro de un método y asignada a una variable local
        Function<String, Predicate<String>> startsWithLetter =
                letter -> name -> name.startsWith(letter);

        System.out.println(
                startsWithLetter.apply("R").test("Rafael"));
        System.out.println(
                startsWithLetter.apply("P").test("Rafael"));

        System.out.println(
                checkIf("Rafael", startsWithLetter.apply("R")));
        System.out.println(
                checkIf("Rafael", startsWithLetter.apply("P")));
    }
}
