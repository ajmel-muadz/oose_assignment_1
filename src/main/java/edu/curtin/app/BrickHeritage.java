package edu.curtin.app;

public class BrickHeritage extends StructureDecorator
{
    public String label = "brick";

    public BrickHeritage(IStructure decoratedStructure)
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
        return decoratedStructure.convertToString() + "heritage=brick ";
    }
}
