package com.mabinogi.tweaked.helpers.furnace;

import net.minecraft.item.ItemStack;

public class TweakedFurnaceRecipe
{
	private ItemStack input;
	private ItemStack output;
	private float experience;

	public TweakedFurnaceRecipe(ItemStack input, ItemStack output, float experience)
	{
		this.input = input;
		this.output = output;
		this.experience = experience;
	}

	public ItemStack getInput()
	{
		return input;
	}

	public ItemStack getOutput()
	{
		return output;
	}

	public float getExperience()
	{
		return experience;
	}
}
