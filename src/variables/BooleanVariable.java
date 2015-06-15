package variables;


/**
 * Created by עמית on 14/06/2015.
 */
public class BooleanVariable extends Variable{

    public BooleanVariable(boolean finalType,String line){
        super(finalType,line);
    }

    boolean correctDeclaration(String line) {
        String INT_DEC_REGEX = "\\s*[^,]"+"("+VAR_NAME_REGEX+BOOLEAN_REGEX+"?)?((,"+VAR_NAME_REGEX+BOOLEAN_REGEX+"?)?)*\\s";
        getMatcher(line,INT_DEC_REGEX);
        return matcher.matches();
    }

    boolean validValue(String value) {
        getMatcher(value,BOOLEAN_REGEX);
        return matcher.matches();
    }
}
