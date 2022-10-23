package layers.model;

/**
 * This data class can handle two not defined variables and saves them in it
 * to get access to these two variables.
 * @param <X>
 * @param <Y>
 *
 * @author Tim Schmidt (tim.schmidt@student.ibs-ol.de)
 */
public class Tuple<X, Y> {

    private final X x;
    private final Y y;

    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getFirst() {
        return this.x;
    }

    public Y getSecond() {
        return this.y;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Tuple) {
            Tuple <X, Y> tuple = (Tuple<X, Y>) obj;
            return tuple.getFirst().equals(this.getFirst()) && tuple.getSecond().equals(this.getSecond());
        }
        return false;
    }
}
