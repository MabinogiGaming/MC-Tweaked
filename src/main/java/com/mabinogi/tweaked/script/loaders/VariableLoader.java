package com.mabinogi.tweaked.script.loaders;

import com.mabinogi.tweaked.api.variables.ITweakedVariable;
import com.mabinogi.tweaked.controllers.TweakedAnnotations;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.holders.VariableHolder;

import java.util.HashMap;
import java.util.Map;

import static com.mabinogi.tweaked.Tweaked.LOG;

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
    	LOG.debug("Stored TweakedVariable : " + start);
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
		ITweakedVariable variable = TweakedAnnotations.VARIABLES.get(varType);
		if (variable == null)
    	{
			ScriptHelper.reportScriptError(var.start, "TweakedVariable type \"" + varType + "\" is not recognized");
    		return;
    	}
		else
		{			
			in = variable.parse(var, var.start, in);
			
			//check for errors, note that message should be handled by the TweakedArgument
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
