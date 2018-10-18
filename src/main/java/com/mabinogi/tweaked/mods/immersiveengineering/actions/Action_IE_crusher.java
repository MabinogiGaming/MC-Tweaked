package com.mabinogi.tweaked.mods.immersiveengineering.actions;

import static com.mabinogi.tweaked.Tweaked.LOG;

import java.util.ArrayList;
import java.util.List;

import com.mabinogi.tweaked.api.actions.ActionAbstract;
import com.mabinogi.tweaked.api.annotations.TweakedAction;
import com.mabinogi.tweaked.script.objects.ObjAll;
import com.mabinogi.tweaked.script.objects.ObjDict;
import com.mabinogi.tweaked.script.objects.ObjStack;
import com.mabinogi.tweaked.script.objects.ObjStackList;

import blusunrize.immersiveengineering.api.crafting.CrusherRecipe;
import net.minecraft.item.ItemStack;

public class Action_IE_crusher 
{	
	public static Action_IE_Crusher_Add ADD = null;
	public static Action_IE_Crusher_Remove REMOVE = null;
	
	
	//**************************************************************************************//
	//										add												//
	//**************************************************************************************//

	@TweakedAction(value="ie.crusher.add", modid="immersiveengineering")
	public static class Action_IE_Crusher_Add extends ActionAbstract
	{
		public static List<CrusherRecipe> RECIPES = new ArrayList<>();
		
		public Action_IE_Crusher_Add()
		{
			ADD = this;
		}
		
		public void build(ObjStack output, ObjStack input, Integer energy)
		{
			RECIPES.add(new CrusherRecipe(output.getItemStack(), input.getItem(), energy));
		}
		
		public void build(ObjStack output, ObjDict input, Integer energy)
		{
			RECIPES.add(new CrusherRecipe(output.getItemStack(), input.getItem(), energy));
		}

		@Override
		protected void run()
		{
			//add recipes
			for (CrusherRecipe recipe : RECIPES)
			{
				CrusherRecipe.recipeList.add(recipe);
				
				//debug
				LOG.debug("IE : Added Crusher recipe : " + recipe);
			}
			
			//cleanup
			RECIPES = null;
		}
	}
	
	
	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//
	
	@TweakedAction(value="ie.crusher.remove", modid="immersiveengineering")
	public static class Action_IE_Crusher_Remove extends ActionAbstract
	{
		public static Boolean CLEAR = false;
		public static List<ItemStack> RECIPES = new ArrayList<>();
		
		public Action_IE_Crusher_Remove()
		{
			REMOVE = this;
		}
		
		public void build(ObjStack stack)
		{
			RECIPES.add(stack.getItemStack());
		}
		
		public void build(ObjStackList stackList)
		{
			for (ObjStack stack : stackList.list)
			{
				build(stack);
			}
		}
		
		public void build(ObjAll all)
		{
			CLEAR = true;
		}

		@Override
		protected void run()
		{
			//clear recipes
			if (CLEAR)
			{
				CrusherRecipe.recipeList.clear();
				
				//debug
				LOG.debug("IE : Removed all Crusher recipes");
			}
			else
			{
				//remove recipes
				for (ItemStack stack : RECIPES)
				{
					CrusherRecipe.removeRecipesForOutput(stack);
					
					//debug
					LOG.debug("IE : Removed Crusher recipe : " + stack);
				}
			}
			
			//cleanup
			CLEAR = null;
			RECIPES = null;
		}
	}
}
