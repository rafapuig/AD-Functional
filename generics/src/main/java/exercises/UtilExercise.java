package exercises;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilExercise {

    public static void main(String[] args) {
        Integer[] arr1 = {1, 2};
        Integer[] arr2 = {3, 4};
        Integer[] merged1 = merge(arr1, arr2);
        System.out.println(Arrays.toString(merged1));

        String[] arr3 = {"uno", "dos", "tres"};
        String[] arr4 = {"cuatro", "cinco", "seis"};
        String[] merged2 = merge(arr3, arr4);
        System.out.println(Arrays.toString(merged2));

        List<Number> list = new ArrayList<>();
        add(list, 10, 20, 30L, 40.5F, 50.9);
        System.out.println(list);

    }

    @SuppressWarnings("unchecked")
    public static <T> T[] merge(T[] a, T[] b) {
        int length = a.length + b.length;
        Class<T> clazz = (Class<T>) a.getClass().getComponentType();
        T[] dest = (T[]) Array.newInstance(clazz, length);

        for (int i = 0; i < a.length; i++) {
            dest[i] = a[i];
        }
        for (int i = 0; i < b.length; i++) {
            dest[a.length + i] = b[i];
        }

        //System.arraycopy(a, 0, dest, 0, a.length);
        //System.arraycopy(b, 0, dest, a.length, b.length);

        return dest;
    }

    public static /* añade los parámetros de tipo */<T,U extends T> void add(List<T> list, U... elements) {
        list.addAll(List.of(elements));
    }

}
