package com.mabinogi.tweaked.helpers.trade;

import net.minecraft.item.ItemStack;

public class TweakedTradeEntry
{
	private String career;
	private int level;
	private ItemStack inputA;
	private ItemStack inputB;
	private ItemStack output;

	private int powerMin;
	private int powerMax;
	private boolean treasure;

	private String mapType;

	public TweakedTradeEntry(String career, int level, ItemStack inputA, ItemStack inputB, ItemStack output)
	{
		this.career= career;
		this.level = level;
		this.inputA = inputA;
		this.inputB = inputB;
		this.output = output;
	}

	public TweakedTradeEntry(String career, int level, ItemStack inputA, ItemStack output, int powerMin, int powerMax, boolean treasure)
	{
		this.career = career;
		this.level = level;
		this.inputA = inputA;
		this.output = output;
		this.powerMin = powerMin;
		this.powerMax = powerMax;
		this.treasure = treasure;
	}

	public TweakedTradeEntry(String career, int level, ItemStack inputA, ItemStack inputB, String mapType)
	{
		this.career = career;
		this.level = level;
		this.inputA = inputA;
		this.inputB = inputB;
		this.mapType = mapType;
	}

	public String getCareer()
	{
		return career;
	}

	public int getLevel()
	{
		return level;
	}

	public ItemStack getInputA()
	{
		return inputA;
	}

	public ItemStack getInputB()
	{
		return inputB;
	}

	public ItemStack getOutput()
	{
		return output;
	}

	public int getPowerMin()
	{
		return powerMin;
	}

	public int getPowerMax()
	{
		return powerMax;
	}

	public boolean isTreasure()
	{
		return treasure;
	}

	public String getMapType()
	{
		return mapType;
	}
}
