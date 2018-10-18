package com.mabinogi.tweaked.script.loaders;

import static com.mabinogi.tweaked.Tweaked.LOG;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mabinogi.tweaked.TweakedActions;
import com.mabinogi.tweaked.TweakedAnnotations;
import com.mabinogi.tweaked.api.actions.IAction;
import com.mabinogi.tweaked.api.arguments.IArgument;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.holders.ActionHolder;

public class ActionLoader {
	
	public static boolean parseAction(String in)
    {
    	//create script
    	String start = "tweak." + in;
    	ActionHolder action = new ActionHolder();
    	
    	//parse action
    	String actionName = null;
    	if (in.contains("(") && in.endsWith(")"))
    	{
    		actionName = in.substring(0, in.indexOf("("));
    		
    		in = in.substring(in.indexOf("(") + 1, in.length() - 1);
    	}
    	else
    	{
    		ScriptHelper.reportScriptError(start, "Unable to determine method");
    		return false;
    	}
    	
    	//attempt to find action class
    	Object actionClass = TweakedAnnotations.ACTIONS.get(actionName);
    	if (actionClass == null)
    	{
    		ScriptHelper.reportScriptError(start, "Action \"" + actionName + "\" doesn't exist");
    		return false;
    	}
    	
    	//sanity check
    	if (!(actionClass instanceof IAction))
    	{
    		ScriptHelper.reportScriptError(start, "Action \"" + actionName + "\" is invalid");
    		return false;
    	}
    	
    	//apply to holder
    	action.start = start;
    	action.tweak = in;
    	action.actionClass = actionClass;
    	
    	//add action to map
    	TweakedActions.storeAction(actionName, action);
    	
    	//debug
    	LOG.debug("Stored Action : " + start);
    	return true;
    }
	
	public static void applyAction(ActionHolder action)
    {
    	String in = action.tweak;
    	
    	//parse arguments
    	while (in.length() > 0)
    	{
    		//find argument handler
    		IArgument argument = TweakedAnnotations.ARGUMENTS.get(in.substring(0, 1));
    		if (argument == null)
    		{
    			ScriptHelper.reportScriptError(action.start, "Argument token \"" + in.substring(0, 1) + "\" is not recognized");
        		return;
    		}
    		else
    		{
    			in = argument.parse(action, action.start, in);
    		}
    		
    		//check for errors, note that message should be handled by the IArgument
    		if (in == null)
    		{
    			return;
    		}
    	}
    	
    	//final sanity checks
    	if (action.actionClass == null)
    	{
    		ScriptHelper.reportScriptError(action.start, "Incomplete action");
    		return;
    	}
    	
    	//attempt to find correct method
    	try
		{
			Method method = action.actionClass.getClass().getMethod("build", action.classes.toArray(new Class<?>[action.classes.size()]));
			method.invoke(action.actionClass, action.args.toArray());
		}
    	catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			ScriptHelper.reportScriptError(action.start, "Build doesn't exist or has invalid arguments");
    		return;
		}
    }

}
