package com.mabinogi.tweaked.script.arguments;

import com.mabinogi.tweaked.api.annotations.TweakedArgument;
import com.mabinogi.tweaked.api.arguments.ITweakedArgument;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.holders.ActionHolder;

@TweakedArgument({"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"})
public class ArgNumber implements ITweakedArgument
{
	@Override
	public String parse(ActionHolder action, String start, String in)
	{
		String arg = null;
		if (in.contains(","))
		{
			arg = in.substring(0, in.indexOf(",")).trim();
			
			in = in.substring(in.indexOf(",") + 1, in.length());
		}
		else if (in.contains(")"))
		{
			arg = in.substring(0, in.indexOf(")")).trim();
			
			in = in.substring(in.indexOf(")") + 1, in.length());
		}
		else
		{
			arg = in;
			
			in = "";
		}
		
		try
		{
			if (arg.contains("."))
			{
				//assume float
				Float f = Float.parseFloat(arg);
				
				action.args.add(f);
				action.classes.add(Float.class);
			}
			else
			{
				//assume integer
				Integer i = Integer.parseInt(arg);
				
				action.args.add(i);
				action.classes.add(Integer.class);
			}
		}
		catch(NumberFormatException e)
		{
			ScriptHelper.reportScriptError(start, "Malformed Number, format exception");
			return null;
		}

		return in;
	}
}
