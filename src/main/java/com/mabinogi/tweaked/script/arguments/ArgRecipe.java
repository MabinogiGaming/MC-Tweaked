package com.mabinogi.tweaked.script.arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mabinogi.tweaked.api.annotations.TweakedArgument;
import com.mabinogi.tweaked.api.arguments.IArgument;
import com.mabinogi.tweaked.api.objects.IIngredient;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.builders.IngredientBuilder;
import com.mabinogi.tweaked.script.holders.ActionHolder;
import com.mabinogi.tweaked.script.objects.ObjRecipe;

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
		
		ObjRecipe recipe = null;
		if (rows.size() == 1)
		{
			IIngredient[] rowA = parseRow(start, rows.get(0));
			if (rowA == null)
			{
				ScriptHelper.reportScriptError(start, "Malformed Recipe, invalid row");
				return null;
			}
			recipe = new ObjRecipe(rowA[0].getItem(), rowA[1].getItem(), rowA[2].getItem(), null, null, null, null, null, null);
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
			recipe = new ObjRecipe(rowA[0].getItem(), rowA[1].getItem(), rowA[2].getItem(), rowB[0].getItem(), rowB[1].getItem(), rowB[2].getItem(), null, null, null);
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
			recipe = new ObjRecipe(rowA[0].getItem(), rowA[1].getItem(), rowA[2].getItem(), rowB[0].getItem(), rowB[1].getItem(), rowB[2].getItem(), rowC[0].getItem(), rowC[1].getItem(), rowC[2].getItem());
		}
		else
		{
			ScriptHelper.reportScriptError(start, "Malformed Recipe, invalid row count of \"" + rows.size() + "\"");
			return null;
		}
		
		//add arg
		action.args.add(recipe);
		action.classes.add(ObjRecipe.class);
		
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
			IIngredient ingredient = IngredientBuilder.build(arg.substring(arg.indexOf("<") + 1, arg.indexOf(">")));
			if (ingredient == null)
			{
				ScriptHelper.reportScriptError(start, "Ingredient \"" + arg + "\" doesn't exist");
				return null;
			}
			
			array[index++] = ingredient;
			
			//clean line
			in = in.substring(in.indexOf(">") + 1);
			if (in.startsWith(",")) in = in.substring(1);
    	}
		
		return array;
	}
}
