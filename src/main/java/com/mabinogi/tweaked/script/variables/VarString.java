package com.mabinogi.tweaked.script.variables;

import com.mabinogi.tweaked.annotations.TweakedVariable;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.holders.VariableHolder;
import com.mabinogi.tweaked.script.variables.iface.IVariable;

@TweakedVariable("String")
public class VarString implements IVariable 
{
	@Override
	public String parse(VariableHolder var, String start, String in)
	{
		if (!in.startsWith("\""))
		{
			ScriptHelper.reportScriptError(start, "Malformed String, must start with \"\"\"");
			return null;
		}
		
		//remove leading speechmark
		in = in.substring(1);
		
		if (!in.endsWith("\""))
		{
			ScriptHelper.reportScriptError(start, "Malformed String, must end with \"\"\"");
			return null;
		}
		
		String arg = in.substring(0, in.indexOf("\""));
		
		//add arg
		var.clazz = String.class;
		var.value = arg;
		
		return "";
	}

}