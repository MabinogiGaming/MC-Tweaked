package com.mabinogi.tweaked.mods.jei.actions;

import java.util.ArrayList;
import java.util.List;

import com.mabinogi.tweaked.actions.iface.IAction;
import com.mabinogi.tweaked.annotations.TweakedAction;
import com.mabinogi.tweaked.mods.jei.Plugin_JEI;
import com.mabinogi.tweaked.script.holders.ActionHolder;
import com.mabinogi.tweaked.script.loaders.ActionLoader;
import com.mabinogi.tweaked.script.objects.ObjStack;
import com.mabinogi.tweaked.script.objects.ObjStackList;

import net.minecraft.item.ItemStack;

@TweakedAction(value="jei", modid="jei")
public class Action_JEI implements IAction {
	
	public static final String METHOD_ADD = "add";
	public static final String METHOD_HIDE = "hide";
	
	public static List<ActionHolder> ACTIONS_ADD = new ArrayList<>();
	public static List<ActionHolder> ACTIONS_HIDE = new ArrayList<>();

	@Override
	public boolean store(String methodName, ActionHolder action)
	{
		switch (methodName)
		{
			case METHOD_ADD:
			{
				ACTIONS_ADD.add(action);
				return true;
			}
			case METHOD_HIDE:
			{
				ACTIONS_HIDE.add(action);
				return true;
			}
			default:
				return false;
		}
	}
	
	//**************************************************************************************//
	//										ADD												//
	//**************************************************************************************//
	
	public static List<ItemStack> STACKS_ADD = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	public static void addApply()
	{
		//apply scripts
		for (ActionHolder script : ACTIONS_ADD)
		{
			ActionLoader.applyAction(METHOD_ADD, script);
		}
		
		//add stacks
		if (!STACKS_ADD.isEmpty())
		{
			Plugin_JEI.itemRegistry.addIngredientsAtRuntime(ItemStack.class, STACKS_ADD);
		}
		
		//clean up
		ACTIONS_ADD = null;
		STACKS_ADD = null;
	}
	
	public void add(ObjStack ingredient)
	{
		STACKS_ADD.add(ingredient.getItemStack());
	}
	
	public void add(ObjStackList ingredientList)
	{
		for (ObjStack ingredient : ingredientList.list)
		{
			add(ingredient);
		}
	}
	
	//**************************************************************************************//
	//										HIDE											//
	//**************************************************************************************//
	
	public static List<ItemStack> STACKS_HIDE = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	public static void hideApply()
	{
		//apply scripts
		for (ActionHolder script : ACTIONS_HIDE)
		{
			ActionLoader.applyAction(METHOD_HIDE, script);
		}
		
		//hide stacks
		if (!STACKS_HIDE.isEmpty())
		{
			Plugin_JEI.itemRegistry.removeIngredientsAtRuntime(ItemStack.class, STACKS_HIDE);
		}
		
		//clean up
		ACTIONS_HIDE = null;
		STACKS_HIDE = null;
	}
	
	public void hide(ObjStack ingredient)
	{
		STACKS_HIDE.add(ingredient.getItemStack());
	}
	
	public void hide(ObjStackList ingredientList)
	{
		for (ObjStack ingredient : ingredientList.list)
		{
			hide(ingredient);
		}
	}

}
