package Model.Values;

import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements Value{
    private final int value;

    public boolean equals(Object obj) {
        return obj instanceof IntValue;
    }

    public IntValue(int v) { this.value = v;}

    public int getValue() { return this.value;}

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(value);
    }
}
