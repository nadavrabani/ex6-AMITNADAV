package variables;

/**
 * Created by עמית on 14/06/2015.
 */
public class VariableFactory {
    private static String INT = "int";
    private static String DOUBLE = "double";
    private static String STRING = "String";
    private static String BOOLEAN = "boolean";
    private static String CHAR = "char";

    public static Variable createVariable(String varType,boolean finalType,String line){
        if (varType.equals(INT))
            return new IntVariable(finalType,line);
        if (varType.equals(DOUBLE))
            return new DoubleVariable(finalType,line);
        if (varType.equals(STRING))
            return new StringVariable(finalType,line);
        if (varType.equals(BOOLEAN))
            return new BooleanVariable(finalType,line);
        if (varType.equals(CHAR))
            return new CharVariable(finalType,line);

        }
    }
}
