package com.mabinogi.tweaked.mods.immersiveengineering.actions;

import blusunrize.immersiveengineering.api.ApiUtils;
import blusunrize.immersiveengineering.api.crafting.BlastFurnaceRecipe;
import blusunrize.immersiveengineering.api.crafting.BlastFurnaceRecipe.BlastFurnaceFuel;
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

public class Action_IE_BlastFurnace
{	
	public static Action_IE_BlastFurnace_Add ADD = null;
	public static Action_IE_BlastFurnace_Remove REMOVE = null;
	public static Action_IE_BlastFurnace_AddFuel ADD_FUEL = null;
	public static Action_IE_BlastFurnace_RemoveFuel REMOVE_FUEL = null;
	
	
	//**************************************************************************************//
	//										add												//
	//**************************************************************************************//
	
	@TweakedAction(value="ie.blastfurnace.add", modid="immersiveengineering")
	public static class Action_IE_BlastFurnace_Add extends ActionAbstract
	{
		public List<BlastFurnaceRecipe> RECIPES = new ArrayList<>();
		
		public Action_IE_BlastFurnace_Add()
		{
			ADD = this;
		}
		
		public void build(ObjStack output, ObjStack input, Integer time)
		{
			build(output, input, time, null);
		}
		
		public void build(ObjStack output, ObjStack input, Integer time, ObjStack slag)
		{
			RECIPES.add(new BlastFurnaceRecipe(output.getItemStack(), input.getItem(), time, slag.getItemStack()));
		}
		
		public void build(ObjStack output, ObjDict input, Integer time)
		{
			build(output, input, time, null);
		}
		
		public void build(ObjStack output, ObjDict input, Integer time, ObjStack slag)
		{
			RECIPES.add(new BlastFurnaceRecipe(output.getItemStack(), input.getItem(), time, slag.getItemStack()));
		}
		
		@Override
		protected void run()
		{
			//add recipes
			for (BlastFurnaceRecipe recipe : RECIPES)
			{
				BlastFurnaceRecipe.recipeList.add(recipe);
				
				//debug
				LOG.debug("IE : Added BlastFurnace recipe : " + recipe);
			}
			
			//cleanup
			RECIPES = null;
		}
	}
	
	
	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//
	
	@TweakedAction(value="ie.blastfurnace.remove", modid="immersiveengineering")
	public static class Action_IE_BlastFurnace_Remove extends ActionAbstract
	{
		public Boolean CLEAR = false;
		public List<ItemStack> RECIPES = new ArrayList<>();
		
		public Action_IE_BlastFurnace_Remove()
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
				BlastFurnaceRecipe.recipeList.clear();
				
				//debug
				LOG.debug("IE : Removed all CokeOven recipes");
			}
			else
			{
				//remove recipes
				for (ItemStack stack : RECIPES)
				{
					BlastFurnaceRecipe.removeRecipes(stack);
					
					//debug
					LOG.debug("IE : Removed CokeOven recipe : " + stack);
				}
			}
			
			//cleanup
			CLEAR = null;
			REMOVE = null;
		}
	}
	
	
	//**************************************************************************************//
	//										add fuel										//
	//**************************************************************************************//
	
	@TweakedAction(value="ie.blastfurnace.addFuel", modid="immersiveengineering")
	public static class Action_IE_BlastFurnace_AddFuel extends ActionAbstract
	{
		public List<BlastFurnaceFuel> FUELS = new ArrayList<>();
		
		public Action_IE_BlastFurnace_AddFuel()
		{
			ADD_FUEL = this;
		}
		
		public void build(ObjStack input, Integer time)
		{
			FUELS.add(new BlastFurnaceFuel(ApiUtils.createIngredientStack(input.getItem()), time));
		}
		
		public void build(ObjDict input, Integer time)
		{
			FUELS.add(new BlastFurnaceFuel(ApiUtils.createIngredientStack(input.getItem()), time));
		}
		
		@Override
		protected void run()
		{
			//add recipes
			for (BlastFurnaceFuel fuel : FUELS)
			{
				BlastFurnaceRecipe.blastFuels.add(fuel);
				
				//debug
				LOG.debug("IE : Added BlastFurnace fuel : " + fuel);
			}
			
			//cleanup
			FUELS = null;
		}
	}
	
	
	//**************************************************************************************//
	//										remove fuel										//
	//**************************************************************************************//
	
	@TweakedAction(value="ie.blastfurnace.removeFuel", modid="immersiveengineering")
	public static class Action_IE_BlastFurnace_RemoveFuel extends ActionAbstract
	{
		public Boolean CLEAR = false;
		public List<BlastFurnaceFuel> FUELS = new ArrayList<>();
		
		public Action_IE_BlastFurnace_RemoveFuel()
		{
			REMOVE_FUEL = this;
		}
		
		public void build(ObjStack stack)
		{
			for(BlastFurnaceFuel fuel : BlastFurnaceRecipe.blastFuels)
			{
				if(fuel.input.matchesItemStack(stack.getItemStack()))
				{
					FUELS.add(fuel);
				}
			}
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
			//clear fuels
			if (CLEAR)
			{
				BlastFurnaceRecipe.blastFuels.clear();
				
				//debug
				LOG.debug("IE : Removed all BlastFurnace fuel");
			}
			else
			{
				//add recipes
				for (BlastFurnaceFuel fuel : FUELS)
				{
					BlastFurnaceRecipe.blastFuels.remove(fuel);
					
					//debug
					LOG.debug("IE : Removed BlastFurnace fuel : " + fuel);
				}
			}
			
			//cleanup
			CLEAR = null;
			FUELS = null;
		}
	}
	
}
