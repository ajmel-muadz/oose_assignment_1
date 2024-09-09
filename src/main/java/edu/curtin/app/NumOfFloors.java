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
        boolean valueToReturn = true;
        if (decoratedStructure.convertToString().contains("height-limit"))
        {
            String[] splitLine = decoratedStructure.convertToString().split(" ");
            for (String element : splitLine)
            {
                if (element.contains("height-limit"))
                {
                    String[] splitLineHeightLimit = element.split("=");
                    int heightLimitValue = Integer.parseInt(splitLineHeightLimit[1]);

                    if (numOfFloors > heightLimitValue)
                    {
                        StructureCannotBuildResult.setResult("Cannot build structure. Number of floors is higher than height limit.");
                        //System.out.println("Cannot build structure. Number of floors is higher than height limit.");
                        return (decoratedStructure.canBuild() && false);
                    }
                    else
                    {
                        valueToReturn = true;
                    }
                }
            }
        }

        if (decoratedStructure.convertToString().contains("flood-risk"))
        {
            if (numOfFloors < 2)
            {
                StructureCannotBuildResult.setResult("Cannot build structure. Reason: There is a flood risk so there should be at least 2 floors.");
                //System.out.println("Cannot build structure. Reason: There is a flood risk so there should be at least 2 floors.");
                return (decoratedStructure.canBuild() && false);
            }
            else
            {
                valueToReturn = true;
            }
        }
        return (decoratedStructure.canBuild() && valueToReturn);
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
