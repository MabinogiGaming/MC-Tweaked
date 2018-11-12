package com.mabinogi.tweaked.mods.immersiveengineering.actions;

import blusunrize.immersiveengineering.api.crafting.AlloyRecipe;
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

@SuppressWarnings("unused")
public class Action_IE_Alloy
{	
	public static Action_IE_Alloy_Add ADD = null;
	public static Action_IE_Alloy_Remove REMOVE = null;
	
	
	//**************************************************************************************//
	//										add												//
	//**************************************************************************************//
	
	@TweakedAction(value="ie.alloy.add", modid="immersiveengineering")
	public static class Action_IE_Alloy_Add extends ActionAbstract
	{
		public List<AlloyRecipe> RECIPES = new ArrayList<>();
		
		public Action_IE_Alloy_Add()
		{
			ADD = this;
		}

		public void build(ObjStack output, ObjStack inputA, ObjStack inputB, Integer time)
		{
			RECIPES.add(new AlloyRecipe(output.getItemStack(), inputA.getItem(), inputB.getItem(), time));
		}

		public void build(ObjStack output, ObjStack inputA, ObjDict inputB, Integer time)
		{
			RECIPES.add(new AlloyRecipe(output.getItemStack(), inputA.getItem(), inputB.getItem(), time));
		}
		
		public void build(ObjStack output, ObjDict inputA, ObjDict inputB, Integer time)
		{
			RECIPES.add(new AlloyRecipe(output.getItemStack(), inputA.getItem(), inputB.getItem(), time));
		}
		
		@Override
		protected void run()
		{
			//add recipes
			for (AlloyRecipe recipe : RECIPES)
			{
				AlloyRecipe.recipeList.add(recipe);
				
				//debug
				LOG.debug("IE : Added Alloy recipe : " + recipe.output);
			}
			
			//cleanup
			RECIPES = null;
		}
	}
	
	
	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//
	
	@TweakedAction(value="ie.alloy.remove", modid="immersiveengineering")
	public static class Action_IE_Alloy_Remove extends ActionAbstract
	{
		public Boolean CLEAR = false;
		public List<ItemStack> RECIPES = new ArrayList<>();
		
		public Action_IE_Alloy_Remove()
		{
			REMOVE = this;
		}
		
		public void build(ObjStack stack)
		{
			RECIPES.add(stack.getItemStack());
		}
		
		public void build(ObjStackList stackList)
		{
			for (ObjStack stack : stackList.getList())
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
				AlloyRecipe.recipeList.clear();
				
				//debug
				LOG.debug("IE : Removed all Alloy recipes");
			}
			else
			{
				//remove recipes
				for (ItemStack stack : RECIPES)
				{
					AlloyRecipe.removeRecipes(stack);
					
					//debug
					LOG.debug("IE : Removed Alloy recipe : " + stack);
				}
			}
			
			//cleanup
			CLEAR = null;
			REMOVE = null;
		}
	}
	
}
