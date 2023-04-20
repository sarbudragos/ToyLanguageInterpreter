package Model.ADTs;

import java.util.List;

public interface StackInterface<T> extends Iterable<T>, Cloneable {

    T top();

    T pop();
    void push(T object);

    boolean empty();

    List<T> toList();

    public Object clone() throws CloneNotSupportedException;

    @Override
    String toString();
}
