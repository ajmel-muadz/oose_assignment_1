package edu.curtin.app;

public class WoodHeritage extends StructureDecorator
{
    public String label = "wood";

    public WoodHeritage(IStructure decoratedStructure)
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
        return decoratedStructure.convertToString() + "heritage=wood ";
    }
}
