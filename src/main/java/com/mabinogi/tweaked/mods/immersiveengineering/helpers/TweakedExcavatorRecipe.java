package com.mabinogi.tweaked.mods.immersiveengineering.helpers;

public class TweakedExcavatorRecipe
{
	private String name;
	private Integer weight;
	private Float fail;
	private String ore;
	private Float chance;

	public TweakedExcavatorRecipe(String name, Integer weight, Float fail, String ore, Float chance)
	{
		this.name = name;
		this.weight = weight;
		this.fail = fail;
		this.ore = ore;
		this.chance = chance;
	}

	public String getName()
	{
		return name;
	}

	public Integer getWeight()
	{
		return weight;
	}

	public Float getFail()
	{
		return fail;
	}

	public String getOre()
	{
		return ore;
	}

	public Float getChance()
	{
		return chance;
	}
}
