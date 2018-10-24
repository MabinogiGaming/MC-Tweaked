package com.mabinogi.tweaked;

import com.mabinogi.tweaked.actions.Action_Lang;
import com.mabinogi.tweaked.actions.Action_Recipes;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.registries.IForgeRegistryModifiable;

public class TweakedController {
	
	//flags
	public static boolean ITEMS_REGISTERED = false;
	public static boolean RECIPEBOOK_FIXED = false;
	
	//storage
	public static IForgeRegistryModifiable<IRecipe> RECIPE_REGISTRY = null;
	
	public static void initTweaks()
	{
		//remove recipes
		Action_Recipes.REMOVE.apply();
		
		//add shaped recipes
		Action_Recipes.SHAPED.apply();
		
		//add shaped recipes
		Action_Recipes.SHAPELESS.apply();
		
		//apply stack translations
		Action_Lang.SET_NAME.apply();
	}

}
