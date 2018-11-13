package com.mabinogi.tweaked.script.variables;

import com.mabinogi.tweaked.api.annotations.TweakedVariable;
import com.mabinogi.tweaked.api.variables.ITweakedVariable;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.holders.VariableHolder;
import com.mabinogi.tweaked.script.objects.ObjIngredient;
import com.mabinogi.tweaked.script.objects.ObjStack;

@TweakedVariable("stack")
public class VarStack implements ITweakedVariable
{
	@Override
	public String parse(VariableHolder var, String start, String in)
	{
		if (!in.startsWith("<"))
		{
			ScriptHelper.reportScriptError(start, "Malformed ITweakedIngredient, must start with \"<\"");
			return null;
		}
		
		if (!in.endsWith(">"))
		{
			ScriptHelper.reportScriptError(start, "Malformed ITweakedIngredient, must end with \">\"");
			return null;
		}
		
		String arg = in;
		
		//attempt to build component
		ObjIngredient obj = new ObjIngredient(arg.substring(arg.indexOf("<") + 1, arg.lastIndexOf(">")));
		if (obj == null || obj.ingredient == null)
		{
			ScriptHelper.reportScriptError(start, "ITweakedIngredient \"" + arg + "\" doesn't exist");
			return null;
		}
		
		if (!(obj.ingredient instanceof ObjStack))
		{
			ScriptHelper.reportScriptError(start, "ITweakedIngredient \"" + arg + "\" is not a Stack");
			return null;
		}
		
		//add arg
		var.clazz = ObjStack.class;
		var.value = obj.ingredient;
		
		return "";
	}

}