package oop.ex6.scopes;

import java.util.ArrayList;

import oop.ex6.variables.Variable;

/**
 * A block that represents an if statement / while loop block
 * @author Nadav
 *
 */
public class InnerScope extends Scope{

	public InnerScope(ArrayList<Variable> scopeVariables, Scope parentScope, int firsLine, int lastLine) {
		super(scopeVariables, parentScope, firsLine, lastLine);
	}
}
