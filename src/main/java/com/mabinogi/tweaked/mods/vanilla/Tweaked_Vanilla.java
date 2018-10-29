package com.mabinogi.tweaked.mods.vanilla;

import com.mabinogi.tweaked.Tweaked;
import com.mabinogi.tweaked.controllers.TweakedRecipes;
import com.mabinogi.tweaked.mods.ModManager;
import com.mabinogi.tweaked.mods.vanilla.actions.Action_Vanilla_Lang;
import com.mabinogi.tweaked.mods.vanilla.actions.Action_Vanilla_Recipes;
import com.mabinogi.tweaked.mods.vanilla.proxy.Proxy_Vanilla_Common;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.registries.IForgeRegistryModifiable;

@Mod(modid = Tweaked_Vanilla.MODID, name = Tweaked_Vanilla.NAME, version = Tweaked.VERSION, dependencies=Tweaked_Vanilla.DEPENDENCIES)
public class Tweaked_Vanilla
{
	public static final String MODID = "tweaked_vanilla";
	public static final String NAME = "Tweaked_Vanilla";
	public static final String DEPENDENCIES = "required-after:tweaked;";

	@SidedProxy(clientSide = "com.mabinogi.tweaked.mods.vanilla.proxy.Proxy_Vanilla_Client", serverSide = "com.mabinogi.tweaked.mods.vanilla.proxy.Proxy_Vanilla_Common")
	public static Proxy_Vanilla_Common proxy;

	//flags
	public static boolean ITEMS_REGISTERED = false;
	public static boolean RECIPEBOOK_FIXED = false;

	//storage
	public static IForgeRegistryModifiable<IRecipe> RECIPE_REGISTRY = null;

	@EventHandler
	public void pre(FMLPreInitializationEvent event)
	{
		//register events
		proxy.registerEvents();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (ModManager.VANILLA_LOADED)
		{
			//remove recipes
			Action_Vanilla_Recipes.REMOVE.apply();

			//add shaped recipes
			Action_Vanilla_Recipes.SHAPED.apply();

			//add shaped recipes
			Action_Vanilla_Recipes.SHAPELESS.apply();

			//apply stack translations
			Action_Vanilla_Lang.SET_NAME.apply();

			//create dummy recipes
			TweakedRecipes.createDummyRecipes();
		}
	}
}