package edu.curtin.app;

import java.util.Arrays;

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
            for (int i = 0; i < splitLine.length; i++)
            {
                if (splitLine[i].contains("height-limit"))
                {
                    String[] splitLineHeightLimit = splitLine[i].split("=");
                    int heightLimitValue = Integer.parseInt(splitLineHeightLimit[1]);

                    if (numOfFloors > heightLimitValue)
                    {
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
