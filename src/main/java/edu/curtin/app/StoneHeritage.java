package edu.curtin.app;

public class StoneHeritage extends StructureDecorator
{
    public String label = "heritage-stone";

    public StoneHeritage(IStructure decoratedStructure)
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
        return decoratedStructure.convertToString() + "heritage=stone ";
    }
    @Override
    public boolean canBuild()
    {
        return (decoratedStructure.canBuild() && true);
    }
}
