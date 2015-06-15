package variables;


/**
 * Created by עמית on 14/06/2015.
 */
public class IntVariable extends Variable{

    public IntVariable(boolean finalType,String line){
        super(finalType,line);
    }

    boolean correctDeclaration(String line) {
        String INT_DEC_REGEX = "\\s*[^,]"+"("+VAR_NAME_REGEX+INT_REGEX+"?)?((,"+VAR_NAME_REGEX+INT_REGEX+"?)?)*\\s";
        getMatcher(line,INT_DEC_REGEX);
        return matcher.matches();
    }

    @Override
    public String getType() {
        return "int";
    }

    @Override
    public String getName() {
        return null;
    }

    boolean validValue(String value) {
        getMatcher(value,INT_REGEX);
        return matcher.matches();
    }
}
