package com.mabinogi.tweaked.script.loaders;

import static com.mabinogi.tweaked.Tweaked.LOG;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mabinogi.tweaked.actions.iface.IAction;
import com.mabinogi.tweaked.annotations.Annotations;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.arguments.iface.IArgument;
import com.mabinogi.tweaked.script.holders.ActionHolder;

public class ActionLoader {
	
	public static boolean parseAction(String in)
    {
    	//create script
    	String start = "tweak." + in;
    	ActionHolder action = new ActionHolder();
    	
    	//parse class
    	String className = null;
    	if (in.contains("#"))
    	{
    		className = in.substring(0, in.indexOf("#"));
    		
    		in = in.substring(in.indexOf("#") + 1);
    	}
    	else
    	{
    		ScriptHelper.reportScriptError(start, "Unable to determine action");
    		return false;
    	}
    	
    	//parse method
    	String methodName = null;
    	if (in.contains("(") && in.endsWith(")"))
    	{
    		methodName = in.substring(0, in.indexOf("("));
    		
    		in = in.substring(in.indexOf("(") + 1, in.length() - 1);
    	}
    	else
    	{
    		ScriptHelper.reportScriptError(start, "Unable to determine method");
    		return false;
    	}
    	
    	//attempt to find action class
    	Object actionClass = Annotations.ACTIONS.get(className);
    	if (actionClass == null)
    	{
    		ScriptHelper.reportScriptError(start, "Action \"" + className + "\" doesn't exist");
    		return false;
    	}
    	
    	//sanity check
    	if (!(actionClass instanceof IAction))
    	{
    		ScriptHelper.reportScriptError(start, "Action \"" + className + "\" is invalid");
    		return false;
    	}
    	
    	//apply to holder
    	action.start = start;
    	action.tweak = in;
    	action.actionClass = actionClass;
    	
    	//add action to handler
    	if (!((IAction) actionClass).store(methodName, action))
    	{
    		ScriptHelper.reportScriptError(start, "Method \"" + methodName + "\" doesn't exist");
    		return false;
    	}
    	
    	//debug
    	LOG.debug("Stored Action : " + start);
    	return true;
    }
	
	public static void applyAction(String methodName, ActionHolder action)
    {
    	String in = action.tweak;
    	
    	//parse arguments
    	while (in.length() > 0)
    	{
    		//find argument handler
    		IArgument argument = Annotations.ARGUMENTS.get(in.substring(0, 1));
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
    	if (action.actionClass == null || methodName == null)
    	{
    		ScriptHelper.reportScriptError(action.start, "Incomplete action");
    		return;
    	}
    	
    	//attempt to find correct method
    	try
		{
			Method method = action.actionClass.getClass().getMethod(methodName, action.classes.toArray(new Class<?>[action.classes.size()]));
			method.invoke(action.actionClass, action.args.toArray());
		}
    	catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			ScriptHelper.reportScriptError(action.start, "Method \"" + methodName + "\" doesn't exist or has invalid arguments");
    		return;
		}
    }

}
