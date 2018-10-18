package com.mabinogi.tweaked.script.arguments;

import com.mabinogi.tweaked.api.annotations.TweakedArgument;
import com.mabinogi.tweaked.api.arguments.IArgument;
import com.mabinogi.tweaked.api.objects.IIngredient;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.builders.IngredientBuilder;
import com.mabinogi.tweaked.script.holders.ActionHolder;

@TweakedArgument("<")
public class ArgIngredient implements IArgument {

	@Override
	public String parse(ActionHolder action, String start, String in)
	{
		if (!in.contains(">"))
		{
			ScriptHelper.reportScriptError(start, "Malformed Ingredient, an enclosing \">\" is missing");
			return null;
		}
		
		//get arg
		String arg = in.substring(0, in.indexOf(">") + 1); 
		
		//attempt to build ingredient
		IIngredient ingredient = IngredientBuilder.build(arg.substring(arg.indexOf("<") + 1, arg.indexOf(">")));
		if (ingredient == null)
		{
			ScriptHelper.reportScriptError(start, "Ingredient \"" + arg + "\" doesn't exist");
			return null;
		}
		
		//add arg
		action.args.add(ingredient);
		action.classes.add(ingredient.getClass());
		
		//clean line
		in = in.substring(in.indexOf(">") + 1);
		if (in.startsWith(",")) in = in.substring(1);
		
		return in;
	}

}
