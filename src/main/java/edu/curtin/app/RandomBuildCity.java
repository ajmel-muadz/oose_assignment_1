package edu.curtin.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RandomBuildCity implements IBuildCity
{
    @Override
    public void setStructures(Grid grid, Scanner input)
    {
        /* Initialise the total structures, total cost and the grid to display. */
        /* ------------------------------------------------------------------------ */
        int totalStructuresBuilt = 0;
        double totalCostOfGrid = 0.0;

        List<List<String>> builtStructuresDisplay = new ArrayList<>();
        for (int r = 0; r < grid.getNumOfRows(); r++)
        {
            List<String> newRow = new ArrayList<>();
            for (int c = 0; c < grid.getNumOfColumns(); c++)
            {
                newRow.add(" ");
            }
            builtStructuresDisplay.add(newRow);
        }
        /* ------------------------------------------------------------------------ */

        for (int r = 0; r < grid.getNumOfRows(); r++)
        {
            for (int c = 0; c < grid.getNumOfColumns(); c++)
            {
                GridSquare gridChosen = grid.getGridSquares().get(r).get(c);
    
                IStructure existingStructure = gridChosen.getIStructure();
                IStructure modifiedStructure = existingStructure;
    
                // Set a random number of for the grid chosen.
                Random random = new Random();
                int randomNumOfFloors = random.nextInt(50)+1; // Generate random 1 to 50.
                modifiedStructure = new NumOfFloors(modifiedStructure);
                ((NumOfFloors)modifiedStructure).setNumOfFloors(randomNumOfFloors);
    
                // Set random foundation type.
                int randomFoundationInt = random.nextInt(2)+1;
                if (randomFoundationInt == 1)
                {
                    modifiedStructure = new Slab(modifiedStructure);
                }
                else if (randomFoundationInt == 2)
                {
                    modifiedStructure = new Stilts(modifiedStructure);
                }
    
                // Set random construction material.
                int randomConstructionMaterialInt = random.nextInt(4)+1;
                if (randomConstructionMaterialInt == 1)
                {
                    modifiedStructure = new Wood(modifiedStructure);
                }
                else if (randomConstructionMaterialInt == 2)
                {
                    modifiedStructure = new Brick(modifiedStructure);
                }
                else if (randomConstructionMaterialInt == 3)
                {
                    modifiedStructure = new Stone(modifiedStructure);
                }
                else if (randomConstructionMaterialInt == 4)
                {
                    modifiedStructure = new Concrete(modifiedStructure);
                }
    
                gridChosen.setIStructure(modifiedStructure);
    
                
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
    
                    // This is the total number of structures able to be built.
                    totalStructuresBuilt = totalStructuresBuilt + 1;
                    // This is the total cost of the grid.
                    totalCostOfGrid = totalCostOfGrid + overallCost;
                    // This is for marking the location of the structure able to be built.
                    builtStructuresDisplay.get(r).set(c, "X");
    
                    // Change grid square back to original once we captured the relevant data.
                    gridChosen.setIStructure(existingStructure);
                }
            }
        }

        System.out.println("Total structures built: " + totalStructuresBuilt);
        System.out.println("Total cost: $" + Math.round(totalCostOfGrid * 100.0) / 100.0);
        for (int r = 0; r < grid.getNumOfRows(); r++)
        {
            for (int c = 0; c < grid.getNumOfColumns(); c++)
            {
                System.out.print(builtStructuresDisplay.get(r).get(c));
            }
            System.out.println("");
        }
    }
}
