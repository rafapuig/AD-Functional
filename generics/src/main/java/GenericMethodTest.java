public class GenericMethodTest<T> {

    //En un método de instancia se puede hacer referencia
    // a los parámetros declarados en el tipo genérico (en este caso T)
    public <V> void m1(Wrapper<V> a, Wrapper<V> b, T c) {
        //Hacer algo 
    }

    //En un método estático no se puede hacer referencia
    //a los parámetros declarados en el tipo genérico
    //Únicamente podemos usar los parámetros de tipo declarados en el propio método
    public static <S> void m2(Wrapper<S> a, Wrapper<S> b /*, T c*/) {

    }

}
