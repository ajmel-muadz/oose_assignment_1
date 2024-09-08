package edu.curtin.app;

public class FloodRisk extends StructureDecorator
{
    private double floodRisk;

    public FloodRisk(IStructure decoratedStructure)
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
        return decoratedStructure.convertToString() + "flood-risk=" + floodRisk + "% ";
    }

    // Accessor
    public double getFloodRisk()
    {
        return floodRisk;
    }

    // Mutator
    public void setFloodRisk(double pFloodRisk)
    {
        floodRisk = pFloodRisk;
    }
}
