package edu.curtin.app;

public class Brick extends StructureDecorator
{
    public String label = "brick";

    public Brick(IStructure decoratedStructure)
    {
        super(decoratedStructure);
    }

    @Override
    public double calculateCost()
    {
        double cost = 0.0;

        String[] splitLine = decoratedStructure.convertToString().split(" ");
        for (int i = 0; i < splitLine.length; i++)
        {
            if (splitLine[i].contains("num-of-floors"))
            {
                String[] splitLineNumOfFloors = splitLine[i].split("=");
                int numOfFloorsValue = Integer.parseInt(splitLineNumOfFloors[1]);
                cost = (30000.00 * numOfFloorsValue);

                if (decoratedStructure.convertToString().contains("terrain=swampy"))
                {
                    cost = cost + (20000.00 * numOfFloorsValue);
                }
                else if (decoratedStructure.convertToString().contains("terrain=rocky"))
                {
                    cost = cost + 50000;
                }
            }
        }

        return (decoratedStructure.calculateCost() + cost);
    }
    @Override
    public String convertToString()
    {
        return decoratedStructure.convertToString() + "construction-material=brick ";
    }
    @Override
    public boolean canBuild()
    {
        boolean valueToReturn = true;
        if (decoratedStructure.convertToString().contains("heritage"))
        {
            if (decoratedStructure.convertToString().contains("heritage=brick"))
            {
                valueToReturn = true;
            }
            else
            {
                System.out.println("Cannot build structure. Reason: Structure does not match heritage.");
                valueToReturn = false;
            }
        }

        return (decoratedStructure.canBuild() && valueToReturn);
    }
    
}
