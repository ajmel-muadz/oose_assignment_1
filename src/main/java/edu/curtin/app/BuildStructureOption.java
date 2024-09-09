package edu.curtin.app;

public class BuildStructureOption 
{
    public static void setStructure(GridSquare gridChosen, String foundationTypeChosen,
    int numOfFloorsChosen, String constructionMaterialChosen)
    {
        /* We are setting a new structure based on user's inputs. */
        /* ---------------------------------------------------------------------------------- */
        IStructure existingStructure = gridChosen.getIStructure();
        IStructure modifiedStructure = existingStructure;

        // Set number of floors user enters into relevant grid.
        modifiedStructure = new NumOfFloors(modifiedStructure);
        ((NumOfFloors)modifiedStructure).setNumOfFloors(numOfFloorsChosen);

        // Set foundation type user enters into relevant grid.
        if (foundationTypeChosen.equals("slab"))
        {
            modifiedStructure = new Slab(modifiedStructure);
        }
        else if (foundationTypeChosen.equals("stilts"))
        {
            modifiedStructure = new Stilts(modifiedStructure);
        }

        // Set construction material user enters into relevant grid.
        if (constructionMaterialChosen.equals("wood"))
        {
            modifiedStructure = new Wood(modifiedStructure);
        }
        else if (constructionMaterialChosen.equals("brick"))
        {
            modifiedStructure = new Brick(modifiedStructure);
        }
        else if (constructionMaterialChosen.equals("stone"))
        {
            modifiedStructure = new Stone(modifiedStructure);
        }
        else if (constructionMaterialChosen.equals("concrete"))
        {
            modifiedStructure = new Concrete(modifiedStructure);
        }

        gridChosen.setIStructure(modifiedStructure);
        /* ---------------------------------------------------------------------------------- */

        System.out.println("\n" + gridChosen.getIStructure().convertToString());
        System.out.println("");
        boolean gridCanBeBuilt = gridChosen.getIStructure().canBuild();

        // We only calculate the cost if the grid can be built.
        if (gridCanBeBuilt == true)
        {
            // Cost so far without considering contamination or flood risk zone.
            double overallCost = gridChosen.getIStructure().calculateCost();

            if (gridChosen.getIStructure().convertToString().contains("contamination"))
            {
                overallCost = overallCost * 1.50;
            }
            if (gridChosen.getIStructure().convertToString().contains("flood-risk"))
            {
                String splitLine[] = gridChosen.getIStructure().convertToString().split(" ");
                for (String element : splitLine)
                {
                    if (element.contains("flood-risk"))
                    {
                        String[] splitLineFloodRisk = element.split("=");
                        double floodRiskValue = Double.parseDouble(splitLineFloodRisk[1]);
                        overallCost = overallCost * (1.00 + (floodRiskValue / 50.00));
                    }
                }
            }

            System.out.println("Cost: $" + Math.round(overallCost * 100.0) / 100.0);
        }
        else
        {
            // We print the reasons we cannot build the structure.
            System.out.println(StructureCannotBuildResult.getResult());

            // We clear the results so older results do not display.
            StructureCannotBuildResult.clearResult();
        }

        // At the end of the 'Build structure' process we reset the grid structure.
        // This is because 'Build structure' is more of a plan, doesn't actually build. 
        gridChosen.setIStructure(existingStructure);
    }
}
