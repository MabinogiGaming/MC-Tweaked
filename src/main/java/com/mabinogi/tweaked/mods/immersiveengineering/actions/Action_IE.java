package com.mabinogi.tweaked.mods.immersiveengineering.actions;

import java.util.ArrayList;
import java.util.List;

import com.mabinogi.tweaked.actions.iface.IAction;
import com.mabinogi.tweaked.annotations.TweakedAction;
import com.mabinogi.tweaked.mods.immersiveengineering.events.Events_IE;
import com.mabinogi.tweaked.script.holders.ActionHolder;
import com.mabinogi.tweaked.script.loaders.ActionLoader;
import com.mabinogi.tweaked.script.objects.ObjAll;
import com.mabinogi.tweaked.script.objects.ObjStringList;

@TweakedAction(value="ie", modid="immersiveengineering")
public class Action_IE implements IAction
{
	public static final String METHOD_DISABLE = "disableMultiblock";
	
	public static List<ActionHolder> ACTIONS_DISABLE = new ArrayList<>();
	
	@Override
	public boolean store(String methodName, ActionHolder action)
	{
		switch (methodName)
		{
			case METHOD_DISABLE:
			{
				ACTIONS_DISABLE.add(action);
				return true;
			}
			default:
				return false;
		}
	}
	
	//**************************************************************************************//
	//										DISABLE											//
	//**************************************************************************************//
	
	public static void applyDisableMultiblock()
	{
		//apply scripts
		for (ActionHolder script : ACTIONS_DISABLE)
		{
			ActionLoader.applyAction(METHOD_DISABLE, script);
		}
		
		//clean up
		ACTIONS_DISABLE = null;
	}
	
	public void disableMultiblock(String name)
	{
		Events_IE.MULTIBLOCK_BLACKLIST.add(name);
	}
	
	public void disableMultiblock(ObjStringList names)
	{
		for (String name : names.list)
		{
			disableMultiblock(name);
		}
	}
	
	public void disableMultiblock(ObjAll all)
	{
		Events_IE.MULTIBLOCK_BLACKLIST_ALL = true;
	}
}


