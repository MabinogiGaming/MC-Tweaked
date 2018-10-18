package com.mabinogi.tweaked.script.arguments;

import com.mabinogi.tweaked.api.annotations.TweakedArgument;
import com.mabinogi.tweaked.api.arguments.IArgument;
import com.mabinogi.tweaked.script.holders.ActionHolder;
import com.mabinogi.tweaked.script.objects.ObjAll;

@TweakedArgument("*")
public class ArgAll implements IArgument
{
	@Override
	public String parse(ActionHolder action, String start, String in)
	{
		//remove asterisk, we literally do nothing else
		in = in.substring(1);
		
		//add arg
		action.args.add(new ObjAll());
		action.classes.add(ObjAll.class);
		
		//clean line
		if (in.startsWith(",")) in = in.substring(1);
		
		return in;
	}
}
