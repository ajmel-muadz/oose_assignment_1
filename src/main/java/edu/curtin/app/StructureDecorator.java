package edu.curtin.app;

public abstract class StructureDecorator implements IStructure
{
    protected IStructure decoratedStructure;
    public StructureDecorator(IStructure decoratedStructure)
    {
        this.decoratedStructure = decoratedStructure;
    }

    @Override
    public double calculateCost()
    {
        return decoratedStructure.calculateCost();
    }
    @Override
    public String convertToString()
    {
        return decoratedStructure.convertToString();
    }
    @Override
    public boolean canBuild()
    {
        return decoratedStructure.canBuild();
    }
}
