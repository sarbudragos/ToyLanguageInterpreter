package Model.ADTs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyList<T> implements ListInterface<T> {
    ArrayList<T> internalList;

    public MyList()
    {
        internalList = new ArrayList<>();
    }

    @Override
    public int size() {
        return internalList.size();
    }

    @Override
    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public T get(int index) {
        return internalList.get(index);
    }

    @Override
    public T set(int index, T element) {
        return internalList.set(index, element);
    }

    @Override
    public boolean add(T element) {
        return internalList.add(element);
    }

    @Override
    public T remove(int index) {
        return internalList.remove(index);
    }

    @Override
    public List<T> getInternalList() {
        return internalList;
    }

    @Override
    public String toString(){
        return internalList.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }
}
