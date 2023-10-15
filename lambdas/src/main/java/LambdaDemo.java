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

//Una expresion lambda es una funcion anonima, cuyo tipo se obtenda por el contexto
// dicho se hará corresponder con una interfaz funcional
// (cuyo metodo abstracto es compatible por su firma con la de expresion lambda

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

        interface HelloFunctional {
            String sayHello(String name); // El metodo unico abstracto SAM
        }

        //Implementacion de la interfaz mediante un tipo anonimo (mas intanciacion)
        var helloAnonymous = new HelloFunctional() {
            @Override
            public String sayHello(String name) {
                return "Hola, " + name + "!(anonymous)";
            }
        };

        //Una expresion lambda es una nueva instancia cuyo tipo es el de la interfaz funcional
        HelloFunctional helloLambda = name -> "Hola, " + name + "!(lambda)";

        //Invocar el metodo SAM produce que se ejecute el metodo SAM o la expresion lambda en su caso
        String result1 = helloAnonymous.sayHello("Fulanito");
        String result2 = helloLambda.sayHello("Menganito");

        System.out.println(result1);
        System.out.println(result2);

        //Expresion lambda con cuerpo definido mediante un bloque
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
        //El tipo varía según el contexto (lo determina el compilador)
        ArrayList<Long> idList = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();

        //El contexto en el que se usa una expresion lambda espera un tipo (target type)
        // Inferir el target type se conoce como target typing

        Adder adder = (x, y) -> x + y; //Target type: lambda.Adder (inferido por el compilador)
        //Como si fuera
        Adder adder1 = (double x, double y) -> x + y;

        Joiner joiner = (x, y) -> x + y; //Target type: lambda.Joiner

        double sum1 = adder.add(12.45, 36.75);
        double sum2 = adder.add(10, 15); //Casting de int a double

        String text = joiner.join("Hola", "lambda");

        System.out.println("sum1 = " + sum1);
        System.out.println("sum2 = " + sum2);
        System.out.println("text = " + text);
    }

    // Se puede pasar una expresion lambda como argumento en la llamada a un metodo
    static void targetTypingMethodCall() {
        //Misma expresion lambda como argumento de llamada a metodo
        LambdaUtil.adderTest((x, y) -> x + y);
        LambdaUtil.joinerTest((x, y) -> x + y);

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

        //Falla porque la lambda se corresponde con varias versiones del metodo
        //lambda.LambdaUtilOverloadedTest.test((x, y) -> x + y);

        //Solucion 1: explicitar el tipo de los parametros
        LambdaUtilOverloaded.test((double x, double y) -> x + y);
        LambdaUtilOverloaded.test((String x, String y) -> x + y);


        //Solucion 2: Casting de la expresion
        LambdaUtilOverloaded.test((Adder) (x, y) -> x + y);
        LambdaUtilOverloaded.test((Joiner) (x, y) -> x + y);

        //Solucion 3: No usar la expresion directamente, asignar a una variable y pasar la variable
        Adder adder = (x, y) -> x + y;
        LambdaUtilOverloaded.test(adder);

        Joiner joiner = (x, y) -> x + y;
        LambdaUtilOverloaded.test(joiner);
    }


    static void lambdasBridging() {
        //Una expresion lambda es una instancia de una interfaz funcional
        //Crear una nueva instancia requiere que el tipo este definido en el lado izquierdo
        Predicate<String> isNull = text -> text == null;
        Predicate<String> isNull1 = Objects::isNull; //Mediante referencia a metodo Objets.isNull(Object)

        //var isNull = text -> text == null; //NO COMPILA pq no se puede inferir el tipo
        //La firma del metodo SAM de la interfaz Predicate<String> se puede inferir:
        //boolean test(String input)

        interface LikePredicate<T> {
            boolean test(T value); //El SAM es identico a la de Predicate<T>
        }

        LikePredicate<String> isNull2 = value -> value == null;
        //Predicate<String> isNull3 =  isNull2; //NO COMPILA
        //Predicate<String> isNull4 = (Predicate<String>) isNull2; //COMPILA pero provocara una ClassCastException

        Predicate<String> isNull5 = isNull2::test; // Puentear mediante una referencia a metodo

        //Debido a esta incompatibilidad, no debemos definir nuevos interfaces funcionales
        //sino hacer uso de las interfaces funcionales disponibles en el paquete java.util.function

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
                //return filter1(values, predicate::test); //Puentear
                return values.stream()
                        .filter(predicate::test)
                        .toList();
            }

            //Este metodo devuelve una funcion lambda  (una instancia de Predicate<Integer>)
            static Predicate<Integer> isGreaterThan(int value) {
                //Lexical scoping, captura del parametro value por la expresion lambda
                return compareValue -> compareValue > value;
            }
        }

        var values = Arrays.asList("a", null, "c");
        //Lambdas creadas ad hoc como argumentos de llamada no sufren la incompatibilidad
        //El compilador infiere correctamente a partir de la firma del metodo
        var result1 = Filters.filter1(values, value -> value == null);
        var result2 = Filters.filter2(values, value -> value == null);

        //De nuevo tendremos que puentear (mediante una referencia a metodo, en el metodo SAM de la interfaz)
        LikePredicate<Integer> isGreaterThan10 = Filters.isGreaterThan(10)::test;

        //Probar si 9 es mayor que 20 llamando a la lambda
        Filters.isGreaterThan(20).test(9);

        //Para ejecutar la expresion lambda tenemos que llamar al SAM de la interfaz funcional
        isGreaterThan10.test(19);
    }

    static boolean checkIf(String value, Predicate<String> checkCriteria) {
        return checkCriteria.test(value);
    }

    //Closure (una expresion lambda que accede a una variable dentro de su ambito
    static Predicate<String> checkIfStartsWith(String letter) {
        return name -> name.startsWith(letter);
    }

    static void testCheckIfStartsWith() {

        System.out.println(
                checkIfStartsWith("R").test("Rafael"));
        System.out.println(
                checkIfStartsWith("P").test("Rafael"));

        //Funcion creada dentro de una funcion
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
