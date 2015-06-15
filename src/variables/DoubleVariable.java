package variables;


/**
 * Created by עמית on 14/06/2015.
 */
public class DoubleVariable extends Variable{

    public DoubleVariable(boolean finalType,String line){
        super(finalType,line);
    }

    boolean correctDeclaration(String line) {
        String INT_DEC_REGEX = "\\s*[^,]"+"("+VAR_NAME_REGEX+DOUBLE_REGEX+"?)?((,"+VAR_NAME_REGEX+DOUBLE_REGEX+"?)?)*\\s";
        getMatcher(line,INT_DEC_REGEX);
        return matcher.matches();
    }

    boolean validValue(String value) {
        getMatcher(value,DOUBLE_REGEX);
        return matcher.matches();
    }
}
