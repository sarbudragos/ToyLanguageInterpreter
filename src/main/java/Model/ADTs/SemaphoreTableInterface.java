package Model.ADTs;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface SemaphoreTableInterface {
    int put(Pair<Integer, List<Integer>> value);

    Pair<Integer, List<Integer>> get(int key);

    Map<Integer, Pair<Integer, List<Integer>>> getContent();

    void setContent(Map<Integer, Pair<Integer, List<Integer>>> internalMap);
}
