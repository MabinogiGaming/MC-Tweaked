package com.mabinogi.tweaked.mods.vanilla.actions;

import com.mabinogi.tweaked.Tweaked;
import com.mabinogi.tweaked.api.actions.ActionAbstract;
import com.mabinogi.tweaked.api.annotations.TweakedAction;
import com.mabinogi.tweaked.controllers.TweakedRecipes;
import com.mabinogi.tweaked.mods.vanilla.Tweaked_Vanilla;
import com.mabinogi.tweaked.script.objects.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mabinogi.tweaked.Tweaked.LOG;

public class Action_Vanilla_Recipes
{	
	public static Action_Recipes_Remove REMOVE = null;
	public static Action_Recipes_Shaped SHAPED = null;
	public static Action_Recipes_Shapeless SHAPELESS = null;
	
	
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
			if (Tweaked_Vanilla.RECIPE_REGISTRY == null)
			{
				LOG.warn("Warning : Recipe Registry Missing");
				return;
			}
			
			if (!STACKS.isEmpty())
			{
				//convert stacks into recipe names
				for (Map.Entry<ResourceLocation, IRecipe> recipe : Tweaked_Vanilla.RECIPE_REGISTRY.getEntries())
				{
					for (ObjStack ingredient : STACKS)
					{
						if (ingredient.matches(recipe.getValue().getRecipeOutput()))
						{
							NAMES.add(recipe.getValue().getRegistryName());
							break;
						}
					}
				}
			}
			
			if (!NAMES.isEmpty())
			{
				//remove recipes
				for (ResourceLocation recipe : NAMES)
				{
					Tweaked_Vanilla.RECIPE_REGISTRY.remove(recipe);

					//schedule the recipe to be replaced by a dummy if required
					TweakedRecipes.REMOVED_RECIPES.add(recipe);
					
					//debug
					LOG.debug("Removed Recipe : " + recipe);
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
		
		public void build(String recipeName, ObjStack output, ObjRecipeShaped input)
		{
			if (input.recipeArgs == null)
			{
				LOG.warn("Warning : Invalid recipe arguments for \"" + recipeName + "\"");
				return;
			}

			//create a new shaped
			IRecipe recipe = new ShapedOreRecipe(null, output.getItemStack(), input.recipeArgs);

			//set container to Tweaked so that recipes are registered to it rather than Tweaked_Vanilla
			TweakedRecipes.setTweakedContainer();

			//add recipe
			recipe.setRegistryName(new ResourceLocation(Tweaked.MODID, recipeName));
			RECIPES.add(recipe);

			//restore container
			TweakedRecipes.restoreContainer();
		}
		
		@Override
		protected void run()
		{
			//search registry to match recipes
			if (Tweaked_Vanilla.RECIPE_REGISTRY == null)
			{
				LOG.warn("Warning : Recipe Registry Missing");
				return;
			}
			
			if (!RECIPES.isEmpty())
			{
				for (IRecipe recipe : RECIPES)
				{
					Tweaked_Vanilla.RECIPE_REGISTRY.register(recipe);
					
					//debug
					LOG.debug("Added Shaped Recipe : " + recipe.getRecipeOutput());
				}
			}

			//cleanup
			RECIPES = null;
		}
	}
		
		
	//**************************************************************************************//
	//										shapeless										//
	//**************************************************************************************//
	
	@TweakedAction("recipes.shapeless")
	public static class Action_Recipes_Shapeless extends ActionAbstract
	{
		public List<IRecipe> RECIPES = new ArrayList<>();
		
		public Action_Recipes_Shapeless()
		{
			SHAPELESS = this;
		}
		
		public void build(String recipeName, ObjStack output, ObjRecipeShapeless input)
		{
			if (input.recipeArgs == null)
			{
				LOG.warn("Warning : Invalid recipe arguments for \"" + recipeName + "\"");
				return;
			}

			//create a new shapeless recipe
			IRecipe recipe = new ShapelessOreRecipe(null, output.getItemStack(), input.recipeArgs);

			//set container to Tweaked so that recipes are registered to it rather than Tweaked_Vanilla
			TweakedRecipes.setTweakedContainer();

			//add recipe
			recipe.setRegistryName(new ResourceLocation(Tweaked.MODID, recipeName));
			RECIPES.add(recipe);

			//restore container
			TweakedRecipes.restoreContainer();
		}
		
		@Override
		protected void run()
		{
			//search registry to match recipes
			if (Tweaked_Vanilla.RECIPE_REGISTRY == null)
			{
				LOG.warn("Warning : Recipe Registry Missing");
				return;
			}
			
			if (!RECIPES.isEmpty())
			{
				for (IRecipe recipe : RECIPES)
				{
					Tweaked_Vanilla.RECIPE_REGISTRY.register(recipe);
					
					//debug
					LOG.debug("Added Shapeless Recipe : " + recipe.getRecipeOutput());
				}
			}

			//cleanup
			RECIPES = null;
		}
	}
}
