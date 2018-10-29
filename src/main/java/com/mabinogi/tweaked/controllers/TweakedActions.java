package com.mabinogi.tweaked.controllers;

import com.mabinogi.tweaked.script.holders.ActionHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweakedActions
{
	private static Map<String, List<ActionHolder>> ACTION_MAP = new HashMap<>();
	
	public static void storeAction(String actionName, ActionHolder action)
	{
		if (!ACTION_MAP.containsKey(actionName))
		{
			ACTION_MAP.put(actionName, new ArrayList<>());
		}
		ACTION_MAP.get(actionName).add(action);
	}
	
	public static List<ActionHolder> getActions(String actionName)
	{
		if (ACTION_MAP.containsKey(actionName))
		{
			return ACTION_MAP.get(actionName);
		}
		return new ArrayList<>();
	}
	
	public static void clearActions(String actionName)
	{
		if (ACTION_MAP.containsKey(actionName))
		{
			ACTION_MAP.remove(actionName);
		}
	}
	
}
