package Model.ADTs;

import java.util.List;

public interface ListInterface<T> extends Iterable<T>{
    int size();

    boolean isEmpty();

    boolean contains(Object o);

    T get(int index);

    T set(int index, T element);

    boolean add(T element);

    T remove(int index);

    List<T> getInternalList();

    @Override
    String toString();
}
