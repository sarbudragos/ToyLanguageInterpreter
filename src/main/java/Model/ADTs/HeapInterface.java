package Model.ADTs;

import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;

public interface HeapInterface {
    int insert(Value value);

    boolean delete(int position);

    Map<Integer, Value> getContent();

    void setContent(HashMap<Integer, Value> internalMap);
}
