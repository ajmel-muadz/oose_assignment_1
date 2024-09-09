package edu.curtin.app;

public class HeightLimit extends StructureDecorator
{
    private int heightLimit;

    public HeightLimit(IStructure decoratedStructure)
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
        return decoratedStructure.convertToString() + "height-limit=" + heightLimit + " ";
    }
    @Override
    public boolean canBuild()
    {
        return (decoratedStructure.canBuild() && true);
    }

    // Accessor
    public int getHeightLimit()
    {
        return heightLimit;
    }

    // Mutator
    public void setHeightLimit(int pHeightLimit)
    {
        heightLimit = pHeightLimit;
    }
}
