package com.mabinogi.tweaked.helpers.anvil;

import com.mabinogi.tweaked.script.ScriptHelper;
import net.minecraft.item.ItemStack;

public class TweakedAnvilRecipe
{
	private ItemStack left;
	private ItemStack right;
	private ItemStack output;
	private int cost;

	public TweakedAnvilRecipe(ItemStack left, ItemStack right, ItemStack output, int cost)
	{
		this.left = left;
		this.right = right;
		this.output = output;
		this.cost = cost;
	}

	public ItemStack getLeft()
	{
		return left.copy();
	}

	public ItemStack getRight()
	{
		return right.copy();
	}

	public ItemStack getOutput()
	{
		return output.copy();
	}

	public int getCost()
	{
		return cost;
	}

	public boolean matches(ItemStack inLeft, ItemStack inRight)
	{
		if (ScriptHelper.areScriptItemsEqual(left, inLeft) && ScriptHelper.areScriptItemsEqual(right, inRight))
		{
			if (inLeft.getCount() >= left.getCount() && inRight.getCount() >= right.getCount())
			{
				return true;
			}
		}
		return false;
	}
}
