package com.mabinogi.tweaked.mods.immersiveengineering.actions;

import blusunrize.immersiveengineering.api.crafting.CokeOvenRecipe;
import com.mabinogi.tweaked.api.actions.ActionAbstract;
import com.mabinogi.tweaked.api.annotations.TweakedAction;
import com.mabinogi.tweaked.script.objects.ObjAll;
import com.mabinogi.tweaked.script.objects.ObjDict;
import com.mabinogi.tweaked.script.objects.ObjStack;
import com.mabinogi.tweaked.script.objects.ObjStackList;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.LOG;

public class Action_IE_CokeOven
{	
	public static Action_IE_CokeOven_Add ADD = null;
	public static Action_IE_CokeOven_Remove REMOVE = null;
	
	
	//**************************************************************************************//
	//										add												//
	//**************************************************************************************//
	
	@TweakedAction(value="ie.cokeoven.add", modid="immersiveengineering")
	public static class Action_IE_CokeOven_Add extends ActionAbstract
	{
		public List<CokeOvenRecipe> RECIPES = new ArrayList<>();
		
		public Action_IE_CokeOven_Add()
		{
			ADD = this;
		}
		
		public void build(ObjStack output, ObjStack input, Integer time, Integer creosote)
		{
			RECIPES.add(new CokeOvenRecipe(output.getItemStack(), input.getItem(), time, creosote));
		}
		
		public void build(ObjStack output, ObjDict input, Integer time, Integer creosote)
		{
			RECIPES.add(new CokeOvenRecipe(output.getItemStack(), input.getItem(), time, creosote));
		}
		
		@Override
		protected void run()
		{
			//add recipes
			for (CokeOvenRecipe recipe : RECIPES)
			{
				CokeOvenRecipe.recipeList.add(recipe);
				
				//debug
				LOG.debug("IE : Added CokeOven recipe : " + recipe.output);
			}
			
			//cleanup
			RECIPES = null;
		}
	}
	
	
	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//
	
	@TweakedAction(value="ie.cokeoven.remove", modid="immersiveengineering")
	public static class Action_IE_CokeOven_Remove extends ActionAbstract
	{
		public Boolean CLEAR = false;
		public List<ItemStack> RECIPES = new ArrayList<>();
		
		public Action_IE_CokeOven_Remove()
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

		@SuppressWarnings("unused")
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
				CokeOvenRecipe.recipeList.clear();
				
				//debug
				LOG.debug("IE : Removed all CokeOven recipes");
			}
			else
			{
				//remove recipes
				for (ItemStack stack : RECIPES)
				{
					CokeOvenRecipe.removeRecipes(stack);
					
					//debug
					LOG.debug("IE : Removed CokeOven recipe : " + stack);
				}
			}
			
			//cleanup
			CLEAR = null;
			REMOVE = null;
		}
	}
	
}
