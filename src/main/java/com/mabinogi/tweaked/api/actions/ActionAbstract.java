package com.mabinogi.tweaked.api.actions;

import com.mabinogi.tweaked.TweakedActions;
import com.mabinogi.tweaked.api.annotations.TweakedAction;
import com.mabinogi.tweaked.script.holders.ActionHolder;
import com.mabinogi.tweaked.script.loaders.ActionLoader;

public abstract class ActionAbstract implements IAction
{
	protected abstract void run();
	
	public void apply()
	{
		//get the classname from the annotation
		String actionName = getClass().getAnnotation(TweakedAction.class).value();
		
		//apply actions in the action loader, which should cause this action to build
		for (ActionHolder action : TweakedActions.getActions(actionName))
		{
			ActionLoader.applyAction(action);
		}
		
		//run the action, handled by the action itself
		run();
		
		//cleanup the action
		TweakedActions.clearActions(actionName);
	}
}
