package com.mabinogi.tweaked.script.arguments;

import com.mabinogi.tweaked.api.annotations.TweakedArgument;
import com.mabinogi.tweaked.api.arguments.ITweakedArgument;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.holders.ActionHolder;

@TweakedArgument("\"")
public class ArgString implements ITweakedArgument
{
	@Override
	public String parse(ActionHolder action, String start, String in)
	{
		//remove leading speechmark
		in = in.substring(1);
		
		//find next speechmark
		if (!in.contains("\""))
		{
			ScriptHelper.reportScriptError(start, "Malformed String, must end with \"\"\"");
			return null;
		}
		
		//get arg
		String arg = in.substring(0, in.indexOf("\""));
		
		//add arg
		action.args.add(arg);
		action.classes.add(String.class);
		
		//clean line
		in = in.substring(in.indexOf("\"") + 1);
		if (in.startsWith(",")) in = in.substring(1);
		
		return in;
	}
}
