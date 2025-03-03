public class ObjectWrapper {

    private Object ref;

    public ObjectWrapper(Object ref) {
        this.ref = ref;
    }

    public Object get() {
        return ref;
    }

    public void set(Object ref) {
        this.ref = ref;
    }
}

class ObjectWrapperDemo {

    public static void main(String[] args) {

        ObjectWrapper wrapper = new ObjectWrapper("Hola mundo");
        String texto = (String) wrapper.get(); // Downcasting
        System.out.println(texto);
        wrapper.set("Otro texto");
        texto = (String) wrapper.get();
        System.out.println(texto);

        wrapper.set(15);
        String texto2 = (String) wrapper.get(); //Downcasting fallará
    }

}
