package com.mabinogi.tweaked.actions;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mabinogi.tweaked.Tweaked;
import com.mabinogi.tweaked.actions.iface.IAction;
import com.mabinogi.tweaked.annotations.TweakedAction;
import com.mabinogi.tweaked.script.holders.ActionHolder;
import com.mabinogi.tweaked.script.loaders.ActionLoader;
import com.mabinogi.tweaked.script.objects.ObjStack;

import net.minecraft.util.text.translation.LanguageMap;

@TweakedAction("lang")
public class ActionLang implements IAction {
	
	public static final String METHOD_TRANSLATE = "setName";
	
	public static final List<ActionHolder> ACTIONS_TRANSLATE = new ArrayList<>();
	
	public boolean store(String methodName, ActionHolder action)
	{
		switch (methodName)
		{
			case METHOD_TRANSLATE:
			{
				ACTIONS_TRANSLATE.add(action);
				return true;
			}
			default:
				return false;
		}
	}
	
	//**************************************************************************************//
	//										SETNAME											//
	//**************************************************************************************//
	
	private static Map<String, String> setNames = new HashMap<>();
	
	public static void setNameApply()
	{
		//apply scripts
		for (ActionHolder script : ACTIONS_TRANSLATE)
		{
			ActionLoader.applyAction(METHOD_TRANSLATE, script);
		}
		
		if (!setNames.isEmpty())
		{
			for (Entry<String, String> translation : setNames.entrySet())
			{
				//check translation for dodgy formatting
				String value = translation.getValue().replace("\\\"", "\"");
				
				//inject into language map
				try
				{
					LanguageMap.inject(new ByteArrayInputStream((translation.getKey() + "=" + value).getBytes("utf-8")));
				} 
				catch (UnsupportedEncodingException e)
				{
					Tweaked.LOG.warn("Warning : Error translating \"" + translation.getKey() + "\"");
				}
			}
		}
		
		//clean up
		ACTIONS_TRANSLATE.clear();
		setNames.clear();
	}
	
	public void setName(ObjStack stack, String translation)
	{
		setNames.put(stack.getItemStack().getUnlocalizedName() + ".name", translation);
	}

}
