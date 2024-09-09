package edu.curtin.app;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Logger;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App
{
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws IOException
    {
        int buildCityApproach = 2;

        // Declare the grid first.
        Grid grid = new Grid();

        /* This block of code is for reading the file and populating the grid. */
        /* ----------------------------------------------------------------------------------- */

        if (args.length == 1)
        {
            String filename = args[0];

            try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
            {
                // In this block of code we read the number of rows and columns in first line.
                String dimensionsLine;
                dimensionsLine = reader.readLine();
                String[] splitDimensionsLine = dimensionsLine.split(",");
                int rowsAvailable = Integer.parseInt(splitDimensionsLine[0]);
                int columnsAvailable = Integer.parseInt(splitDimensionsLine[1]);

                // Once we read rows and columns, we initialise a grid with the correct size.
                grid = new Grid(rowsAvailable, columnsAvailable);

                // The initialised grid will then have its data populated.
                grid = populateGridWithData(grid, reader, rowsAvailable, columnsAvailable);

                logger.info(() -> "Rows available: " + rowsAvailable);
                logger.info(() -> "Columns available: " + columnsAvailable);
            }
            catch (FileNotFoundException e)
            {
                System.out.println("Error while reading: " + e.getMessage());
            }
        }
        else
        {
            System.out.println("You must enter only one filename for the argument!");
        }
        /* ----------------------------------------------------------------------------------- */


        /* This section is for showing the menu. */
        /* ----------------------------------------------------------------------------------- */
        try (Scanner input = new Scanner(System.in))
        {
            while (true)
            {
                printMenuOptions();
                System.out.print("Please enter your Menu choice (by typing one of the numbers): ");
                String optionChoice = input.nextLine();

                if (optionChoice.equals("1"))
                {
                    // Build Structure
                    int rowChosen;
                    int columnChosen;
                    GridSquare gridChosen;
                    int numOfFloorsChosen;
                    String foundationTypeChosen;
                    String constructionMaterialChosen;

                    /* This code block is responsible for getting user input for build structure. */
                    /* ---------------------------------------------------------------------------------- */
                    // We get the row and columns from the user, which pinpoints us to a grid.
                    rowChosen = UserInput.getRowInput(grid, input);
                    columnChosen = UserInput.getColumnInput(grid, input);
                    gridChosen = grid.getGridSquares().get(rowChosen).get(columnChosen);

                    // We then ask for the relevant things in the structure to put in the grid.
                    numOfFloorsChosen = UserInput.getNumOfFloorsInput(grid, input);
                    foundationTypeChosen = UserInput.getFoundationTypeInput(input);
                    constructionMaterialChosen = UserInput.getConstructionMaterialInput(input);
                    /* ---------------------------------------------------------------------------------- */

                    // We build the structure based on user's inputs.
                    BuildStructureOption.setStructure(gridChosen, foundationTypeChosen,
                    numOfFloorsChosen, constructionMaterialChosen);

                }
                else if (optionChoice.equals("2"))
                {
                    CityBuilder cityBuilder = new CityBuilder();

                    // Using strategy pattern to build city polymorphically.
                    cityBuilder.initStrategies();
                    cityBuilder.setStrategy(buildCityApproach, grid, input);
                }
                else if (optionChoice.equals("3"))
                {
                    // Change configuration
                    while (true)
                    {
                        printBuildCityOptions();
                        System.out.print("Please enter your Configuration choice (by typing one of the numbers): ");
                        String buildCityOptionChoice = input.nextLine();

                        if (buildCityOptionChoice.equals("1"))
                        {
                            buildCityApproach = 1;
                            break;
                        }
                        else if (buildCityOptionChoice.equals("2"))
                        {
                            buildCityApproach = 2;
                            break;
                        }
                        else if (buildCityOptionChoice.equals("3"))
                        {
                            buildCityApproach = 3;
                            break;
                        }
                    }
                    System.out.println("You chose option " + buildCityApproach);
                }
                else if (optionChoice.equals("4"))
                {
                    // Quit
                    break;
                }
                else
                {
                    System.out.println("You must choose one of the numbers for a valid input!\n");
                }
            }
        }
        catch (InputMismatchException e)
        {
            System.out.println("You must enter a number. Try again.");
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
                                }
                                else if (splitZoningLineRead[0].equals("height-limit"))
                                {
                                    // First assign structure height limit decorator.
                                    structure = new HeightLimit(structure);

                                    // Typecast into HeightLimit to allow setting value.
                                    ((HeightLimit)structure)
                                    .setHeightLimit(Integer.parseInt(splitZoningLineRead[1]));
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
                logger.info(() -> gridToModify.getIStructure().convertToString());
            }
        }
        return pGrid;
    }
    /* ----------------------------------------------------------------------------------- */


    /* Method used to print menu options. */
    /* ----------------------------------------------------------------------------------- */
    private static void printMenuOptions()
    {
        // Print menu options.
        System.out.println("\n------------------------");
        System.out.println("          Menu          ");
        System.out.println("------------------------");
        System.out.println("1. Build Structure");
        System.out.println("2. Build City");
        System.out.println("3. Configure");
        System.out.println("4. Quit");
        System.out.println("------------------------\n");
    }
    /* ----------------------------------------------------------------------------------- */


    /* Method used to print the Build City options. */
    /* ----------------------------------------------------------------------------------- */
    private static void printBuildCityOptions()
    {
        System.out.println("\n---------------------------------");
        System.out.println("          Configuration          ");
        System.out.println("---------------------------------");
        System.out.println("1. Uniform");
        System.out.println("2. Random");
        System.out.println("3. Central");
        System.out.println("---------------------------------\n");
    }
    /* ----------------------------------------------------------------------------------- */
}
