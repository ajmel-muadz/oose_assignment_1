package edu.curtin.app;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Logger;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class App
{
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws IOException
    {
        // Declare the grid first.
        Grid grid = new Grid();

        /* This block of code is for reading the file and populating the grid. */
        /* ----------------------------------------------------------------------------------- */
        int rowsAvailable = -1;
        int columnsAvailable = -1;

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

                logger.info(grid.getGridSquares().get(4).get(1).getIStructure().convertToString());
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
                System.out.print("Please enter your choice (by typing one of the numbers): ");
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
                    rowChosen = getRowInput(input, rowsAvailable);
                    columnChosen = getColumnInput(input, columnsAvailable);
                    gridChosen = grid.getGridSquares().get(rowChosen).get(columnChosen);

                    // We then ask for the relevant things in the structure to put in the grid.
                    numOfFloorsChosen = getNumOfFloorsInput(input);
                    foundationTypeChosen = getFoundationTypeInput(input);
                    constructionMaterialChosen = getConstructionMaterialInput(input);
                    /* ---------------------------------------------------------------------------------- */

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
                            for (int i = 0; i < splitLine.length; i++)
                            {
                                if (splitLine[i].contains("flood-risk"))
                                {
                                    String[] splitLineFloodRisk = splitLine[i].split("=");
                                    double floodRiskValue = Double.parseDouble(splitLineFloodRisk[1]);
                                    overallCost = overallCost * (1.00 + (floodRiskValue / 50.00));
                                }
                            }
                        }

                        System.out.println(overallCost);
                    }

                    // At the end of the 'Build structure' process we reset the grid structure.
                    // This is because 'Build structure' is more of a plan, doesn't actually build. 
                    gridChosen.setIStructure(existingStructure);

                }
                else if (optionChoice.equals("2"))
                {
                    // Build City
                }
                else if (optionChoice.equals("3"))
                {
                    // Change configuration
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


    /* Method used to get row input from user to build a structure. */
    /* ----------------------------------------------------------------------------------- */
    private static int getRowInput(Scanner input, int pRowsAvailable)
    {
        /* Ask for the row first for the specific grid square. */
        /* -------------------------------------------------------------------------------- */
        while (true)
        {
            System.out.print("Enter the row of the grid square: ");
            int rowInput = input.nextInt();
            input.nextLine();

            if (rowInput < 0)
            {
                System.out.println("The row cannot be a negative number!\n");
            }
            else if (rowInput >= pRowsAvailable)
            {
                System.out.println("That exceeds the size of the row! (zero-based indexing).\n");
            }
            else
            {
                return rowInput;
            }
        }
        /* -------------------------------------------------------------------------------- */
    }
    /* ----------------------------------------------------------------------------------- */


    /* Method used to get column input from user to build structure. */
    /* ----------------------------------------------------------------------------------- */
    private static int getColumnInput(Scanner input, int pColumnsAvailable)
    {
        /* Ask for the column first for the specific grid square. */
        /* -------------------------------------------------------------------------------- */
        while (true)
        {
            System.out.print("Enter the column of the grid square: ");
            int columnInput = input.nextInt();
            input.nextLine();

            if (columnInput < 0)
            {
                System.out.println("The column cannot be a negative number!");
            }
            else if (columnInput >= pColumnsAvailable)
            {
                System.out.println("That exceeds the size of the column! (zero-based indexing).");
            }
            else
            {
                return columnInput;
            }
        }
        /* -------------------------------------------------------------------------------- */
    }
    /* ----------------------------------------------------------------------------------- */


    /* Method used to get the number of floors from user to build structure. */
    /* ----------------------------------------------------------------------------------- */
    private static int getNumOfFloorsInput(Scanner input)
    {
        /* Ask for the number of floors for the structure. */
        /* -------------------------------------------------------------------------------- */
        while (true)
        {
            System.out.print("Enter the number of floors: ");
            int numOfFloorsInput = input.nextInt();
            input.nextLine();

            if (numOfFloorsInput <= 0)
            {
                System.out.println("The number of floors has to be a positive number!");
            }
            else
            {
                return numOfFloorsInput;
            }
        }
        /* -------------------------------------------------------------------------------- */
    }
    /* ----------------------------------------------------------------------------------- */


    /* Method used to get foundation type from user to build structure. */
    /* ----------------------------------------------------------------------------------- */
    private static String getFoundationTypeInput(Scanner input)
    {
        /* Ask for the foundation type: either 'slab' or 'stilts'. */
        /* -------------------------------------------------------------------------------- */
        while (true)
        {
            System.out.println("\nChoose your foundation type:");
            System.out.println("1. Slab");
            System.out.println("2. Stilts");
            System.out.println("");
            
            System.out.print("Enter foundation type number: ");
            String foundationTypeNumber = input.nextLine();

            if (foundationTypeNumber.equals("1"))
            {
                System.out.println("You chose slab");
                return "slab";
            }
            else if (foundationTypeNumber.equals("2"))
            {
                System.out.println("You chose stilts");
                return "stilts";
            }
            else
            {
                System.out.println("You must type either '1' or '2'!");
            }
        }
        /* -------------------------------------------------------------------------------- */
    }
    /* ----------------------------------------------------------------------------------- */


    /* Method used to get construction material type from user to build structure. */
    /* ----------------------------------------------------------------------------------- */
    private static String getConstructionMaterialInput(Scanner input)
    {
        /* Ask for the construction material: either 'wood', 'stone', 'brick', 'concrete'. */
        /* -------------------------------------------------------------------------------- */
        while (true)
        {
            System.out.println("\nChoose your construction material:");
            System.out.println("1. Wood");
            System.out.println("2. Stone");
            System.out.println("3. Brick");
            System.out.println("4. Concrete");

            System.out.print("Enter construction material number: ");
            String constructionMaterialNumber = input.nextLine();

            if (constructionMaterialNumber.equals("1"))
            {
                System.out.println("You chose wood");
                return "wood";
            }
            else if (constructionMaterialNumber.equals("2"))
            {
                System.out.println("You chose stone");
                return "stone";
            }
            else if (constructionMaterialNumber.equals("3"))
            {
                System.out.println("You chose brick");
                return "brick";
            }
            else if (constructionMaterialNumber.equals("4"))
            {
                System.out.println("You chose concrete");
                return "concrete";
            }
            else
            {
                System.out.println("You must type either '1', '2', '3' or '4'!");
            }
        }
        /* -------------------------------------------------------------------------------- */
    }
    /* ----------------------------------------------------------------------------------- */
}
