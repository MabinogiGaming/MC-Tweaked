package com.mabinogi.tweaked.controllers;

import com.mabinogi.tweaked.mods.vanilla.Tweaked_Vanilla;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweakedRecipes
{
	public static List<ResourceLocation> REMOVED_RECIPES = new ArrayList<>();

	public static ModContainer TWEAKED_CONTAINER;
	private static ModContainer LAST_CONTAINER;
	private static Map<String, ModContainer> MOD_CONTAINERS;

	public static void setTweakedContainer()
	{
		LAST_CONTAINER = Loader.instance().activeModContainer();
		Loader.instance().setActiveModContainer(TWEAKED_CONTAINER);
	}

	public static void setModContainer(String mod)
	{
		//build map if we haven't already
		if (MOD_CONTAINERS == null)
		{
			MOD_CONTAINERS = new HashMap<>();
			for (ModContainer modContainer : Loader.instance().getModList())
			{
				MOD_CONTAINERS.put(modContainer.getModId(), modContainer);
			}
		}

		if (MOD_CONTAINERS.containsKey(mod))
		{
			LAST_CONTAINER = Loader.instance().activeModContainer();
			Loader.instance().setActiveModContainer(MOD_CONTAINERS.get(mod));
		}
	}

	public static void restoreContainer()
	{
		Loader.instance().setActiveModContainer(LAST_CONTAINER);
	}

	public static void createDummyRecipes()
	{
		if (REMOVED_RECIPES.isEmpty()) return;

		for (ResourceLocation recipe : REMOVED_RECIPES)
		{
			if (!Tweaked_Vanilla.RECIPE_REGISTRY.containsKey(recipe))
			{
				//swap to correct container
				setModContainer(recipe.getResourceDomain());

				//recipe hasn't been replaced, we need to add a dummy
				IRecipe dummy = new DummyRecipe();
				dummy.setRegistryName(recipe);

				Tweaked_Vanilla.RECIPE_REGISTRY.register(dummy);

				//restore our container
				restoreContainer();
			}
		}
	}

	/**
	 * A no-op implementation of {@link IRecipe} designed to override vanilla recipes.
	 * Credit to Choonster
	 */
	public static class DummyRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

		@Override
		public boolean matches(final InventoryCrafting inv, final World worldIn)
		{
			return false;
		}

		@Override
		public ItemStack getCraftingResult(final InventoryCrafting inv)
		{
			return ItemStack.EMPTY;
		}

		@Override
		public boolean canFit(final int width, final int height)
		{
			return false;
		}

		@Override
		public ItemStack getRecipeOutput()
		{
			return ItemStack.EMPTY;
		}
	}
}
