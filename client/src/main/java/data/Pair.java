package data;

public class Pair<T1, T2> {
    private final T1 first;
    private final T2 second;

    public Pair(T1 response, T2 second) {
        this.first = response;
        this.second = second;
    }

    public boolean getFirst() {
        return (boolean) first;
    }

    public T2 getSecond() {
        return second;
    }
}
