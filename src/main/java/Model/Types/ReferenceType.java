package Model.Types;

import Model.Values.ReferenceValue;
import Model.Values.Value;

public class ReferenceType implements Type{
    Type innerType;

    public ReferenceType()
    {
    }

    public ReferenceType(Type inner)
    {
        this.innerType =inner;
    }
    public Type getInnerType()
    {
        return innerType;
    }
    public boolean equals(Object another)
    {
        if (another instanceof  ReferenceType)
            return innerType.equals(((ReferenceType) another).getInnerType());
        else
            return false;
    }
    public String toString()
    {
        return "Ref( " + innerType.toString()+" )";
    }
    public Value defaultValue() { return new ReferenceValue(0, innerType);}

    @Override
    public Type deepCopy() {
        return new ReferenceType(innerType.deepCopy());
    }
}
