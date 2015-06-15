package oop.ex6.scopes;

import java.util.ArrayList;

import oop.ex6.variables.Variable;

/**
 * represents the block of a whole java "page"
 * 
 * @author Nadav
 *
 */
public class FullScope extends Scope{

	public FullScope(ArrayList<Variable> scopeVariables, Scope parentScope, int firsLine, int lastLine) {
		super(scopeVariables, parentScope, firsLine, lastLine);
	}

}
