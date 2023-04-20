package Model.Types;


import Model.Values.*;

public class BoolType implements Type
{
    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }
    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public BoolType deepCopy()
    {
        return new BoolType();
    }
}
