package layers.model;

/**
 * This data class can handle two not defined variables and saves them in it to get access to these two variables.
 * @param <X>
 * @param <Y>
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
        return this == obj;
    }
}
