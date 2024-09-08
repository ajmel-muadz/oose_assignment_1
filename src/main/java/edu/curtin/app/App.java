package edu.curtin.app;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Logger;

public class App
{
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws IOException
    {
        // Declare the grid first.
        Grid grid;

        /* This block of code is for reading the file and populating the grid. */
        /* ----------------------------------------------------------------------------------- */
        int rowsAvailable;
        int columnsAvailable;

        if (args.length == 1)
        {
            String filename = args[0];

            try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
            {
                // In this block of code we read the number of rows and columns in first line.
                String dimensionsLine;
                dimensionsLine = reader.readLine();
                String[] splitDimensionsLine = dimensionsLine.split(",");
                rowsAvailable = Integer.parseInt(splitDimensionsLine[0]);
                columnsAvailable = Integer.parseInt(splitDimensionsLine[1]);

                // Once we read rows and columns, we initialise a grid with the correct size.
                grid = new Grid(rowsAvailable, columnsAvailable);

                // The initialised grid will then have its data populated.
                grid = populateGridWithData(grid, reader, rowsAvailable, columnsAvailable);

                System.out.println(grid.getGridSquares().get(2).get(2).getIStructure().convertToString());
            }
        }
        /* ----------------------------------------------------------------------------------- */
    }


    /* Method to populate grid with data read from the file. */
    /* ----------------------------------------------------------------------------------- */
    private static Grid populateGridWithData(Grid pGrid, BufferedReader reader,
    int pRowsAvailable, int pColumnsAvailable) throws IOException
    {
        for (int r = 0; r < pRowsAvailable; r++)
        {
            for (int c = 0; c < pColumnsAvailable; c++)
            {
                GridSquare gridToModify = pGrid.getGridSquares().get(r).get(c);
                IStructure structure = new BaseStructure();
                String lineRead = reader.readLine();
                String[] splitLineRead = lineRead.split(",");

                for (int i = 0; i < splitLineRead.length; i++)
                {
                    // If first word, it is always terrain. Else, it is a zoning rule.
                    if (i == 0)
                    {
                        if (splitLineRead[0].equals("flat"))
                        {
                            // DO SOMETHING FOR FLAT
                            structure = new FlatTerrain(structure);
                        }
                        else if (splitLineRead[0].equals("swampy"))
                        {
                            // DO SOMETHING FOR SWAMPY
                            structure = new SwampyTerrain(structure);
                        }
                        else if (splitLineRead[0].equals("rocky"))
                        {
                            // DO SOMETHING FOR ROCKY
                            structure = new RockyTerrain(structure);
                        }
                    }
                    else
                    {
                        String[] splitZoningLineRead = splitLineRead[i].split("=");

                        for (int j = 0; j < splitZoningLineRead.length; j++)
                        {
                            if (j == 0)
                            {
                                if (splitZoningLineRead[0].equals("flood-risk"))
                                {
                                    // First assign structure flood risk decorator.
                                    structure = new FloodRisk(structure);

                                    // Typecast into FloodRisk to allow setting value.
                                    ((FloodRisk)structure)
                                    .setFloodRisk(Double.parseDouble(splitZoningLineRead[1]));

                                    // Typecast back into IStructure.
                                    //structure = ((IStructure)structure);
                                }
                                else if (splitZoningLineRead[0].equals("height-limit"))
                                {
                                    // First assign structure height limit decorator.
                                    structure = new HeightLimit(structure);

                                    // Typecast into HeightLimmit to allow setting value.
                                    ((HeightLimit)structure)
                                    .setHeightLimit(Integer.parseInt(splitZoningLineRead[1]));

                                    // Typecast back into IStructure.
                                    //structure = ((IStructure)structure);
                                }
                                else if (splitZoningLineRead[0].equals("heritage"))
                                {
                                    if (splitZoningLineRead[1].equals("wood"))
                                    {
                                        structure = new WoodHeritage(structure);
                                    }
                                    else if (splitZoningLineRead[1].equals("stone"))
                                    {
                                        structure = new StoneHeritage(structure);
                                    }
                                    else if (splitZoningLineRead[1].equals("brick"))
                                    {
                                        structure = new BrickHeritage(structure);
                                    }
                                }
                                else if (splitZoningLineRead[0].equals("contamination"))
                                {
                                    structure = new Contamination(structure);
                                }
                            }
                        }
                    }
                }

                gridToModify.setIStructure(structure);
            }
        }
        return pGrid;
    }
    /* ----------------------------------------------------------------------------------- */
}
