package com.mabinogi.tweaked.script.loaders;

import static com.mabinogi.tweaked.Tweaked.LOG;

import java.util.HashMap;
import java.util.Map;

import com.mabinogi.tweaked.TweakedAnnotations;
import com.mabinogi.tweaked.api.variables.IVariable;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.holders.VariableHolder;

public class VariableLoader {
	
	public static final Map<String, VariableHolder> VARS = new HashMap<>();
	
	public static boolean parseVariable(String in)
    {
    	//create script
    	String start = "$" + in;
    	VariableHolder var = new VariableHolder();

    	//parse var name
    	String varName = null;
    	if (in.contains("="))
    	{
    		varName = in.substring(0, in.indexOf("=")).trim();
    		
    		in = in.substring(in.indexOf("=") + 1);
    	}
    	else
    	{
    		ScriptHelper.reportScriptError(start, "Unable to determine variable");
    		return false;
    	}
    	
    	//apply to holder
    	var.start = start;
    	var.tweak = in;
		
		//add to variables
		VARS.put(varName, var);
		
		//debug
    	LOG.debug("Stored Variable : " + start);
    	return true;
    }
    
    public static void applyVariable(VariableHolder var)
    {
    	String in = var.tweak;
    	
    	//parse var type
    	String varType = null;
    	if (in.contains("(") && in.endsWith(")"))
    	{
    		varType = in.substring(0, in.indexOf("("));
    		
    		in = in.substring(in.indexOf("(") + 1, in.length() - 1);
    	}
    	else
    	{
    		ScriptHelper.reportScriptError(var.start, "Unable to determine variable type");
    		return;
    	}
    	
    	//attempt to find varType
		IVariable variable = TweakedAnnotations.VARIABLES.get(varType);
		if (variable == null)
    	{
			ScriptHelper.reportScriptError(var.start, "Variable type \"" + varType + "\" is not recognized");
    		return;
    	}
		else
		{			
			in = variable.parse(var, var.start, in);
			
			//check for errors, note that message should be handled by the IArgument
    		if (in == null)
    		{
    			return;
    		}
		}
		
		//final checks
		if (var.value == null || var.clazz == null)
    	{
    		ScriptHelper.reportScriptError(var.start, "Incomplete variable");
    		return;
    	}
		
		//set initialized
		var.init = true;
    }

}
