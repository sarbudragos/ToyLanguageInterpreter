package Model.ADTs;

import Model.ADTs.SemaphoreTableInterface;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SemaphoreTable implements SemaphoreTableInterface {
    Map<Integer, Pair<Integer, List<Integer>>> internalMap;
    int firstFreeLocation;

    public SemaphoreTable()
    {
        this.internalMap = new HashMap<>();
        firstFreeLocation = 0;
    }

    @Override
    public synchronized int put(Pair<Integer, List<Integer>> value) {
        internalMap.put(firstFreeLocation, value);

        int address = firstFreeLocation;

        while (internalMap.containsKey(firstFreeLocation)) {
            firstFreeLocation++;
        }

        return address;
    }

    @Override
    public synchronized Pair<Integer, List<Integer>> get(int key) {
        return internalMap.get(key);
    }

    @Override
    public synchronized Map<Integer, Pair<Integer, List<Integer>>> getContent() {
        return internalMap;
    }

    @Override
    public synchronized void setContent(Map<Integer, Pair<Integer, List<Integer>>> internalMap) {
        this.internalMap = internalMap;
    }
}
