package exercises.events.lambda;

import exercises.events.EventArgs;
import exercises.events.EventHandler;
import org.objectweb.asm.*;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class LambdaUtils {

    public static boolean isLambda(Object potentialLambda) {
        try {
            Class<?> potentialLambdaClass = potentialLambda.getClass();
            if (!potentialLambdaClass.isSynthetic()) {
                return false;
            }
            Method writeReplace = potentialLambdaClass.getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            Object writeReplaceObject = writeReplace.invoke(potentialLambda);
            return writeReplaceObject != null && SerializedLambda.class.isAssignableFrom(writeReplaceObject.getClass());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
            return false;
        }
    }

    private static SerializedLambda getSerializedLambda(Serializable lambda) throws Exception {
        final Method method = lambda.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(true);
        return (SerializedLambda) method.invoke(lambda);
    }

    private static List<Object> readBytecodeOf(Object lambdaObject) {
        if (lambdaObject instanceof Serializable serializable) {
            try {
                SerializedLambda serializedLambda = getSerializedLambda(serializable);
                //System.out.println(serializedLambda);
                ClassReader classReader = new ClassReader(serializedLambda.getImplClass());
                LambdaClassVisitor lambdaClassVisitor =
                        new LambdaClassVisitor(Opcodes.ASM9, serializedLambda.getImplMethodName());
                classReader.accept(lambdaClassVisitor,
                        ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);
                return lambdaClassVisitor.getBytecode();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return List.of(new LambdaClassVisitor.UniqueObject(Float.NaN));
        }
    }

    public static boolean equalLambdaCode(Object a, Object b) {
        if (!isLambda(a)) return false;
        if (!isLambda(b)) return false;
        List<Object> aBytecode = readBytecodeOf(a);
        List<Object> bBytecode = readBytecodeOf(b);
        //System.out.println(aBytecode);
        //System.out.println(bBytecode);
        return aBytecode.equals(bBytecode);
    }

    public static boolean equals(Object a, Object b) {
        if (!isLambda(a) || !isLambda(b)) return a.equals(b);
        return equalLambdaCode(a, b);
    }

    public static void main(String[] args) {
        EventHandler<String, EventArgs> ev1 = (s, e) -> System.out.println("Hola");
        EventHandler<Integer, EventArgs> ev2 = (s, e) -> {
            while (true) System.out.println("Hola");
        };
        System.out.println(equalLambdaCode(ev1, ev2));
    }
}
