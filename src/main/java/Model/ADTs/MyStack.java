package Model.ADTs;

import java.util.*;

public class MyStack<T> implements StackInterface<T> {

    Stack<T> internalStack;

    public MyStack()
    {
        internalStack = new Stack<>();
    }


    @Override
    public T top() {
        try {
            return internalStack.peek();
        }
        catch (EmptyStackException ex)
        {
            return null;
        }
    }

    @Override
    public T pop() {
        return internalStack.pop();
    }

    @Override
    public void push(T object) {
        internalStack.push(object);
    }

    @Override
    public boolean empty() {
        return internalStack.empty();
    }

    @Override
    public List<T> toList() {
        ArrayList<T> list = new ArrayList<>(internalStack);
        Collections.reverse(list);
        return list;
    }

    @Override
    public String toString() {
        return internalStack.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        //noinspection unchecked
        MyStack<T> newStack = (MyStack<T>) super.clone();
        //noinspection unchecked
        newStack.internalStack = (Stack<T>) internalStack.clone();
        return newStack;
    }

    @Override
    public Iterator<T> iterator() {
        return internalStack.iterator();
    }
}
