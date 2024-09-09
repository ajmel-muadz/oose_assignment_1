package edu.curtin.app;

public class Wood extends StructureDecorator
{
    public String label = "wood";

    public Wood(IStructure decoratedStructure)
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
        return decoratedStructure.convertToString() + "construction-material=wood ";
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

        if (decoratedStructure.convertToString().contains("heritage"))
        {
            if (decoratedStructure.convertToString().contains("heritage=wood"))
            {
                valueToReturn = true;
            }
            else
            {
                valueToReturn = false;
            }
        }

        return (decoratedStructure.canBuild() && valueToReturn);
    }
}
