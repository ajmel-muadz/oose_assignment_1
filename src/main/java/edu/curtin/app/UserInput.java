package edu.curtin.app;

import java.util.Scanner;

public class UserInput 
{
    /* Method used to get row input from user to build a structure. */
    /* ----------------------------------------------------------------------------------- */
    public static int getRowInput(Grid grid, Scanner input)
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
            else if (rowInput >= grid.getNumOfRows())
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


    /* Method used to get column input from user to build structure. */
    /* ----------------------------------------------------------------------------------- */
    public static int getColumnInput(Grid grid, Scanner input)
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
            else if (columnInput >= grid.getNumOfColumns())
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
    public static int getNumOfFloorsInput(Grid grid, Scanner input)
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
    public static String getFoundationTypeInput(Scanner input)
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
    public static String getConstructionMaterialInput(Scanner input)
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
