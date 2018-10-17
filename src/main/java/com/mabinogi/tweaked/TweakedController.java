package com.mabinogi.tweaked;

import com.mabinogi.tweaked.actions.ActionRecipe;
import com.mabinogi.tweaked.actions.ActionLang;

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
		ActionRecipe.removeApply();
		
		//add shaped recipes
		ActionRecipe.addShapedApply();
		
		//apply stack translations
		ActionLang.setNameApply();
	}

}
