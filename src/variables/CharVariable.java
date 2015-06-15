package variables;


/**
 * Created by עמית on 14/06/2015.
 */
public class CharVariable extends Variable{

    public CharVariable(boolean finalType,String line){
        super(finalType,line);
    }

    boolean correctDeclaration(String line) {
        String INT_DEC_REGEX = "\\s*[^,]"+"("+VAR_NAME_REGEX+CHAR_REGEX+"?)?((,"+VAR_NAME_REGEX+CHAR_REGEX+"?)?)*\\s";
        getMatcher(line,INT_DEC_REGEX);
        return matcher.matches();
    }

    boolean validValue(String value) {
        getMatcher(value,CHAR_REGEX);
        return matcher.matches();
    }
}
