package com.mabinogi.tweaked.script.arguments;

import com.mabinogi.tweaked.api.annotations.TweakedArgument;
import com.mabinogi.tweaked.api.arguments.ITweakedArgument;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.holders.ActionHolder;
import com.mabinogi.tweaked.script.objects.ObjIngredient;

@TweakedArgument("<")
public class ArgIngredient implements ITweakedArgument
{

	@Override
	public String parse(ActionHolder action, String start, String in)
	{
		if (!in.contains(">"))
		{
			ScriptHelper.reportScriptError(start, "Malformed ITweakedIngredient, an enclosing \">\" is missing");
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
		
		//add arg
		action.args.add(obj.ingredient);
		action.classes.add(obj.ingredient.getClass());
		
		//clean line
		in = in.substring(in.indexOf(">") + 1);
		if (in.startsWith(",")) in = in.substring(1);
		
		return in;
	}

}
