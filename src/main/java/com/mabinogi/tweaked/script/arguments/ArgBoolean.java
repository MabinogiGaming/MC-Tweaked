package com.mabinogi.tweaked.script.arguments;

import com.mabinogi.tweaked.api.annotations.TweakedArgument;
import com.mabinogi.tweaked.api.arguments.ITweakedArgument;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.holders.ActionHolder;

@TweakedArgument({"f", "t"})
public class ArgBoolean implements ITweakedArgument
{
	@Override
	public String parse(ActionHolder action, String start, String in)
	{
		String arg = null;
		if (in.contains(","))
		{
			arg = in.substring(0, in.indexOf(",")).trim();
			
			in = in.substring(in.indexOf(",") + 1);
		}
		else if (in.contains(")"))
		{
			arg = in.substring(0, in.indexOf(")")).trim();
			
			in = in.substring(in.indexOf(")") + 1);
		}
		else
		{
			arg = in;
			
			in = "";
		}

		if (arg.equals("true"))
		{
			action.args.add(true);
			action.classes.add(Boolean.class);
		}
		else if (arg.equals("false"))
		{
			action.args.add(false);
			action.classes.add(Boolean.class);
		}
		else
		{
			ScriptHelper.reportScriptError(start, "Malformed Boolean, must be 'true' or 'false'");
			return null;
		}

		return in;
	}
}
