package com.mabinogi.tweaked.controllers;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Field;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.LOG;

public class TweakedReflection
{
	private static final Field DICT_IDTOSTACK = ReflectionHelper.findField(OreDictionary.class, "idToStack");
	private static final Field DICT_IDTOSTACK_UN = ReflectionHelper.findField(OreDictionary.class, "idToStackUn");

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

	public static List<NonNullList<ItemStack>> getDictStacksUn()
	{
		try
		{
			return (List<NonNullList<ItemStack>>) DICT_IDTOSTACK_UN.get(null);
		}
		catch (IllegalAccessException e)
		{
			LOG.error("Error : Unable to get Ore Dictionary");
			return null;
		}
	}
}
