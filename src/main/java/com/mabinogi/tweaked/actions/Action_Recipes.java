package com.mabinogi.tweaked.actions;

import static com.mabinogi.tweaked.Tweaked.LOG;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mabinogi.tweaked.Tweaked;
import com.mabinogi.tweaked.TweakedController;
import com.mabinogi.tweaked.api.actions.ActionAbstract;
import com.mabinogi.tweaked.api.annotations.TweakedAction;
import com.mabinogi.tweaked.script.objects.ObjRecipe;
import com.mabinogi.tweaked.script.objects.ObjStack;
import com.mabinogi.tweaked.script.objects.ObjStackList;
import com.mabinogi.tweaked.script.objects.ObjStringList;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class Action_Recipes
{	
	public static Action_Recipes_Remove REMOVE = null;
	public static Action_Recipes_Shaped SHAPED = null;
	
	
	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//
	
	@TweakedAction("recipes.remove")
	public static class Action_Recipes_Remove extends ActionAbstract
	{
		public List<ObjStack> STACKS =  new ArrayList<>();
		public List<ResourceLocation> NAMES = new ArrayList<>();
		
		public Action_Recipes_Remove()
		{
			REMOVE = this;
		}
		
		public void build(String recipeName)
		{
			NAMES.add(new ResourceLocation(recipeName));
		}
		
		public void build(ObjStringList nameList)
		{
			for (String s : nameList.list)
			{
				build(s);
			}
		}
		
		public void build(ObjStack ingredient)
		{
			STACKS.add(ingredient);
		}
		
		public void build(ObjStackList ingredientList)
		{
			for (ObjStack ingredient : ingredientList.list)
			{
				build(ingredient);
			}
		}
		
		@Override
		protected void run()
		{
			//search registry to match recipes
			if (TweakedController.RECIPE_REGISTRY == null)
			{
				LOG.warn("Warning : Recipe Registry Missing");
				return;
			}
			
			if (!STACKS.isEmpty())
			{
				//convert stacks into recipe names
				for (Map.Entry<ResourceLocation, IRecipe> recipe : TweakedController.RECIPE_REGISTRY.getEntries())
				{
					for (ObjStack ingredient : STACKS)
					{
						if (ingredient.matches(recipe.getValue().getRecipeOutput()))
						{
							NAMES.add(recipe.getValue().getRegistryName());
							continue;
						}
					}
				}
			}
			
			if (!NAMES.isEmpty())
			{
				//remove recipes
				for (ResourceLocation recipe : NAMES)
				{
					TweakedController.RECIPE_REGISTRY.remove(recipe);
					
					//debug
					LOG.debug("Removed Recipe : " + recipe.toString());
				}
			}
			
			//cleanup
			NAMES = null;
			STACKS = null;
		}
	}
	
	
	//**************************************************************************************//
	//										shaped											//
	//**************************************************************************************//
	
	@TweakedAction("recipes.shaped")
	public static class Action_Recipes_Shaped extends ActionAbstract
	{
		public List<IRecipe> RECIPES = new ArrayList<>();
		
		public Action_Recipes_Shaped()
		{
			SHAPED = this;
		}
		
		public void build(String recipeName, ObjStack output, ObjRecipe input)
		{
			if (input.recipeArgs == null)
			{
				LOG.warn("Warning : Invalid recipe arguments for \"" + recipeName + "\"");
				return;
			}
			
			IRecipe recipe = new ShapedOreRecipe(null, output.getItemStack(), input.recipeArgs);
			if (recipe != null)
			{
				recipe.setRegistryName(new ResourceLocation(Tweaked.MODID, recipeName));
				RECIPES.add(recipe);
			}
		}
		
		@Override
		protected void run()
		{
			//search registry to match recipes
			if (TweakedController.RECIPE_REGISTRY == null)
			{
				LOG.warn("Warning : Recipe Registry Missing");
				return;
			}
			
			if (!RECIPES.isEmpty())
			{
				for (IRecipe recipe : RECIPES)
				{
					TweakedController.RECIPE_REGISTRY.register(recipe);
					
					//debug
					LOG.debug("Added Shaped Recipe : " + recipe.toString());
				}
			}

			//cleanup
			RECIPES = null;
		}
	}
	
}
