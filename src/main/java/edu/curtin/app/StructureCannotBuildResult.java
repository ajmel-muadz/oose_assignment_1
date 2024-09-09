package edu.curtin.app;

public class StructureCannotBuildResult 
{
    private static String result = "";

    // Mutator
    public static void setResult(String pResult)
    {
        result = result + pResult + "\n";
    }
    public static void clearResult()
    {
        result = "";
    }

    // Accessor
    public static String getResult()
    {
        return result;
    }
}
