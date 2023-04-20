package Model.Types;

import Model.Values.StringValue;
import Model.Values.*;

public class StringType implements Type{
    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }
    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public StringType deepCopy()
    {
        return new StringType();
    }
}
