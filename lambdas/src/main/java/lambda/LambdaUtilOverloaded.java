package lambda;

public class LambdaUtilOverloaded {

    public static void test(Adder adder) {
        double x = 123.5;
        double y = 345.2;
        double sum = adder.add(x, y);
        System.out.print("Usando un lambda.Adder: ");
        System.out.println(x + " + " + y + " = " + sum);
    }

    public static void test(Joiner joiner) {
        String s1 = "Hola";
        String s2 = "Mundo";
        String s3 = joiner.join(s1, s2);
        System.out.print("Usando un lambda.Joiner: ");
        System.out.println("'" + s1 + "' + '" + s2 + "' = '" + s3 + "'");
    }

}
