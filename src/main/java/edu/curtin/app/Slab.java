package edu.curtin.app;

public class Slab extends StructureDecorator
{
    public String label = "slab";

    public Slab(IStructure decoratedStructure)
    {
        super(decoratedStructure);
    }

    @Override
    public double calculateCost()
    {
        return decoratedStructure.calculateCost() + (0.0);
    }
    @Override
    public String convertToString()
    {
        return decoratedStructure.convertToString() + "foundation-type=slab ";
    }
    @Override
    public boolean canBuild()
    {
        boolean valueToReturn = true;
        if (decoratedStructure.convertToString().contains("terrain=swampy"))
        {
            valueToReturn = false;
        }
        else
        {
            valueToReturn = true;
        }

        return (decoratedStructure.canBuild() && valueToReturn);
    }
}
