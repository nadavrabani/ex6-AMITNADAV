package variables;


/**
 * Created by עמית on 14/06/2015.
 */
public class StringVariable extends Variable{

    public StringVariable(boolean finalType,String line){
        super(finalType,line);
    }

    boolean correctDeclaration(String line) {
        String INT_DEC_REGEX = "\\s*[^,]"+"("+VAR_NAME_REGEX+STRING_REGEX+"?)?((,"+VAR_NAME_REGEX+STRING_REGEX+"?)?)*\\s";
        getMatcher(line,INT_DEC_REGEX);
        return matcher.matches();
    }

    boolean validValue(String value) {
        getMatcher(value,STRING_REGEX);
        return matcher.matches();
    }
}
