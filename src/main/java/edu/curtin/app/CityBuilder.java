package edu.curtin.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CityBuilder 
{
    private Map<Integer, IBuildCity> strategies = new HashMap<>();

    public void initStrategies()
    {
        strategies.put(1, new UniformBuildCity());
        strategies.put(2, new RandomBuildCity());
        strategies.put(3, new CentralBuildCity());
    }

    public void setStrategy(Integer key, Grid grid, Scanner input)
    {
        IBuildCity strategy = strategies.get(key);
        strategy.setStructures(grid, input);
    }
}
