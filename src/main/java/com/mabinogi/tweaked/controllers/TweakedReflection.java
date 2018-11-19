package com.mabinogi.tweaked.controllers;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.WeightedRandom.Item;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.LOG;

@SuppressWarnings("unchecked")
public class TweakedReflection
{
	private static final Field DICT_IDTOSTACK = ReflectionHelper.findField(OreDictionary.class, "idToStack");

	private static final Field SEED_ENTRIES = ReflectionHelper.findField(ForgeHooks.class, "seedList");
	private static Field SEED_STACK;
	private static Constructor<? extends Item> SEED_CONSTRUCTOR;

	static
	{
		//get the private seed stack
		Class<? extends Item> seedEntry;
		try
		{
			seedEntry = (Class<? extends Item>) Class.forName("net.minecraftforge.common.ForgeHooks$SeedEntry");
			SEED_STACK = ReflectionHelper.findField(seedEntry, "seed");

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
			LOG.error("Error : Unable to get Ore Dictionary");
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
			LOG.error("Error : Unable to get Seed Entries");
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
			LOG.error("Error : Unable to get Seed Stack");
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

}
