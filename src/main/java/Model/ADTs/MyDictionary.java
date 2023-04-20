package Model.ADTs;

import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<KeyType, ValueType> implements DictionaryInterface<KeyType, ValueType> {

    HashMap<KeyType,ValueType> internalMap;

    public MyDictionary()
    {
        internalMap = new HashMap<>();
    }

    @Override
    public int size() {
        return internalMap.size();
    }

    @Override
    public boolean isEmpty() {
        return internalMap.isEmpty();
    }

    @Override
    public boolean containsKey(KeyType key) {
        return internalMap.containsKey(key);
    }

    @Override
    public ValueType get(KeyType key) {
        return internalMap.get(key);
    }

    @Override
    public ValueType put(KeyType key, ValueType value) {
        return internalMap.put(key, value);
    }

    @Override
    public ValueType remove(KeyType key) {
        return internalMap.remove(key);
    }

    @Override
    public Set<Map.Entry<KeyType, ValueType>> entrySet() {
        return internalMap.entrySet();
    }

    @Override
    public Map<KeyType, ValueType> getContent() {
        return (Map<KeyType, ValueType>) internalMap;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        //noinspection unchecked
        MyDictionary<KeyType, ValueType> newDictionary = (MyDictionary<KeyType, ValueType>) super.clone();
        //noinspection unchecked
        newDictionary.internalMap = (HashMap<KeyType, ValueType>) internalMap.clone();
        return newDictionary;
    }

    @Override
    public String toString(){
        return internalMap.toString();
    }
}
