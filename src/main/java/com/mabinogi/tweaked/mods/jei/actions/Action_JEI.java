package com.mabinogi.tweaked.mods.jei.actions;

import com.mabinogi.tweaked.api.actions.ActionAbstract;
import com.mabinogi.tweaked.api.annotations.TweakedAction;
import com.mabinogi.tweaked.mods.jei.Plugin_JEI;
import com.mabinogi.tweaked.mods.jei.Tweaked_JEI;
import com.mabinogi.tweaked.script.objects.ObjStack;
import com.mabinogi.tweaked.script.objects.ObjStackList;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.LOG;


public class Action_JEI
{
	public static Action_JEI_Add ADD = null;
	public static Action_JEI_Hide HIDE = null;
	
	
	//**************************************************************************************//
	//										add												//
	//**************************************************************************************//
	
	@TweakedAction(value="jei.add", modid="jei")
	public static class Action_JEI_Add extends ActionAbstract
	{
		public List<ItemStack> STACKS = new ArrayList<>();
		
		public Action_JEI_Add()
		{
			ADD = this;
		}
		
		public void build(ObjStack stack)
		{
			STACKS.add(stack.getItemStack());
		}
		
		public void build(ObjStackList stacks)
		{
			for (ObjStack stack : stacks.list)
			{
				build(stack);
			}
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void run()
		{
			//add stacks
			if (!STACKS.isEmpty())
			{
				Tweaked_JEI.proxy.addIngredients(STACKS);
				
				//debug
				LOG.debug("JEI : Added " + STACKS.size() + " items");
			}
			
			//cleanup
			STACKS = null;
		}
	}
	
	
	//**************************************************************************************//
	//										hide											//
	//**************************************************************************************//
	
	@TweakedAction(value="jei.hide", modid="jei")
	public static class Action_JEI_Hide extends ActionAbstract
	{
		public List<ItemStack> STACKS = new ArrayList<>();
		
		public Action_JEI_Hide()
		{
			HIDE = this;
		}
		
		public void build(ObjStack stack)
		{
			STACKS.add(stack.getItemStack());
		}
		
		public void build(ObjStackList stacks)
		{
			for (ObjStack stack : stacks.list)
			{
				build(stack);
			}
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void run()
		{
			//hide stacks
			if (!STACKS.isEmpty())
			{
				Tweaked_JEI.proxy.hideIngredients(STACKS);
				
				//debug
				LOG.debug("JEI : Hidden " + STACKS.size() + " items");
			}
			
			//cleanup
			STACKS = null;
		}
	}
}
