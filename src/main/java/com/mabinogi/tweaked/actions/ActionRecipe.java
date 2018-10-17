package com.mabinogi.tweaked.actions;

import static com.mabinogi.tweaked.Tweaked.LOG;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mabinogi.tweaked.Tweaked;
import com.mabinogi.tweaked.TweakedController;
import com.mabinogi.tweaked.actions.iface.IAction;
import com.mabinogi.tweaked.annotations.TweakedAction;
import com.mabinogi.tweaked.script.holders.ActionHolder;
import com.mabinogi.tweaked.script.loaders.ActionLoader;
import com.mabinogi.tweaked.script.objects.ObjRecipe;
import com.mabinogi.tweaked.script.objects.ObjStack;
import com.mabinogi.tweaked.script.objects.ObjStackList;
import com.mabinogi.tweaked.script.objects.ObjStringList;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;

@TweakedAction("recipes")
public class ActionRecipe implements IAction {
	
	public static final String METHOD_REMOVE = "remove";
	public static final String METHOD_ADD_SHAPED = "shaped";
	
	public static final List<ActionHolder> ACTIONS_REMOVE = new ArrayList<>();
	public static final List<ActionHolder> ACTIONS_ADD_SHAPED = new ArrayList<>();
	
	public boolean store(String methodName, ActionHolder action)
	{
		switch (methodName)
		{
			case METHOD_REMOVE:
			{
				ACTIONS_REMOVE.add(action);
				return true;
			}
			case METHOD_ADD_SHAPED:
			{
				ACTIONS_ADD_SHAPED.add(action);
				return true;
			}
			default:
				return false;
		}
	}
	
	//**************************************************************************************//
	//										REMOVE											//
	//**************************************************************************************//
	
	public static List<ObjStack> removeIngredients =  new ArrayList<>();
	
	public static List<ResourceLocation> removeNames = new ArrayList<>();
	
	public void remove(String recipeName)
	{
		removeNames.add(new ResourceLocation(recipeName));
	}
	
	public void remove(ObjStringList nameList)
	{
		for (String s : nameList.list)
		{
			remove(s);
		}
	}
	
	public void remove(ObjStack ingredient)
	{
		removeIngredients.add(ingredient);
	}
	
	public void remove(ObjStackList ingredientList)
	{
		for (ObjStack ingredient : ingredientList.list)
		{
			remove(ingredient);
		}
	}
	
	public static void removeApply()
	{
		//apply scripts
		for (ActionHolder script : ACTIONS_REMOVE)
		{
			ActionLoader.applyAction(METHOD_REMOVE, script);
		}
		
		//search registry to match recipes
		if (TweakedController.RECIPE_REGISTRY == null)
		{
			LOG.warn("Warning : Recipe Registry Missing");
			return;
		}
		
		if (!removeIngredients.isEmpty())
		{
			//convert stacks into recipe names
			for (Map.Entry<ResourceLocation, IRecipe> recipe : TweakedController.RECIPE_REGISTRY.getEntries())
			{
				for (ObjStack ingredient : removeIngredients)
				{
					if (ingredient.matches(recipe.getValue().getRecipeOutput()))
					{
						removeNames.add(recipe.getValue().getRegistryName());
						continue;
					}
				}
			}
		}
		
		if (!removeNames.isEmpty())
		{
			//remove recipes
			for (ResourceLocation recipe : removeNames)
			{
				TweakedController.RECIPE_REGISTRY.remove(recipe);
				
				//debug
				LOG.debug("Removed Recipe : " + recipe.toString());
			}
		}
		
		//clean up
		ACTIONS_REMOVE.clear();
		removeNames.clear();
		removeIngredients.clear();
	}
	
	//**************************************************************************************//
	//										addShaped										//
	//**************************************************************************************//
	
	public static List<IRecipe> shapedRecipes = new ArrayList<>();

	public void shaped(String recipeName, ObjStack output, ObjRecipe input)
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
			shapedRecipes.add(recipe);
		}
	}
	
	public static void addShapedApply()
	{
		//apply scripts
		for (ActionHolder script : ACTIONS_ADD_SHAPED)
		{
			ActionLoader.applyAction(METHOD_ADD_SHAPED, script);
		}
		
		//search registry to match recipes
		if (TweakedController.RECIPE_REGISTRY == null)
		{
			LOG.warn("Warning : Recipe Registry Missing");
			return;
		}
		
		if (!shapedRecipes.isEmpty())
		{
			for (IRecipe recipe : shapedRecipes)
			{
				TweakedController.RECIPE_REGISTRY.register(recipe);
			}
		}
		
		//clean up
		ACTIONS_ADD_SHAPED.clear();
		shapedRecipes.clear();
	}
	
}
