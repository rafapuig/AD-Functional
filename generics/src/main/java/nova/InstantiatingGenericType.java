package nova;

import model.Person;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class InstantiatingGenericType {



    public static <T> void createGenericType() {
        //T t = new T(); // No se puede instanciar un tipo generico debido a la erasure
    }

    //Workarounds
    //(1) Pasar la clase del tipo gnenerico
    //Ejemplo de llamada createGenericType(String.class)
    public static <T> T createGenericType(Class<T> typeClass) {
        try {
            T t = typeClass.getDeclaredConstructor().newInstance();
            return t;
        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException e) {
            System.err.println("Could not instantiate: " + typeClass.getName());
        }
        return null;
    }

    public static <T> T createGenericType(Class<T> typeClass, Map<Class<?>, Object> constructorData) {
        try {

            System.out.println(Arrays.toString(constructorData.keySet().stream().toArray()));
            System.out.println(Arrays.toString(constructorData.values().toArray(new Object[0])));

            T t = typeClass.getDeclaredConstructor(
                            constructorData.keySet().toArray(new Class<?>[0]))
                    .newInstance(constructorData.values().toArray());

            return t;

        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException e) {
            System.err.println("Could not instantiate: " + typeClass.getName());
        }
        return null;
    }

    public static <T> T createGenericType(Class<T> typeClass,
                                          Class<?>[] parameterTypes,
                                          Object[] initArgs) {
        try {

            T t = typeClass.getDeclaredConstructor(
                            parameterTypes)
                    .newInstance(initArgs);

            return t;

        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException e) {
            System.err.println("Could not instantiate: " + typeClass.getName());
        }
        return null;
    }



    public static <T> T createGenericType(Class<T> typeClass,
                                          Map.Entry<Class<?>, Object>... l) {
        try {

            Class<?>[] parameterTypes= Arrays.stream(l).map(e -> e.getKey()).toArray(Class<?>[]::new);
            Object initArgs = Arrays.stream(l).map(e -> e.getValue()).toArray();

            T t = typeClass.getDeclaredConstructor(
                            parameterTypes)
                    .newInstance(initArgs);

            return t;

        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException e) {
            System.err.println("Could not instantiate: " + typeClass.getName());
        }
        return null;
    }


    //(2) pasar la referencia al constructor
    //Ejemplo de llamada: createGenericType(String::new)
    public static <T> T createGenericType(Supplier<T> constructor) {
        T t = constructor.get();
        return t;
    }

    public static void main(String[] args) {
        createGenericType();
        createGenericType(String.class);
        createGenericType(String::new);

        //createPersonTypeTest();
        createPersonTypeTest1();

        String hola = createGenericType(() -> new String("Hola"));
        System.out.println(hola);

        Person p = createGenericType(() -> new Person(1, "Rafa"));

        BiFunction<Integer, String, Person> constP = (id, name) -> new Person(id, name);
        Supplier<Person> constPerson = () -> constP.apply(1, ""); // new model.Person(1,"");
    }

    private static void createPersonTypeTest() {
        LinkedHashMap<Class<?>, Object> constData = new LinkedHashMap<>();
        constData.put(int.class, 1);
        constData.put(String.class, "Armando Bronca Segura");


        //System.out.println(constData);

        Person armando = createGenericType(Person.class, constData);
        System.out.println(armando.toString());
    }

    private static void createPersonTypeTest1() {

        Person armando = createGenericType(
                Person.class,
                new Class<?>[]{int.class, String.class},
                new Object[]{1, "Armando"});

        System.out.println(armando.toString());
    }

    private static void createPersonTypeTest2() {

        //Map.Entry<Class<?>,String> e = new AbstractMap.SimpleEntry<>(int.class,1);


        Person armando = createGenericType(
                Person.class,
                new Class<?>[]{int.class, String.class},
                new Object[]{1, "Armando"});

        System.out.println(armando.toString());
    }

    static <T> void instantiateParameter() {
        Optional<T> op = Optional.ofNullable(null);
        System.out.println(op.get().getClass().getName());
    }

    static <T> void instantiateParameter(Supplier<T> constructor) {
        Optional<T> op = Optional.ofNullable(constructor.get());
        System.out.println(op.get().getClass().getName());
    }

    static <T> void intantiateGenericParameterObject(Supplier<T> constructor) {
        T t = constructor.get();
        System.out.println(t.getClass().getName());
    }

    static <T> T instantiateGenericParameterObject(Class<T> type) {

        T instance = null;
        try {
            instance = type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    private static void kkkkk() {
        MyClass<String, String, String, String> sss;

        class StringThing extends Thing<String> {
        }

        //nova.Thing<String> t1 = new nova.Thing<>();
        StringThing t1 = new StringThing();
        String s = t1.createGenericInstance();

        System.out.println("clase: " + s.getClass().getName());

        InstantiatingGenericType.<String>instantiateParameter(String::new);

        //Object o = instantiateGenericParameterObject(Wrapper.class);
        //System.out.println(o.getClass().getName());

        Consumer<Object> consumer = System.out::println;
        Optional.of(Integer.valueOf(10)).ifPresent(consumer);
    }


}
