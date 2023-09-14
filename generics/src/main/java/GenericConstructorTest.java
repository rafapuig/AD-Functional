public class GenericConstructorTest<T> {

    public <U extends T> GenericConstructorTest(U initialData) {
        String typeName = initialData.getClass().getSimpleName();
        System.out.println("typeName = " + typeName);
    }

}
