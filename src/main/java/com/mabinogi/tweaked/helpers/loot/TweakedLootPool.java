package com.mabinogi.tweaked.helpers.loot;

import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import java.util.ArrayList;
import java.util.List;

public class TweakedLootPool
{
	private String poolName;

	//conditions
	private List<LootCondition> conditions = new ArrayList<>();

	//counts
	private int minCount;
	private int maxCount;
	private int minBonus;
	private int maxBonus;

	public TweakedLootPool(String poolName, int minCount, int maxCount)
	{
		this(poolName, minCount, maxCount, 0, 0);
	}

	public TweakedLootPool(String poolName, int minCount, int maxCount, int minBonus, int maxBonus)
	{
		this.poolName = poolName;
		this.minCount = minCount;
		this.maxCount = maxCount;
		this.minBonus = minBonus;
		this.maxBonus = maxBonus;
	}

	public String getPoolName()
	{
		return poolName;
	}

	public void addCondition(LootCondition condition)
	{
		conditions.add(condition);
	}

	public LootPool generate()
	{
		return new LootPool(new LootEntry[0], conditions.toArray(new LootCondition[0]), new RandomValueRange(minCount, maxCount), new RandomValueRange(minBonus, maxBonus), poolName);
	}
}
