package com.mabinogi.tweaked.script.variables.iface;

import com.mabinogi.tweaked.script.holders.VariableHolder;

public interface IVariable {
	
	public String parse(VariableHolder var, String start, String in);

}
