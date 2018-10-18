package com.mabinogi.tweaked.script.arguments;

import com.mabinogi.tweaked.api.annotations.TweakedArgument;
import com.mabinogi.tweaked.api.arguments.IArgument;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.holders.ActionHolder;
import com.mabinogi.tweaked.script.holders.VariableHolder;
import com.mabinogi.tweaked.script.loaders.VariableLoader;

@TweakedArgument("$")
public class ArgVariable implements IArgument {

	@Override
	public String parse(ActionHolder action, String start, String in)
	{
		String arg = null;
		if (in.contains(","))
		{
			arg = in.substring(0, in.indexOf(","));
		}
		else
		{
			arg = in;
		}
		
		if (arg == null)
		{
			ScriptHelper.reportScriptError(start, "Malformed Variable");
			return null;
		} 
		
		//attempt to find variable
		VariableHolder holder = VariableLoader.VARS.get(arg.substring(1));
		if (holder == null)
		{
			ScriptHelper.reportScriptError(start, "Variable \"" + arg + "\" doesn't exist");
			return null;
		}
		
		//initialize the variable if required
		if (!holder.init)
		{
			VariableLoader.applyVariable(holder);
			
			//if still not initialized then something has failed
			if (!holder.init)
			{
				ScriptHelper.reportScriptError(start, "Variable \"" + arg + "\" is failing to initialize");
				return null;
			}
		}
		
		//add args
		action.args.add(holder.value);
		action.classes.add(holder.clazz);
		
		//clean line
		in = in.substring(in.indexOf(arg) + arg.length());
		if (in.startsWith(",")) in = in.substring(1);
		
		return in;
	}

}
