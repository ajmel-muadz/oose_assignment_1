package edu.curtin.app;

public class FlatTerrain extends StructureDecorator
{
    public String label = "flat";

    public FlatTerrain(IStructure decoratedStructure)
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
        return decoratedStructure.convertToString() + "terrain=flat ";
    }
}
