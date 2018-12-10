package com.mabinogi.tweaked.mods.vanilla;

import com.mabinogi.tweaked.Tweaked;
import com.mabinogi.tweaked.controllers.TweakedRecipes;
import com.mabinogi.tweaked.mods.ModManager;
import com.mabinogi.tweaked.mods.vanilla.actions.*;
import com.mabinogi.tweaked.mods.vanilla.proxy.Proxy_Vanilla_Common;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryModifiable;

@SuppressWarnings("WeakerAccess")
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
			//recipes
			Action_Vanilla_Recipes.REMOVE.apply();
			Action_Vanilla_Recipes.SHAPED.apply();
			Action_Vanilla_Recipes.SHAPELESS.apply();

			//anvil
			Action_Vanilla_Anvil.ADD.apply();
			Action_Vanilla_Anvil.REMOVE.apply();
			Action_Vanilla_Anvil.DISABLE.apply();
			Action_Vanilla_Anvil.BREAK_CHANCE.apply();

			//dict
			Action_Vanilla_Dict.REMOVE.apply();
			Action_Vanilla_Dict.ADD.apply();
			Action_Vanilla_Dict.REPLACE.apply();
			OreDictionary.rebakeMap();

			//lang
			Action_Vanilla_Lang.SET.apply();

			//loot
			Action_Vanilla_Loot.CLEAR.apply();
			Action_Vanilla_Loot.REMOVE_POOL.apply();
			Action_Vanilla_Loot.REMOVE_ENTRY.apply();
			Action_Vanilla_Loot.ADD_POOL.apply();
			Action_Vanilla_Loot.ADD_ENTRY.apply();
			Action_Vanilla_Loot.ADD_CONDITION.apply();
			Action_Vanilla_Loot.ADD_FUNCTION.apply();

			//seeds
			Action_Vanilla_Seeds.REMOVE.apply();
			Action_Vanilla_Seeds.ADD.apply();

			//trades
			Action_Vanilla_Trade.CLEAR_TRADES.apply();
			Action_Vanilla_Trade.ADD_CAREER.apply();
			Action_Vanilla_Trade.ADD_TRADE.apply();
			Action_Vanilla_Trade.ADD_TRADE_ENCHANT.apply();
			Action_Vanilla_Trade.ADD_TRADE_MAP.apply();
			Action_Vanilla_Trade.SET_NAME.apply();

			//create dummy recipes
			TweakedRecipes.createDummyRecipes();
		}
	}

	@EventHandler
	public void post(FMLPostInitializationEvent event)
	{
		//register late events
		proxy.registerEventsLate();
	}
}