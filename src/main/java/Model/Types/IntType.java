package Model.Types;

import Model.Values.IntValue;

public class IntType implements Type{
    public boolean equals(Object second)
    {
        return second instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public IntValue defaultValue()
    {
        return new IntValue(0);
    }

    @Override
    public Type deepCopy()
    {
        return new IntType();
    }
}
