package com.mabinogi.tweaked.script.arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mabinogi.tweaked.api.annotations.TweakedArgument;
import com.mabinogi.tweaked.api.arguments.IArgument;
import com.mabinogi.tweaked.api.objects.IIngredient;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.holders.ActionHolder;
import com.mabinogi.tweaked.script.objects.ObjIngredient;
import com.mabinogi.tweaked.script.objects.ObjRecipeShaped;
import com.mabinogi.tweaked.script.objects.ObjRecipeShapeless;

@TweakedArgument("[")
public class ArgRecipe implements IArgument
{
	//pattern to find rows
	Pattern pattern = Pattern.compile("\\[(.*?)\\]");
	
	@Override
	public String parse(ActionHolder action, String start, String in)
	{
		//remove first set of brackets
		if (!in.startsWith("["))
		{
			ScriptHelper.reportScriptError(start, "Malformed Recipe, must start with \"[\"");
			return null;
		}
		
		if (!in.endsWith("]"))
		{
			ScriptHelper.reportScriptError(start, "Malformed Recipe, must end with \"]\", must be last argument");
			return null;
		}
		
		String arg = in.substring(1, in.length() - 1);
		
		//split the recipe into rows
		List<String> rows = new ArrayList<String>();
		
		Matcher matcher = pattern.matcher(arg);
		while(matcher.find()) 
		{
			rows.add(matcher.group());
		}
		
		Object recipe = null;
		if (rows.size() == 1)
		{
			IIngredient[] rowA = parseRow(start, rows.get(0));
			if (rowA == null)
			{
				ScriptHelper.reportScriptError(start, "Malformed Recipe, invalid row");
				return null;
			}
			recipe = new ObjRecipeShaped(
					rowA[0] != null ? rowA[0].getItem() : null, 
					rowA[1] != null ? rowA[1].getItem() : null, 
					rowA[2] != null ? rowA[2].getItem() : null, 
					null, null, null, null, null, null);
		}
		else if (rows.size() == 2) 
		{
			IIngredient[] rowA = parseRow(start, rows.get(0));
			IIngredient[] rowB = parseRow(start, rows.get(1));
			if (rowA == null || rowB == null)
			{
				ScriptHelper.reportScriptError(start, "Malformed Recipe, invalid row");
				return null;
			}
			recipe = new ObjRecipeShaped(
					rowA[0] != null ? rowA[0].getItem() : null, 
					rowA[1] != null ? rowA[1].getItem() : null, 
					rowA[2] != null ? rowA[2].getItem() : null, 
					rowB[0] != null ? rowB[0].getItem() : null, 
					rowB[1] != null ? rowB[1].getItem() : null, 
					rowB[2] != null ? rowB[2].getItem() : null,
					null, null, null);
		}
		else if (rows.size() == 3) 
		{
			IIngredient[] rowA = parseRow(start, rows.get(0));
			IIngredient[] rowB = parseRow(start, rows.get(1));
			IIngredient[] rowC = parseRow(start, rows.get(2));
			if (rowA == null || rowB == null || rowC == null)
			{
				ScriptHelper.reportScriptError(start, "Malformed Recipe, invalid row");
				return null;
			}
			recipe = new ObjRecipeShaped(
					rowA[0] != null ? rowA[0].getItem() : null, 
					rowA[1] != null ? rowA[1].getItem() : null, 
					rowA[2] != null ? rowA[2].getItem() : null, 
					rowB[0] != null ? rowB[0].getItem() : null, 
					rowB[1] != null ? rowB[1].getItem() : null, 
					rowB[2] != null ? rowB[2].getItem() : null, 
					rowC[0] != null ? rowC[0].getItem() : null, 
					rowC[1] != null ? rowC[1].getItem() : null, 
					rowC[2] != null ? rowC[2].getItem() : null
					);
		}
		else
		{
			//should be a shapeless recipe
			IIngredient[] all = parseShapeless(start, arg);
			if (all == null)
			{
				ScriptHelper.reportScriptError(start, "Malformed Recipe, invalid shapeless");
				return null;
			}
			recipe = new ObjRecipeShapeless(
					all[0] != null ? all[0].getItem() : null, 
					all[1] != null ? all[1].getItem() : null, 
					all[2] != null ? all[2].getItem() : null, 
					all[3] != null ? all[3].getItem() : null, 
					all[4] != null ? all[4].getItem() : null, 
					all[5] != null ? all[5].getItem() : null, 
					all[6] != null ? all[6].getItem() : null, 
					all[7] != null ? all[7].getItem() : null, 
					all[8] != null ? all[8].getItem() : null
					);
		}
		
		//add arg
		action.args.add(recipe);
		action.classes.add(recipe.getClass());
		
		//recipe must be last argument
		return "";
	}
	
	public IIngredient[] parseRow(String start, String in)
	{
		//remove first set of brackets
		if (!in.startsWith("["))
		{
			ScriptHelper.reportScriptError(start, "Malformed Recipe, row must start with \"[\"");
			return null;
		}
		
		if (!in.endsWith("]"))
		{
			ScriptHelper.reportScriptError(start, "Malformed Recipe, row must end with \"]\"");
			return null;
		}
		
		in = in.substring(1, in.length() - 1);

		IIngredient[] array = { null, null, null };
		int index = 0;
		while (in.length() > 0)
    	{
			if (index == 3)
			{
				ScriptHelper.reportScriptError(start, "Malformed Recipes, more than 3 ingredients in row");
				return null;
			}
			
			if (!in.startsWith("<"))
			{
				ScriptHelper.reportScriptError(start, "Malformed Ingredient, must start with \"<\"");
				return null;
			}
			
			if (!in.contains(">"))
			{
				ScriptHelper.reportScriptError(start, "Malformed Ingredient, must end with \">\"");
				return null;
			}
			
			//get arg
			String arg = in.substring(0, in.indexOf(">") + 1);
			
			//attempt to build ingredient
			ObjIngredient obj = new ObjIngredient(arg.substring(arg.indexOf("<") + 1, arg.indexOf(">")));
			if (obj == null || obj.ingredient == null)
			{
				ScriptHelper.reportScriptError(start, "Ingredient \"" + arg + "\" doesn't exist");
				return null;
			}
			
			array[index++] = obj.ingredient;
			
			//clean line
			in = in.substring(in.indexOf(">") + 1);
			if (in.startsWith(",")) in = in.substring(1);
    	}
		
		return array;
	}
	
	public IIngredient[] parseShapeless(String start, String in)
	{
		IIngredient[] array = { null, null, null, null, null, null, null, null, null };
		int index = 0;
		while (in.length() > 0)
    	{
			if (index == 9)
			{
				ScriptHelper.reportScriptError(start, "Malformed Recipes, more than 3 ingredients in row");
				return null;
			}
			
			if (!in.startsWith("<"))
			{
				ScriptHelper.reportScriptError(start, "Malformed Ingredient, must start with \"<\"");
				return null;
			}
			
			if (!in.contains(">"))
			{
				ScriptHelper.reportScriptError(start, "Malformed Ingredient, must end with \">\"");
				return null;
			}
			
			//get arg
			String arg = in.substring(0, in.indexOf(">") + 1);
			
			//attempt to build ingredient
			ObjIngredient obj = new ObjIngredient(arg.substring(arg.indexOf("<") + 1, arg.indexOf(">")));
			if (obj == null || obj.ingredient == null)
			{
				ScriptHelper.reportScriptError(start, "Ingredient \"" + arg + "\" doesn't exist");
				return null;
			}
			
			array[index++] = obj.ingredient;
			
			//clean line
			in = in.substring(in.indexOf(">") + 1);
			if (in.startsWith(",")) in = in.substring(1);
    	}
		
		return array;
	}
}
