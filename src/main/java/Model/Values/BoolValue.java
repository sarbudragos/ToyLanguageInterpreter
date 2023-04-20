package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value{
    boolean value;

    public boolean equals(Object obj) {
        return obj instanceof BoolValue;
    }

    public BoolValue(boolean value) {this.value = value;}

    public boolean getValue() { return value;}

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(value);
    }
}
