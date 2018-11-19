package com.mabinogi.tweaked.helpers;

import com.mabinogi.tweaked.helpers.anvil.TweakedAnvilRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.*;

public class AnvilHelper
{
	private static Float breakChance = null;

	private static boolean clear = false;
	private static boolean disableBooks = false;
	private static boolean disableRepairs = false;
	private static boolean disableBookCombines = false;
	private static boolean disableRepairCombines = false;

	private static List<TweakedAnvilRecipe> additions;
	private static List<ItemStack> removals;

	/**
	 * Used to determine whether to register the onAnvilChange event
	 * @return true if it needs registering
	 */
	public static boolean hasChanges()
	{
		return clear || disableBooks || disableRepairs || disableBookCombines || disableRepairCombines || hasAdditions() || hasRemovals();
	}

	public static boolean hasBreakChance()
	{
		return breakChance != null;
	}

	public static void setBreakChance(float f)
	{
		breakChance = f;

		LOG.debug("anvil.breakChance : " + f);
	}

	public static float getBreakChance()
	{
		return breakChance;
	}

	public static void setClear(boolean b)
	{
		clear = b;

		LOG.debug("anvil.clear : " + b);
	}

	public static boolean isClear()
	{
		return clear;
	}

	public static void setDisableBooks(boolean b)
	{
		disableBooks = b;

		LOG.debug("anvil.disable(books) : " + b);
	}

	public static boolean isDisableBooks()
	{
		return disableBooks;
	}

	public static void setDisableRepairs(boolean b)
	{
		disableRepairs = b;

		LOG.debug("anvil.disable(repairs) : " + b);
	}

	public static boolean isDisableRepairs()
	{
		return disableRepairs;
	}

	public static void setDisableCombineBooks(boolean b)
	{
		disableBookCombines = b;

		LOG.debug("anvil.disable(combinebooks) : " + b);
	}

	public static boolean isDisableCombineBooks()
	{
		return disableBookCombines;
	}

	public static void setDisableCombineRepairs(boolean b)
	{
		disableRepairCombines = b;

		LOG.debug("anvil.disable(combinerepairs) : " + b);
	}

	public static boolean isDisableCombineRepairs()
	{
		return disableRepairCombines;
	}

	public static boolean hasAdditions()
	{
		return additions != null;
	}

	public static void addAddition(TweakedAnvilRecipe recipe)
	{
		if (additions == null) additions = new ArrayList<>();

		additions.add(recipe);

		LOG.debug("anvil.add : " + recipe.getOutput());
	}

	public static List<TweakedAnvilRecipe> getAdditions()
	{
		return additions;
	}

	public static boolean hasRemovals()
	{
		return removals != null;
	}

	public static void addRemoval(ItemStack stack)
	{
		if (removals == null) removals = new ArrayList<>();

		removals.add(stack);

		LOG.debug("anvil.remove : " + stack);
	}

	public static List<ItemStack> getRemovals()
	{
		return removals;
	}

	public static boolean isBookRecipe(ItemStack left, ItemStack right)
	{
		return (left.getItem() == Items.ENCHANTED_BOOK || right.getItem() == Items.ENCHANTED_BOOK);
	}

	public static boolean isRepairRecipe(ItemStack left, ItemStack right)
	{
		return ItemStack.areItemsEqual(left, right) || left.isItemStackDamageable() && left.getItem().getIsRepairable(left, right);
	}

	public enum TweakedAnvilType
	{
		VALID, INVALID, BOOK, REPAIR, COMBINE_BOOK, COMBINE_REPAIR;
	}

	public static TweakedAnvilType getAnvilRecipeType(ItemStack left, ItemStack right)
	{
		//ignore recipes where one slot is empty, probably a rename
		if (left.isEmpty() || right.isEmpty()) return TweakedAnvilType.VALID;

		//book combine, both slots are enchanted books
		if (left.getItem() == Items.ENCHANTED_BOOK && right.getItem() == Items.ENCHANTED_BOOK)
		{
			return TweakedAnvilType.COMBINE_BOOK;
		}

		//book, one slit is enchanted book
		if (left.getItem() == Items.ENCHANTED_BOOK || right.getItem() == Items.ENCHANTED_BOOK)
		{
			return TweakedAnvilType.BOOK;
		}

		//repair combine, both slots are the same item
		if (ItemStack.areItemsEqualIgnoreDurability(left, right))
		{
			return TweakedAnvilType.COMBINE_REPAIR;
		}

		//standard repair
		if (left.isItemStackDamageable() && left.getItem().getIsRepairable(left, right))
		{
			return TweakedAnvilType.REPAIR;
		}

		//assume anything here is invalid, experimental though
		return TweakedAnvilType.INVALID;
	}
}
