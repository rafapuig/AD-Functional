
class Point<T extends Number> {
    T x;
    T y;

    public Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    /*public double getDistance(Point<T> point) {
         double dx =  point.x.doubleValue() - this.x.doubleValue();
         double dy =  point.y.doubleValue() - this.y.doubleValue();
         return Math.sqrt(dx*dx + dy*dy);
    }*/

    public double getDistance(Point<? extends Number> point) {
        double dx = point.x.doubleValue() - this.x.doubleValue();
        double dy = point.y.doubleValue() - this.y.doubleValue();
        return Math.sqrt(dx * dx + dy * dy);
    }
}

public class GenericsBoundsTest {

    public static void main(String[] args) {
        // El parámetro de tipo debe ser o heredar de Number
        //Point<String> stringPoint = null; // Error
        Point<Double> doublePoint = new Point<>(1.3, 4.6);
        Point<Integer> integerPoint = new Point<>(2, 4);
        Point<Number> numberPoint = new Point<>(5, 7.6);
        //numberPoint = integerPoint; // Recuerda que esto no es posible

        integerPoint.getDistance(doublePoint);
    }
}
