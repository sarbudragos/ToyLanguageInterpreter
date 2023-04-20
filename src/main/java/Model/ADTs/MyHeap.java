package Model.ADTs;

import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements HeapInterface{
    HashMap<Integer, Value> internalHashMap;
    int firstFreePosition;

    public MyHeap()
    {
        internalHashMap = new HashMap<>();
        firstFreePosition = 1;
    }

    @Override
    public int insert(Value value) {
        internalHashMap.put(firstFreePosition, value);

        int address = firstFreePosition;

        while (internalHashMap.containsKey(firstFreePosition)) {
            firstFreePosition++;
        }

        return address;
    }

    @Override
    public boolean delete(int position) {
        if(!internalHashMap.containsKey(position))
            return false;

        internalHashMap.remove(position);

        if(position < firstFreePosition)
        {
            firstFreePosition = position;
        }

        return true;
    }

    @Override
    public Map<Integer, Value> getContent() {
        return internalHashMap;
    }

    @Override
    public void setContent(HashMap<Integer, Value> internalMap) {
        internalHashMap = internalMap;
    }
}
