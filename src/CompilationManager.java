package oop.ex6.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import oop.ex6.scopes.*;


public class CompilationManager {
	
	ArrayList<String> fileLines;
	
	//contains the last "{" in order to save running time//TODO to add to README
	int lastOpenScopeBracketLine;	

	//contains the first "}" in order to save running time//TODO to add to README
	int firstCloseScopeBracketLine;
	
	//an array of the line numbers, that start/end a scope
	ArrayList<Integer> scopeBracketLines  = new ArrayList<Integer>();
	
	public CompilationManager(ArrayList<String> fileLines) {
		this.fileLines = fileLines;
		deleteEmptyLines(this.fileLines);

	}
	
	/**
	 * this method gets an array of a file lines, and deletes the empty lines
	 * @param fileLines ArrayList<String> of a file lines
	 */
	private  void deleteEmptyLines(ArrayList<String> fileLines) {
		for (String line: fileLines) {
			if (Parser.isEmptyLine(line)) {
				this.fileLines.remove(line);
			}	
		}
	}
	
	/**
	 * this method pairs the brackets in the file, so that in the scopeBracketLines will appear (openScopeBracketLine, closeScopeBracketLine...etc.....)
	 * 
	 */
	public void pairBrackets() {
		try {
			areScopesDefined();
		} catch (compileException e) {
			System.err.println("Scopes are not defined");//TODO to change the printing message??
		}
		
	    String curLine;
	    
	    //add the full scope to the bracket line array
	    scopeBracketLines.add(0);
	    scopeBracketLines.add(fileLines.size());
	    
	    //runs only in the part that there are open brackets
	    for(int i = 0; i <= lastOpenScopeBracketLine ; i ++) {
	    	curLine = fileLines.get(i);
	        if (Parser.isScopeBeginning(curLine)) {
	        	//add the open bracket
	        	scopeBracketLines.add(i);
	        	//add it's pair
	        	scopeBracketLines.add(findClosingMate(i));
	        }
	    }
		
	}
	
	            
	            
	//TODO add to README what i did here
    /**
     * Returns the line number of the closing bracket that is the couple of the given opening bracket
     * @param int - ScopeBeginLine the begin line of the scope
     * @return int - the line number of the closing bracket that is the couple of the given opening bracket
     */
    public int findClosingMate(int ScopeBeginLine) {
    	
    	//initialize the closing bracket line
        int ScopeCloseLine = ScopeBeginLine;
        
        int counter = 1;
        String curLine;
        
        //when the counter is 0, the closing bracket is found
        while (counter > 0) {
        	ScopeCloseLine ++;
        	curLine = fileLines.get(ScopeCloseLine);
        	if (Parser.isScopeBeginning(curLine)) {
        		counter ++;
        		
        	}else if (Parser.isScopeEnding(curLine)) {
        		counter --;
        	}
        }
        return ScopeCloseLine;
    }
    
	
	//checks if the number of "{" is equal to the number of "}", and updates some fields
	private void areScopesDefined() throws compileException{
		int counter = 0;
		String curLine;
		boolean foundFirstScopeClose = false;
		
		for (int i = 0 ; i < fileLines.size() ; i ++) {
			curLine = fileLines.get(i + 1);
			if (Parser.isScopeBeginning(curLine)) {
				counter ++;
				//updates the field of the last Scope opener
				lastOpenScopeBracketLine = i;
				
			} else if (Parser.isScopeEnding(curLine)) {
				counter --;
				if (!foundFirstScopeClose) {
					foundFirstScopeClose = true;
					//updates the line of the first scope closer
					firstCloseScopeBracketLine = i;
				}
			}
			
			// if there was a "}" before a "{"
			if (counter < 0) throw new compileException();
		}
		
		//the number of "{" isn't equal to the number of "}"
		if (counter != 0) throw new compileException();
	}

}
