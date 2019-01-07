package com.mabinogi.tweaked.controllers;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.WeightedRandom.Item;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.LOG;
import static net.minecraftforge.fml.relauncher.ReflectionHelper.*;

@SuppressWarnings("unchecked")
public class TweakedReflection
{
	private static final Field DICT_IDTOSTACK = findField(OreDictionary.class, "idToStack");

	private static final Field BREWING_RECIPES = findField(BrewingRecipeRegistry.class, "recipes");

	private static final Field SEED_ENTRIES = findField(ForgeHooks.class, "seedList");
	private static Field SEED_STACK;
	private static Constructor<? extends Item> SEED_CONSTRUCTOR;

	static
	{
		//get the private seed stack
		Class<? extends Item> seedEntry;
		try
		{
			seedEntry = (Class<? extends Item>) Class.forName("net.minecraftforge.common.ForgeHooks$SeedEntry");
			SEED_STACK = findField(seedEntry, "seed");

			//get the private constructor
			Constructor<? extends Item> seedConstructor;
			try
			{
				seedConstructor = seedEntry.getConstructor(ItemStack.class, int.class);
				seedConstructor.setAccessible(true);

				SEED_CONSTRUCTOR = seedConstructor;
			}
			catch(NoSuchMethodException | SecurityException ex)
			{
				LOG.error("Error : Method exception when getting seed entries");
			}
		}
		catch(ClassNotFoundException ignored)
		{
			LOG.error("Error : Class not found when getting seed entries");
		}
	}

	public static List<NonNullList<ItemStack>> getDictStacks()
	{
		try
		{
			return (List<NonNullList<ItemStack>>) DICT_IDTOSTACK.get(null);
		}
		catch (IllegalAccessException e)
		{
			LOG.error("Error : Unable to access Ore Dictionary");
			return null;
		}
	}

	public static List<IBrewingRecipe> getBrewingRecipes()
	{
		try
		{
			return (List<IBrewingRecipe>) BREWING_RECIPES.get(null);
		}
		catch (IllegalAccessException e)
		{
			LOG.error("Error : Unable to access Brewing Recipes");
			return null;
		}
	}

	public static List<Item> getSeedEntries()
	{
		try
		{
			return (List<Item>) SEED_ENTRIES.get(null);
		}
		catch (IllegalAccessException e)
		{
			LOG.error("Error : Unable to access Seed Entries");
			return null;
		}
	}

	public static ItemStack getSeedStack(Object entry)
	{
		try
		{
			return (ItemStack) SEED_STACK.get(entry);
		}
		catch(IllegalAccessException ex)
		{
			LOG.error("Error : Unable to access Seed Stack");
			return null;
		}
	}

	public static Item createSeedEntry(ItemStack stack, int chance)
	{
		try
		{
			return SEED_CONSTRUCTOR.newInstance(stack, chance);
		}
		catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
		{
			LOG.error("Error : Unable to create Seed Entry");
			return null;
		}
	}

	public static List<VillagerCareer> getVillagerCareers(VillagerProfession profession)
	{
		try
		{
			return ReflectionHelper.getPrivateValue(VillagerProfession.class, profession, "careers");
		}
		catch(UnableToAccessFieldException ex)
		{
			LOG.error("Error : Unable to get Villager Careers");
			return null;
		}
	}

	public static List<List<ITradeList>> getVillagerTrades(VillagerCareer career)
	{
		try
		{
			return ReflectionHelper.getPrivateValue(VillagerCareer.class, career, "trades");
		}
		catch(UnableToAccessFieldException ex)
		{
			LOG.error("Error : Unable to get Villager Trades");
			return null;
		}
	}

	public static void setVillagerName(VillagerCareer career, String name)
	{
		try
		{
			ReflectionHelper.setPrivateValue(VillagerCareer.class, career, name, "name");
		}
		catch(UnableToAccessFieldException ex)
		{
			LOG.error("Error : Unable to set Villager Name");
		}
	}


}
