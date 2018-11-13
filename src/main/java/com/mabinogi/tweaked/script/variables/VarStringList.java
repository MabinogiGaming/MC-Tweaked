package com.mabinogi.tweaked.script.variables;

import com.mabinogi.tweaked.api.annotations.TweakedVariable;
import com.mabinogi.tweaked.api.variables.ITweakedVariable;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.holders.VariableHolder;
import com.mabinogi.tweaked.script.objects.ObjStringList;

import java.util.ArrayList;
import java.util.List;

@TweakedVariable("stringlist")
public class VarStringList implements ITweakedVariable
{
	@Override
	public String parse(VariableHolder var, String start, String in)
	{
		//create list
		List<String> strings = new ArrayList<>();
		
		while (in.length() > 0)
    	{
			if (!in.startsWith("\""))
			{
				ScriptHelper.reportScriptError(start, "Malformed String, must start with \"\"\"");
				return null;
			}
			
			//remove leading speechmark
			in = in.substring(1);
			
			if (!in.contains("\""))
			{
				ScriptHelper.reportScriptError(start, "Malformed String, must end with \"\"\"");
				return null;
			}
			
			String arg = in.substring(0, in.indexOf("\""));
			
			strings.add(arg);
			
			//clean line
			in = in.substring(in.indexOf("\"") + 1);
			if (in.startsWith(",")) in = in.substring(1);
    	}
		
		//add arg
		var.clazz = ObjStringList.class;
		var.value = new ObjStringList(strings);
		
		//list consumes all tokens
		return "";
	}

}