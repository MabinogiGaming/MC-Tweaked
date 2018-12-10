package com.mabinogi.tweaked.helpers.trade;

public class TweakedTradeCareer
{
	private String profession;
	private String name;

	public TweakedTradeCareer(String profession, String name)
	{
		this.profession = profession;
		this.name = name;
	}

	public String getProfession()
	{
		return profession;
	}

	public String getName()
	{
		return name;
	}
}
