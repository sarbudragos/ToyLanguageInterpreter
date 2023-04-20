package Model.Values;

import Model.Types.BoolType;
import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value{
    private final String value;

    public boolean equals(Object obj) {
        return obj instanceof StringValue;
    }

    public StringValue(String s) {
        this.value = s;
    }

    public String getValue() { return this.value;}



    @Override
    public String toString()
    {
        return "\"" + value + "\"";
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(value);
    }
}
