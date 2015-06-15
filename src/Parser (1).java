package oop.ex6.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.regex.*;
import java.util.List;

import oop.ex6.variables.Variable;

public class Parser {
	// TODO maybe to create a class that only contains the private static final
	// things? is there a design like that?

	// TODO if i want to work with strings here, to create a method that deletes
	// all the spaces in a line

	private static final String SCOPE_ENDING_LINE = "\\s*}\\s*";
	private static final String SCOPE_BEGINNING_LINE = ".*\\{";

	// private static final String SPACE = " ";
	// private static final String END_OF_LINE = ";";

	private static final String VALID_VAR_DECLARATION = "\\s*(final\\s*)?\\s*(int|double|String|boolean|char)\\s*[a-zA-Z]\\w*(\\s*(\\s*\\=\\s*\\S*.*)?[a-zA-Z]\\w*\\)?";
	private static final String VALID_METHOD_DECLARATION = "\\s*void\\s*[a-zA-Z]\\w*\\("
			+ VALID_VAR_DECLARATION + "\\s*\\)\\s*\\;\\s* ";
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
		// TODO i need to correct this method because i need to check that the
		// global variables aren't inside any method!!!!
		for (String line : fileLines) {
			if (isMethodDeclaration(line)) {
				methodsAndGlobals.add(line);
			} else if (isVarDeclaration(line)) {
				methodsAndGlobals.add(line);
			}
		}
		return methodsAndGlobals;
	}
	
	//TODO maybe these functions should be in a different class????
	// TODO to explain in the README what i did here
	/**
	 * this returns the file method names
	 * @return
	 */
	public static ArrayList<String> getMethodNames(List<String> fileLines, ArrayList<Integer> scopeBracketLines) {
		//TODO we'l need to think about how we parse a method: name, arguments......
		ArrayList<Integer> innerScopeLines = getInnerScopeLines(scopeBracketLines, 0);
		String curLine;
		
		ArrayList<String> methodNames = new ArrayList<String>();
		
		int lineNumber = 0;
				
		while (lineNumber < fileLines.size()) {
			
			//TODO to check if it's minus 1
			curLine = fileLines.get(lineNumber - 1);
			
			if (isMethodDeclaration(curLine)) {
				//TODO we'l need to think about how to deal with a method declaration line(arguments, name....)
				String methodName = getMethodName(curLine);
				methodNames.add(methodName);
			}
			
			lineNumber = nextNonNestedLineNumber(innerScopeLines, lineNumber);
		}
		return methodNames;
		
	}
	
	
	//TODO maybe these functions should be in a different class????
	// TODO to explain in the README what i did here
	/**
	 * returns a hash set of the scopes variables
	 * @param fileLines
	 * @param scopeBracketLines
	 * @param firstLineIndex
	 * @return
	 */
	public static HashSet<Variable> getScopeVariables(List<String> fileLines,
			ArrayList<Integer> scopeBracketLines, int firstLineIndex) {
		
		int scopeBeginLine = scopeBracketLines.get(firstLineIndex);
		int scopeEndLine = scopeBracketLines.get(firstLineIndex + 1);
		ArrayList<Integer> innerScopeLines = getInnerScopeLines(scopeBracketLines, firstLineIndex);
		String curLine;
		
		HashSet<Variable> scopeVariables = new HashSet<Variable>();
		
		int lineNumber = scopeBeginLine;
				
		while (lineNumber < scopeEndLine) {
			
			//TODO to check if it's minus 1
			curLine = fileLines.get(lineNumber - 1);
			
			if (isVarDeclaration(curLine)) {
				//TODO we'l need to seperate if it is a list of variables
				Variable var = createVariables(curLine);
				//TODO to figure out how to work with hash function in java
				scopeVariables.hashCode();
			}
			
			lineNumber = nextNonNestedLineNumber(innerScopeLines, lineNumber);
		}
		return scopeVariables;
	}

	
	/**
	 * checks if a line is in a bested scope or not, if it is the nested scope:
	 * it returns the next line it needs to check(the line after the closing
	 * bracket of the nested scope), id it isn't in a nested scope: it will
	 * return the given line
	 * 
	 * @return the next line to check
	 */
	private static int nextNonNestedLineNumber(ArrayList<Integer> innerScopeLines, int lineNumber) {
		// the interval of indexes in the bracket array between two scopes
		final int ARRAY_INTERVAL = 2;
		
		for (int i = 0; i < innerScopeLines.size(); i = i + ARRAY_INTERVAL) {
			if (lineNumber >= innerScopeLines.get(i) && lineNumber <= innerScopeLines.get(i + 1)) {
				return innerScopeLines.get(i + 1) + 1;
			}
		}
		return lineNumber + 1;

	}

	// TODO to explain in the README what i did here
	/**
	 * this method is a helper for the getScopeVariables method, it returns an
	 * array of the lines the method shouldn't check while creating the scope
	 * variables, if there are no nested scopes, returns null
	 * 
	 * @param scopeBracketLines
	 * @param firstLineIndex
	 * @return
	 */
	private static ArrayList<Integer> getInnerScopeLines(ArrayList<Integer> scopeBracketLines,
			int firstLineIndex) {
		ArrayList<Integer> innerScopeLines = new ArrayList<Integer>();
		int givenScopeStart = scopeBracketLines.get(firstLineIndex);
		int givenScopeEnd = scopeBracketLines.get(firstLineIndex + 1);

		// the scopeBracketLines array has no inner scopes
		if (scopeBracketLines.size() == firstLineIndex) {
			return null;

		} else {

			// the interval of indexes in the bracket array between two scopes
			final int ARRAY_INTERVAL = 2;
			// the scope in the loop start line and end line
			int curScopeBegin, curScopeEnd;

			for (int i = firstLineIndex + ARRAY_INTERVAL; i < scopeBracketLines.size(); i = i
					+ ARRAY_INTERVAL) {

				curScopeBegin = scopeBracketLines.get(i);
				curScopeEnd = scopeBracketLines.get(i + 1);

				if ((curScopeBegin > givenScopeStart) && (curScopeEnd < givenScopeEnd)) {
					innerScopeLines.add(curScopeBegin);
					innerScopeLines.add(curScopeEnd);

					// the next scope is not nested in the given scope
				} else
					break;

			}
		}
		return innerScopeLines;

	}

	private static boolean isVarDeclaration(String line) {
		Matcher matcher = getMatcher(line, VALID_VAR_DECLARATION);
		return matcher.matches();

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
	 * @param line
	 *            - the line to check if scope start
	 * @return true if the line starts a new scope, false otherwise
	 * 
	 */
	public static boolean isScopeBeginning(String line) {
		// delete the spaces from the line in case there are spaces after the
		// "{"
		Matcher matcher = getMatcher(line, SCOPE_BEGINNING_LINE);
		return matcher.matches();
	}

	/**
	 * Checks if a new scope Ends in a given line
	 * 
	 * @param line
	 *            - String , the line to check if a scope starts in it
	 * @return true if the line ends a scope, false otherwise
	 * 
	 */
	public static boolean isScopeEnding(String line) {
		// delete the spaces from the line
		Matcher matcher = getMatcher(line, SCOPE_ENDING_LINE);
		return matcher.matches();
	}

	/**
	 * checks if a line is empty
	 * 
	 * @param line
	 *            - String , the line to check if a scope starts in it
	 * @return true if the line ends a scope, false otherwise
	 * 
	 */
	public static boolean isEmptyLine(String line) {
		// delete the spaces from the line
		Matcher matcher = getMatcher(line, EMPTY_LINE);
		return matcher.matches();
	}

	/*
	 * Returns a matcher for the given line and regex expression.
	 */
	private static Matcher getMatcher(String line, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line);
		return matcher;
	}
}
