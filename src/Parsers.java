import java.util.ArrayList;
import java.util.regex.*;
import java.util.List;
import variables.VariableFactory;
import oop.ex6.variables.Variable;
import variables.Variable;

public class Parser {
	// TODO maybe to create a class that only contains the private static final
	// things? is there a design like that?
	
    //TODO if i want to work with strings here, to create a method that deletes all the spaces in a line

	private static String SPACE_REGEX = "\\s+";
	private static String FINAL_REGEX = "Final";
	private static String VAR_TYPE_REGEX = "int|double|boolean|String|char";
	private static boolean finalType;
	private static String varType;
	private static Matcher matcher;
	private static final String SCOPE_ENDING_LINE = "\\s*}\\s*";
	private static final String SCOPE_BEGINNING_LINE = ".*\\{";

	//private static final String SPACE = " ";
	//private static final String END_OF_LINE = ";";
	
	private static final String VALID_VAR_DECLARATION = "\\s*(final\\s*)?\\s*(int|double|String|boolean|char)\\s*[a-zA-Z]\\w*(\\s*(\\s*\\=\\s*\\S*.*)?[a-zA-Z]\\w*\\)?";
	private static final String VALID_METHOD_DECLARATION = "\\s*void\\s*[a-zA-Z]\\w*\\(" + VALID_VAR_DECLARATION + "\\s*\\)\\s*\\;\\s* ";
	private static final String EMPTY_LINE = "\\s*";

	/**
	 * parses the file lines and returns the lines of method declaration, and
	 * global variable declaration.
	 * 
	 * //TODO add to the README that we use this firstParse in order to verify
	 * method calls to methods, or using global variables even if they were
	 * declared a-f-t-e-r them
	 * 
	 * @param fileLines
	 *            List<String> - contains the lines of the file
	 * @return ArrayList<String> containing only the lines of method declaration
	 *         and global variable declaration
	 */
	public static ArrayList<String> firstParse(List<String> fileLines) {
		ArrayList<String> methodsAndGlobals = new ArrayList<String>();
		//TODO i need to correct this method because i need to check that the global variables aren't inside any method!!!!
		for (String line : fileLines) {			
			if (isMethodDeclaration(line)) {
				methodsAndGlobals.add(line);
			} else if (isVarDeclaration(line)) {
				methodsAndGlobals.add(line);
			}
		}
		return methodsAndGlobals;
	}

	//TODO to explain in the README what i did here
	/**
	 * parses a certain scope and returns it's variables declaration lines
	 * @param fileLines List<String> - contains the lines of the file
	 * @param beginningLine the first line of the scope
	 * @param endingLine the last line of the scope 
	 * @return ArrayList<Integer> an array list of the scopes variables 
	 */
	public static ArrayList<Integer> getScopeVariableLines(List<String> fileLines, int beginningLine,int endingLine) {
		
		//TODO if there can be a variable declaration after inner scope starts i need to change this method!!!
		
		ArrayList<Integer> scopeVariables = new ArrayList<Integer>();
		for (int i = beginningLine; i <= endingLine; i++) {	
			if (isVarDeclaration(fileLines.get(i + 1))) {
				scopeVariables.add(i + 1);
			}
		}
		return scopeVariables;
	}

	/**
	 * Checks if the given line is a method declaration
	 * 
	 * @param line
	 *            - String the line to check
	 * @return true if is a method declaration, false otherwise
	 * 
	 */
	// TODO maybe to shorten the name??
	public static boolean isMethodDeclaration(String line) {
		// TODO throw exception?think what errors can i have in a method call?
		Matcher matcher = getMatcher(line, VALID_METHOD_DECLARATION);
		return matcher.matches();

	}
	
    /**
     * Checks if a new scope begins in a given line
     * 
     * @param line - the line to check if scope start
     * @return true if the line starts a new scope, false otherwise
     * 
     */
    public static boolean isScopeBeginning(String line) {
    	//delete the spaces from the line in case there are spaces after the "{"
    	Matcher matcher = getMatcher(line, SCOPE_BEGINNING_LINE);
		return matcher.matches();
    }
    
    /**
     * Checks if a new scope Ends in a given line
     * 
     * @param line - String , the line to check if a scope starts in it
     * @return true if the line ends a scope, false otherwise
     * 
     */
    public static boolean isScopeEnding(String line) {
    	//delete the spaces from the line
    	Matcher matcher = getMatcher(line, SCOPE_ENDING_LINE);
		return matcher.matches();
    }
    	
    
    /**
     * checks if a line is empty
     * 
     * @param line - String , the line to check if a scope starts in it
     * @return true if the line ends a scope, false otherwise
     * 
     */
    public static boolean isEmptyLine(String line) {
    	//delete the spaces from the line
    	Matcher matcher = getMatcher(line, EMPTY_LINE);
		return matcher.matches();
    }
    
	/*
	 * Returns a matcher for the given line and regex expression.
	 */
	private static Matcher getMatcher(String line, String regex) {
		Pattern pattern = Pattern.compile(regex);
		matcher = pattern.matcher(line);
		return matcher;
	}

		private static boolean isVarDeclaration(String line) {
			//Final statement
			matcher = getMatcher(line,SPACE_REGEX);
			matcher.replaceAll(" ");
			matcher = getMatcher(line, FINAL_REGEX);
			finalType = matcher.lookingAt();
			if (finalType == true)
				return true;

			//variable type
			matcher = getMatcher(line, VAR_TYPE_REGEX);
			if (matcher.lookingAt()) {
				varType = matcher.group().toString();
				return true;
			}
			else return false;
		}

		private static boolean createVariables(String line){
			if (finalType){
				line.replaceFirst(FINAL_REGEX,"");
			}
			line.replaceFirst(varType,"");
			Variable variable = VariableFactory.createVariable(varType, finalType, line);
			if (!(variable==null))
				return true;
			else
			return false;
		}
}
