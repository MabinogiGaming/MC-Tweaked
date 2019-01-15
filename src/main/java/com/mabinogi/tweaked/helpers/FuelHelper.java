package com.mabinogi.tweaked.helpers;

import com.mabinogi.tweaked.helpers.furnace.TweakedFuel;
import com.mabinogi.tweaked.script.ScriptHelper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.LOG;

public class FuelHelper
{
	private static boolean clear = false;

	private static List<TweakedFuel> addFuels;
	private static List<ItemStack> removeFuels;

	/**
	 * Used to determine whether to register the loadFuel event
	 * @return true if it needs registering
	 */
	public static boolean hasChanges()
	{
		return clear || addFuels != null || removeFuels != null;
	}

	public static void setClear(boolean b)
	{
		clear = b;

		LOG.debug("furnace.removefuel : clear");
	}

	public static boolean isClear()
	{
		return clear;
	}

	public static void addAddFuel(ItemStack fuel, int burnTime)
	{
		if (addFuels == null) addFuels = new ArrayList<>();

		addFuels.add(new TweakedFuel(fuel.copy(), burnTime));

		LOG.debug("furnace.addfuel : " + ScriptHelper.stackToScript(fuel) + " = " + burnTime);
	}

	public static List<TweakedFuel> getAddFuels()
	{
		return addFuels;
	}

	public static void addRemoveFuel(ItemStack fuel)
	{
		if (removeFuels == null) removeFuels = new ArrayList<>();

		removeFuels.add(fuel.copy());

		LOG.debug("furnace.removefuel : " + ScriptHelper.stackToScript(fuel));
	}

	public static List<ItemStack> getRemoveFuels()
	{
		return removeFuels;
	}
}
