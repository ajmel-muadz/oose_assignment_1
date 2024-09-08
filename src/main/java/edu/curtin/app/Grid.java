package edu.curtin.app;

import java.util.List;
import java.util.ArrayList;

public class Grid 
{
    // Fields or attributes.
    private int numOfRows;
    private int numOfColumns;
    private List<List<GridSquare>> gridSquares;


    // Default constructor
    public Grid()
    {
        numOfRows = 0;
        numOfColumns = 0;
        gridSquares = new ArrayList<>();
    }

    // Constructor with parameters
    public Grid(int pNumOfRows, int pNumOfColumns)
    {
        numOfRows = pNumOfRows;
        numOfColumns = pNumOfColumns;
        gridSquares = new ArrayList<>();

        for (int r = 0; r < numOfRows; r++)
        {
            List<GridSquare> newRow = new ArrayList<>();
            for (int c = 0; c < numOfColumns; c++)
            {
                newRow.add(new GridSquare());
            }
            gridSquares.add(newRow);
        }
    }

    // Accessors
    public int getNumOfRows()
    {
        return numOfRows;
    }
    public int getNumOfColumns()
    {
        return numOfColumns;
    }
    public List<List<GridSquare>> getGridSquares()
    {
        return gridSquares;
    }
}
