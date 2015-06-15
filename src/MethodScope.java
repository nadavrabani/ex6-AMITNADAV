package oop.ex6.scopes;

import java.util.ArrayList;

import oop.ex6.variables.Variable;

//import java.util.ArrayList;
//
//import oop.ex6.variables.Variable;

/**
 * represents a block of a java method
 * 
 * @author Nadav
 *
 */
public class MethodScope extends Scope {
	//TODO do i need this???
//	/**
//	 * validity of the method's return statement
//	 */
//	public boolean hasValidReturn = true;
	
//	/**
//	 * number of arguments that the method gets
//	 */
//	private in't numOfArguments;//TODO i'm not sure we'l need this
//
	
	public MethodScope(ArrayList<Variable> scopeVariables, ArrayList<Variable> methodArguments, Scope parentScope, int firsLine, int lastLine) {
		super(scopeVariables, methodArguments, parentScope, firsLine, lastLine);
	}	

}













