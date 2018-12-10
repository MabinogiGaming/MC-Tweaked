package com.mabinogi.tweaked.script.arguments;

import com.mabinogi.tweaked.api.annotations.TweakedArgument;
import com.mabinogi.tweaked.api.arguments.ITweakedArgument;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.holders.ActionHolder;
import com.mabinogi.tweaked.script.objects.ObjIngredient;
import net.minecraft.enchantment.Enchantment;

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

		//clean line
		in = in.substring(in.indexOf(">") + 1);

		//load any ingredient modifiers
		while (in.startsWith("."))
		{
			if (in.toLowerCase().startsWith(".enchant{") && in.contains("}"))
			{
				in = parseEnchantMod(start, in, obj);
			}
			else
			{
				ScriptHelper.reportScriptError(start, "Modifier doesn't exist");
				break;
			}
		}
		
		//add arg
		action.args.add(obj.ingredient);
		action.classes.add(obj.ingredient.getClass());

		//clean
		if (in.startsWith(",")) in = in.substring(1);
		
		return in;
	}

	private String parseEnchantMod(String start, String in, ObjIngredient ingredient)
	{
		String arg = in.substring(in.indexOf("{") + 1, in.indexOf("}"));

		//split the input
		String[] args = arg.split(":");

		if (args.length == 2)
		{
			try
			{
				String enchantName = args[0];
				int level = Integer.parseInt(args[1]);

				Enchantment enchant = Enchantment.getEnchantmentByLocation(enchantName);
				if (enchant != null)
				{
					//check level is valid
					if(level < enchant.getMinLevel() || level > enchant.getMaxLevel())
					{
						ScriptHelper.reportScriptError(start, "Enchantment Modifier \"" + arg + "\" has a too low/high specified level");
						return null;
					}

					ingredient.addEnchantment(enchant, level);
				}
			}
			catch(NumberFormatException e)
			{
				ScriptHelper.reportScriptError(start, "Enchantment Modifier \"" + arg + "\" must have an integer as the second argument");
				return null;
			}
		}
		else if (args.length == 3)
		{
			try
			{
				String enchantName = args[0] + ":" + args[1];
				int level = Integer.parseInt(args[2]);

				Enchantment enchant = Enchantment.getEnchantmentByLocation(enchantName);
				if (enchant != null)
				{
					//check level is valid
					if(level < enchant.getMinLevel() || level > enchant.getMaxLevel())
					{
						ScriptHelper.reportScriptError(start, "Enchantment Modifier \"" + arg + "\" has a too low/high specified level");
						return null;
					}

					ingredient.addEnchantment(enchant, level);
				}
			}
			catch(NumberFormatException e)
			{
				ScriptHelper.reportScriptError(start, "Enchantment Modifier \"" + arg + "\" must have an integer as the second argument");
				return null;
			}
		}
		else
		{
			ScriptHelper.reportScriptError(start, "Enchantment Modifier \"" + arg + "\" can only have 2 arguments");
			return null;
		}

		//clean line
		in = in.substring(in.indexOf("}") + 1);

		return in;
	}

}
