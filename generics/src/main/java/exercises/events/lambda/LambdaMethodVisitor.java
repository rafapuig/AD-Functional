package exercises.events.lambda;

import org.objectweb.asm.*;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

public class LambdaMethodVisitor extends MethodVisitor {
    final IdentityHashMap<Label, Integer> labelToId = new IdentityHashMap<>();
    final List<Object> methodBytecode = new ArrayList<>();
    boolean canBeCompared = true;
    boolean isStatic;

    protected LambdaMethodVisitor(boolean isStatic, int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
        this.isStatic = isStatic;
    }

    public List<Object> getMethodBytecode() {
        return methodBytecode;
    }

    private record Instruction(int opcode) {
    }

    public void visitInsn(final int opcode) {
        super.visitInsn(opcode);
        methodBytecode.add(new Instruction(opcode));
    }

    record IntInstruction(int opcode, int operand) {
    }

    public void visitIntInsn(final int opcode, final int operand) {
        super.visitIntInsn(opcode, operand);
        methodBytecode.add(new IntInstruction(opcode, operand));
    }

    public void visitVarInsn(final int opcode, final int varIndex) {
        super.visitVarInsn(opcode, varIndex);
        if (!isStatic && opcode == Opcodes.ALOAD && varIndex == 0) {
            canBeCompared = false;
        }
        methodBytecode.add(new IntInstruction(opcode, varIndex));
    }

    // ... and so on for all visit... methods

    @Override
    public void visitCode() {
        super.visitCode();
        //Opcodes.
        //methodBytecode.add(new Instruction(89) );
    }

    record Parameter(String name, int access){}
    @Override
    public void visitParameter(String name, int access) {
        super.visitParameter(name, access);
        methodBytecode.add(new Parameter(name,access));
    }

    record TypeInstruction(int opcode, String type){}
    public void visitTypeInsn(int opcode, String type) {
        super.visitTypeInsn(opcode, type);
        methodBytecode.add(new TypeInstruction(opcode,type));
    }


    record MethodInstruction(int opcode, String owner, String name, String descriptor, boolean isInterface){}
    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        methodBytecode.add(new MethodInstruction(opcode, owner, name, descriptor, isInterface));
    }


    record FieldInstruction(int opcode, String owner, String name, String descriptor){}

    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        super.visitFieldInsn(opcode, owner, name, descriptor);
        methodBytecode.add(new FieldInstruction(opcode, owner, name, descriptor));
    }


    record LocalVariable(String name, String descriptor, String signature, Label start, Label end, int index){}
    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
       super.visitLocalVariable(name, descriptor, signature, start, end, index);
       methodBytecode.add(new LocalVariable(name, descriptor, signature, start, end, index));
    }

    record LdcInsn(Object value) {}

    public void visitLdcInsn(Object value) {
        super.visitLdcInsn(value);
        methodBytecode.add(new LdcInsn(value));
    }

    record JumpInstruction(int opcode, Label label) {}
    public void visitJumpInsn(int opcode, Label label) {
       super.visitJumpInsn(opcode, label);
       methodBytecode.add(new JumpInstruction(opcode, label));
    }
}
