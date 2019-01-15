package com.mabinogi.tweaked.helpers.furnace;

import net.minecraft.item.ItemStack;

public class TweakedFuel
{
	private ItemStack fuel;
	private int burnTime;

	public TweakedFuel(ItemStack fuel, int burnTime)
	{
		this.fuel = fuel;
		this.burnTime = burnTime;
	}

	public ItemStack getFuel()
	{
		return fuel;
	}

	public int getBurnTime()
	{
		return burnTime;
	}
}
