import lambda.Adder;
import lambda.Joiner;
import lambda.LambdaUtilOverloaded;
import lambda.LambdaUtil;

import java.util.ArrayList;

public class LambdaDemo {

    public static void main(String[] args) {
        //targetTyping();
        //targetTypingMethodCall();
        targetTypingOverloadedMethodCall();
    }

    static void targetTyping() {
        // (x + y) -> x + y

        //TIPOS DE EXPRESIONES EN JAVA
        //Standalone expression
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

        //Falla porque la lambda se corresponde con las versiones del metodo
        //lambda.LambdaUtilOverloadedTest.test((x, y) -> x + y);

        //Solucion 1: explicitar el tipo de los parametros
        LambdaUtilOverloaded.test((double x, double y) -> x + y);
        LambdaUtilOverloaded.test((String x, String y) -> x + y);


        //Solucion 2: Casting de la expresion
        LambdaUtilOverloaded.test((Adder) (x, y) -> x + y);
        LambdaUtilOverloaded.test((Joiner) (x, y) -> x + y);

        //Solucion 3: No usar la expresion directamente, asinar a una variable y pasar la variable
        Adder adder = (x, y) -> x + y;
        LambdaUtilOverloaded.test(adder);

        Joiner joiner = (x, y) -> x + y;
        LambdaUtilOverloaded.test(joiner);
    }


}
