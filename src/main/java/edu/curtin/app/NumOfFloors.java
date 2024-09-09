package edu.curtin.app;

public class NumOfFloors extends StructureDecorator
{
    private int numOfFloors;

    public NumOfFloors(IStructure decoratedStructure)
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
        return decoratedStructure.convertToString() + "num-of-floors=" + numOfFloors + " ";
    }
    @Override
    public boolean canBuild()
    {
        return (decoratedStructure.canBuild() && true);
    }

    // Accessor
    public int getNumOfFloors()
    {
        return numOfFloors;
    }

    // Mutator
    public void setNumOfFloors(int pNumOfFloors)
    {
        numOfFloors = pNumOfFloors;
    }
}
