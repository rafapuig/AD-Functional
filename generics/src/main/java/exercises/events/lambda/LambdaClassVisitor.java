package exercises.events.lambda;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Modifier;
import java.util.List;

public class LambdaClassVisitor extends ClassVisitor {
    final String lambdaMethodName;
    LambdaMethodVisitor methodVisitor;

    protected LambdaClassVisitor(int api, String lambdaMethodName) {
        super(api);
        this.lambdaMethodName = lambdaMethodName;
    }

    public record UniqueObject(float number) {
    }

    public List<Object> getBytecode() {
        if (methodVisitor.canBeCompared) {
            return methodVisitor.getMethodBytecode();
        } else {
            return List.of(new UniqueObject(Float.NaN));
        }
    }

    @Override
    public MethodVisitor visitMethod(int modifiers, String name, String descriptor, String signature, String[] exceptions) {
        if (lambdaMethodName.equals(name)) {
            this.methodVisitor =
                    new LambdaMethodVisitor(Modifier.isStatic(modifiers), api,
                            super.visitMethod(modifiers, name, descriptor, signature, exceptions));
            return this.methodVisitor;
        }
        return super.visitMethod(modifiers, name, descriptor, signature, exceptions);
    }
}