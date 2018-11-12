package com.mabinogi.tweaked.helpers.loot;

import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.conditions.KilledByPlayer;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.conditions.RandomChanceWithLooting;

import java.util.ArrayList;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.LOG;

public class TweakedLootHolder
{
	private boolean clearLoot = false;

	private List<TweakedLootPool> addPoolList = null;
	private List<String> removePoolList = null;

	private List<TweakedLootEntry> addEntryList = null;
	private List<TweakedLootEntry> removeEntryList = null;

	public LootTable mergeTable(LootTable table)
	{
		//clear
		if (clearLoot)
		{
			table = new LootTable(new LootPool[0]);
		}

		//remove pools
		if (removePoolList != null)
		{
			for (String pool : removePoolList)
			{
				table.removePool(pool);
			}
			removePoolList = null;
		}

		//remove entries
		if (removeEntryList != null)
		{
			for (TweakedLootEntry entry : removeEntryList)
			{
				LootPool pool;
				if ((pool = table.getPool(entry.getPoolName())) != null)
				{
					pool.removeEntry(entry.getEntryName());
				}
				else
				{
					//missing pool
					LOG.warn("Warning : Loot pool '" + entry.getPoolName() + "' doesn't exist");
				}
			}
			removeEntryList = null;
		}

		//add pools
		if (addPoolList != null)
		{
			for (TweakedLootPool pool : addPoolList)
			{
				if (table.getPool(pool.getPoolName()) == null)
				{
					table.addPool(pool.generate());
				}
				else
				{
					//duplicate poolName
					LOG.warn("Warning : Attempted to add duplicate loot pool");
				}
			}
			addPoolList = null;
		}

		//add entries
		if (addEntryList != null)
		{
			for (TweakedLootEntry entry : addEntryList)
			{
				LootPool pool;
				if ((pool = table.getPool(entry.getPoolName())) != null)
				{
					pool.addEntry(entry.generate());
				}
				else
				{
					//missing pool
					LOG.warn("Warning : Loot pool '" + entry.getPoolName() + "' doesn't exist");
				}
			}
			addEntryList = null;
		}

		return table;
	}

	public void setClear(boolean b)
	{
		clearLoot = b;
	}

	public void addLootPool(TweakedLootPool pool)
	{
		if (addPoolList == null) addPoolList = new ArrayList<>();
		addPoolList.add(pool);
	}

	public void removeLootPool(String poolName)
	{
		if (removePoolList == null) removePoolList = new ArrayList<>();
		removePoolList.add(poolName);
	}

	public void addLootEntry(TweakedLootEntry entry)
	{
		if (addEntryList == null) addEntryList = new ArrayList<>();
		addEntryList.add(entry);
	}

	public void removeLootEntry(TweakedLootEntry entry)
	{
		if (removeEntryList == null) removeEntryList = new ArrayList<>();
		removeEntryList.add(entry);
	}

	public void addConditionPlayerKill(String poolName)
	{
		for (TweakedLootPool pool : addPoolList)
		{
			if (pool.getPoolName().equals(poolName))
			{
				pool.addCondition(new KilledByPlayer(false));
				return;
			}
		}

		//missing pool
		LOG.warn("Warning : Loot pool '" + poolName + "' doesn't exist");
	}

	public void addConditionChance(String poolName, float chance)
	{
		for (TweakedLootPool pool : addPoolList)
		{
			if (pool.getPoolName().equals(poolName))
			{
				pool.addCondition(new RandomChance(chance));
				return;
			}
		}

		//missing pool
		LOG.warn("Warning : Loot pool '" + poolName + "' doesn't exist");
	}

	public void addConditionLooting(String poolName, float chance, float lootMultiplier)
	{
		for (TweakedLootPool pool : addPoolList)
		{
			if (pool.getPoolName().equals(poolName))
			{
				pool.addCondition(new RandomChanceWithLooting(chance, lootMultiplier));
				return;
			}
		}

		//missing pool
		LOG.warn("Warning : Loot pool '" + poolName + "' doesn't exist");
	}

	public void addFunctionEnchant(String poolName, String entryName, String enchantName, int enchantLevel)
	{
		for (TweakedLootEntry entry : addEntryList)
		{
			if (entry.getPoolName().equals(poolName) && entry.getEntryName().equals(entryName))
			{
				entry.addEnchant(enchantName, enchantLevel);
			}
		}
	}

	public void addFunctionEnchantRandomly(String poolName, String entryName, int enchantMin, int enchantMax, boolean enchantTreasure)
	{
		for (TweakedLootEntry entry : addEntryList)
		{
			if (entry.getPoolName().equals(poolName) && entry.getEntryName().equals(entryName))
			{
				entry.setEnchantRandomly(enchantMin, enchantMax, enchantTreasure);
			}
		}
	}

	public void addFunctionSmelt(String poolName, String entryName)
	{
		for (TweakedLootEntry entry : addEntryList)
		{
			if (entry.getPoolName().equals(poolName) && entry.getEntryName().equals(entryName))
			{
				entry.setAutosmelt(true);
			}
		}
	}
}
