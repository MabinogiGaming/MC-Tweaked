package com.mabinogi.tweaked.script.variables;

import com.mabinogi.tweaked.api.annotations.TweakedVariable;
import com.mabinogi.tweaked.api.variables.ITweakedVariable;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.holders.VariableHolder;
import com.mabinogi.tweaked.script.objects.ObjIngredient;
import com.mabinogi.tweaked.script.objects.ObjStack;
import com.mabinogi.tweaked.script.objects.ObjStackList;

import java.util.ArrayList;
import java.util.List;

@TweakedVariable("stackList")
public class VarStackList implements ITweakedVariable
{
	@Override
	public String parse(VariableHolder var, String start, String in)
	{
		//create list
		List<ObjStack> ingredients = new ArrayList<>();
		
		while (in.length() > 0)
    	{
			if (!in.startsWith("<"))
			{
				ScriptHelper.reportScriptError(start, "Malformed ITweakedIngredient, must start with \"<\"");
				return null;
			}
			
			if (!in.contains(">"))
			{
				ScriptHelper.reportScriptError(start, "Malformed ITweakedIngredient, must end with \">\"");
				return null;
			}
			
			//get arg
			String arg = in.substring(0, in.indexOf(">") + 1);
			
			//attempt to build ingredient
			ObjIngredient obj = new ObjIngredient(arg.substring(arg.indexOf("<") + 1, arg.indexOf(">")));
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
			
			ingredients.add((ObjStack) obj.ingredient);
			
			//clean line
			in = in.substring(in.indexOf(">") + 1);
			if (in.startsWith(",")) in = in.substring(1);
    	}
		
		//add arg
		var.clazz = ObjStackList.class;
		var.value = new ObjStackList(ingredients);
		
		//list consumes all tokens
		return "";
	}
}
