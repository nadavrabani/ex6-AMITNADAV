package oop.ex6.scopes;

import java.util.ArrayList;

import oop.ex6.variables.*;

/**
 * an absract class of a block that inherits to Full,Inner and Method scopes
 * 
 * @author Nadav
 *
 */
public abstract class Scope {

	/**
	 * the variables of the scope, in a list
	 */
	protected ArrayList<Variable> scopeVariables;
	
	//TODO to erase this if i can, and use it if i need
//	/**
//	 * the method arguments array
//	 */
//	private ArrayList<Variable> methodArguments;

	/**
	 * the line in the general block where the block starts, and the line that
	 * the block ends
	 */
	public int firstLine, lastLine;// TODO maybe to make it private?

	/**
	 * the Block surrounding this block
	 */
	protected Scope parent = null;// TODO do i need also a child scope?
	
	/**
	 * cTor for a scope that isn't a method
	 * @param parentScope
	 * @param firsLine
	 * @param lastLine
	 */
	protected Scope(ArrayList<Variable> scopeVariables, Scope parentScope, int firsLine, int lastLine) {
		this.scopeVariables = scopeVariables;
		parent = parentScope;
		this.firstLine = firsLine;
		this.lastLine = lastLine;
	}
	
	/**
	 * cTor for method scope//TODO is this way fine: design wise??
	 * @param parentScope
	 * @param firsLine
	 * @param lastLine
	 * @param methodArguments
	 */
	protected Scope(ArrayList<Variable> scopeVariables, ArrayList<Variable> methodArguments, Scope parentScope, int firsLine, int lastLine) {
		this.scopeVariables = scopeVariables;
		parent = parentScope;
		this.firstLine = firsLine;
		this.lastLine = lastLine;
		//add the method arguments to the scope's variable array
		for (Variable var : methodArguments) {
			this.scopeVariables.add(var);
		}
	}

	/**
	 * checks if the given variable is in a "higher" scope (AKA recursive parent
	 * scope)
	 * 
	 * @param variable
	 *            a given variable object
	 */
	public boolean knowsVariable(Variable variable) {

		// check in the current scope
		boolean retVal = findVarInScope(variable, this);// TODO is the word
														// "this" fine??

		Scope parentScope = parent;

		// runs until the value was found or it reached the most outer scope
		while (retVal != true && parentScope != null) {
			parentScope = parentScope.getParent();
			retVal = findVarInScope(variable, parentScope);
		}

		return retVal;
	}

	/**
	 * checks if the given scope contains the given variable
	 * 
	 * @param variable
	 *            a variable
	 * @param scope
	 *            Scope object
	 * @return true if the given scope contains the given variable, false
	 *         otherwise
	 */
	private boolean findVarInScope(Variable variable, Scope scope) {
		for (Variable var : scope.getVars()) {
			if (var.equals(variable)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * returns the scope variables array list
	 * 
	 * @return ArrayList<Variable> - the scope variables array list
	 */
	public ArrayList<Variable> getVars() {
		return scopeVariables;
	}

	/**
	 * returns the scope's parent
	 * 
	 * @return ArrayList<Variable> - the scope's parent
	 */
	public Scope getParent() {
		return parent;
	}
	

}


















