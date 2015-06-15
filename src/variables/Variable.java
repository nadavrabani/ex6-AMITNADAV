package variables;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Created by עמית on 13/06/2015.
 */
public abstract class Variable {
    protected static String DECIMAL_REGEX = "\\s*(=\\s*^-?\\d*\\.\\d+$)";
    protected static String INT_REGEX = "\\s*(=\\s*\\d+)";
    protected static String DOUBLE_REGEX = "(regexInt|regexDecimal)";
    protected static String BOOLEAN_REGEX = "\\s*(=\\s*true|false";
    protected static String CHAR_REGEX = "\\s*(=\\s*.)";
    protected static String STRING_REGEX = "\\s*(=\\s*\".*\")";
    final String VAR_NAME_REGEX = "([a-zA-Z]+[\\w]*\\s*|[_]+[a-zA-Z0-9]+)\\s*";
    protected static Matcher matcher;

    private String decLine;
    private final boolean finalMode;
    protected String name;
    protected String varType;

    //public Variable() {
    //}

    public Variable(boolean finalType, String line) {
        finalMode = finalType;
        decLine = line;
    }

    //\s*[^,](([a-zA-Z]+[\w]*\s*|[_]+[a-zA-Z0-9]+)\s*(=\s*\d+)?)?(((,\s*[a-zA-Z]+[\w]*|[_]+[a-zA-Z0-9]+)\s*(=\s*\d+)?)?)*\s*
    //correct line = "\\s*[^,]"+"("+VAR_NAME_REGEX+INT_REGEX+"?)?((,"+VAR_NAME_REGEX+INT_REGEX+"?)?)*\\s";

    abstract boolean correctDeclaration(String line);

    protected static Matcher getMatcher(String value, String regex) {
        Pattern pattern = Pattern.compile(regex);
        matcher = pattern.matcher(value);
        return matcher;
    }

    public abstract String getType() ;

    public abstract String getName();


    public boolean isFinal(){
        return finalMode;
    }

    abstract boolean validValue(String value);
}
