package com.mabinogi.tweaked.api.variables;

import com.mabinogi.tweaked.script.holders.VariableHolder;

public interface IVariable {
	
	public String parse(VariableHolder var, String start, String in);

}
