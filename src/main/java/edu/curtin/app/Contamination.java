package edu.curtin.app;

public class Contamination extends StructureDecorator
{
    public boolean value = true;

    public Contamination(IStructure decoratedStructure)
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
        return decoratedStructure.convertToString() + "contamination ";
    }
    @Override
    public boolean canBuild()
    {
        return (decoratedStructure.canBuild() && true);
    }
}
