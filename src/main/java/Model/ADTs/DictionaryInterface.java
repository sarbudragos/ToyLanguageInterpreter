package Model.ADTs;

import Model.Values.Value;

import java.util.Map;
import java.util.Set;

public interface DictionaryInterface<KeyType, ValueType> extends Cloneable {
    int size();

    boolean isEmpty();

    boolean containsKey(KeyType key);

    ValueType get(KeyType key);

    ValueType put(KeyType key, ValueType value);

    ValueType remove(KeyType key);

    Set<Map.Entry<KeyType, ValueType>> entrySet();

    Map<KeyType, ValueType> getContent();

    public Object clone() throws CloneNotSupportedException;

    @Override
    String toString();
}
