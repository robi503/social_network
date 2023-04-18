package socialnetwork.tools;

import java.io.Serializable;
import java.util.Objects;

public class Tuple<X extends Comparable<X>> implements Serializable {
    public final X x;
    public final X y;

    public X getX() {
        return x;
    }

    public X getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple<?> tuple)) return false;
        return (Objects.equals(x, tuple.x) && Objects.equals(y, tuple.y)) || (Objects.equals(x, tuple.y) && Objects.equals(y, tuple.x));
    }

    @Override
    public int hashCode() {
        if(x.compareTo(y) < 0)
        {
            return Objects.hash(x, y);
        }
        return Objects.hash(y, x);
    }

    public Tuple(X x, X y) {
        this.x = x;
        this.y = y;
    }
}
