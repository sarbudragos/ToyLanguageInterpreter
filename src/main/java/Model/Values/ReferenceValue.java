package Model.Values;

import Model.Types.ReferenceType;
import Model.Types.Type;

public class ReferenceValue implements Value{
    int address;
    Type locationType;

    public boolean equals(Object obj) {
        return obj instanceof ReferenceValue;
    }

    public ReferenceValue(int address, Type locationType)
    {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress()
    {
        return address;
    }

    @Override
    public Type getType()
    {
        return new ReferenceType(locationType);
    }

    @Override
    public String toString()
    {
        return "( Reference to " + locationType.toString() + " at address: " + address + " )";
    }

    @Override
    public Value deepCopy()
    {
        return new ReferenceValue(address, locationType.deepCopy());
    }
}
