package edu.curtin.app;

public class RockyTerrain extends StructureDecorator
{
    public String label = "rocky";

    public RockyTerrain(IStructure decoratedStructure)
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
        return decoratedStructure.convertToString() + "terrain=rocky ";
    }
}
