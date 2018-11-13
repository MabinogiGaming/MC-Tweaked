package com.mabinogi.tweaked.helpers;

import com.mabinogi.tweaked.helpers.loot.TweakedLootEntry;
import com.mabinogi.tweaked.helpers.loot.TweakedLootHolder;
import com.mabinogi.tweaked.helpers.loot.TweakedLootPool;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.*;

import java.util.HashMap;
import java.util.Map;

import static com.mabinogi.tweaked.Tweaked.LOG;

@SuppressWarnings("unused")
public class LootHelper {

	private static Map<String, TweakedLootHolder> lootMap = new HashMap<>();
	private static LootTable witherOverride = null;

	/**
	 * Check whether there are any loot tables, used to determine whether to register events
	 * @return True if the lootMap is not empty
	 */
	public static boolean hasLootOverrides()
	{
		return !lootMap.isEmpty();
	}

	/**
	 * Check whether there is an override loot table for the Wither, used to determine whether to register events
	 * @return True if a loot table exists for the Wither
	 */
	public static boolean hasWitherOverride()
	{
		return lootMap.containsKey("entities/wither");
	}

	/**
	 * Gets the loot table associated to the Wither
	 * @return The LootTable or null if it does not exist
	 */
	public static LootTable getWitherOverride()
	{
		if (witherOverride == null && lootMap.containsKey("entities/wither"))
		{
			witherOverride = lootMap.get("entities/wither").mergeTable(new LootTable(new LootPool[0]));
			lootMap.remove("entities/wither");
		}
		return witherOverride;
	}

	/**
	 * Modifies a loot table using the lootHolders that have been stored in the lootMap
	 * @param key The ResourceLocation for the loot table
	 * @param lootTable The loot table to be modified
	 * @return The modified loot table or <b>null</b> if unmodified
	 */
	public static LootTable modifyLootTable(String key, LootTable lootTable)
	{
		if (lootMap.containsKey(key))
		{
			LootTable table = lootMap.get(key).mergeTable(lootTable);
			lootMap.remove(key);
			return table;
		}
		return null;
	}

	/**
	 * Gets a LootHolder from the lootMap, creating a new one if one cannot be found
	 * @param key The ResourceLocation for the loot table
	 * @return The LootHolder
	 */
	private static TweakedLootHolder getLootHolder(String key)
	{
		if (lootMap.containsKey(key))
		{
			return lootMap.get(key);
		}
		else
		{
			TweakedLootHolder holder = new TweakedLootHolder();
			lootMap.put(key, holder);

			return holder;
		}
	}

	/**
	 * Flag a LootHolder to clear all pools in a table
	 * @param key The ResourceLocation for the loot table
	 */
	public static void clearTable(String key)
	{
		TweakedLootHolder holder = getLootHolder(key);
		holder.setClear(true);

		LOG.debug("loot.clear : " + key);
	}

	/**
	 * Flag a LootHolder to add a new pool to a table
	 * @param key The ResourceLocation for the loot table
	 * @param poolName The name of the pool, must be unique
	 * @param minCount The minimum amount of items that will generate
	 * @param maxCount The maximum amount of items that will generate
	 */
	public static void addPool(String key, String poolName, int minCount, int maxCount)
	{
		TweakedLootHolder holder = getLootHolder(key);
		holder.addLootPool(new TweakedLootPool(poolName, minCount, maxCount));

		LOG.debug("loot.addpool : " + poolName + " to " + key);
	}

	/**
	 * Flag a LootHolder to add a new pool to a table
	 * @param key The ResourceLocation for the loot table
	 * @param poolName The name of the pool, must be unique
	 * @param minCount The minimum amount of items that will generate
	 * @param maxCount The maximum amount of items that will generate
	 * @param minLuckBonus The minimum amount of additional items that will generate based on player luck
	 * @param maxLuckBonus The maximum amount of additional items that will generate based on player luck
	 */
	public static void addPool(String key, String poolName, int minCount, int maxCount, int minLuckBonus, int maxLuckBonus)
	{
		TweakedLootHolder holder = getLootHolder(key);
		holder.addLootPool(new TweakedLootPool(poolName, minCount, maxCount, minLuckBonus, maxLuckBonus));

		LOG.debug("loot.addpool : " + poolName + " to " + key);
	}

	/**
	 * Flag a LootHolder to remove a pool from a table, including all entries within that pool
	 * @param key The ResourceLocation for the loot table
	 * @param poolName The name of the pool, must exist
	 */
	public static void removePool(String key, String poolName)
	{
		TweakedLootHolder holder = getLootHolder(key);
		holder.removeLootPool(poolName);

		LOG.debug("loot.removepool : " + poolName + " from " + key);
	}

	/**
	 * Flag a LootHolder to add an entry to a pool
	 * @param key The ResourceLocation for the loot table
	 * @param poolName The name of the pool, must exist
	 * @param entryName The name of the entry, must be unique
	 * @param stack The itemstack that will generate
	 * @param weight The weight determines the chance of this item generating compared to others
	 * @param quality Quality allows additional weight to be added depending on player luck
	 */
	public static void addEntry(String key, String poolName, String entryName, ItemStack stack, int weight, int quality)
	{
		TweakedLootHolder holder = getLootHolder(key);
		holder.addLootEntry(new TweakedLootEntry(poolName, entryName, stack, weight, quality));

		LOG.debug("loot.addentry : " + entryName + " to " + key + ":" + poolName);
	}

	/**
	 * Flag a LootHolder to add an entry to a pool
	 * @param key The ResourceLocation for the loot table
	 * @param poolName The name of the pool, must exist
	 * @param entryName The name of the entry, must be unique
	 * @param stack The itemstack that will generate
	 * @param weight The weight determines the chance of this item generating compared to others
	 * @param quality Quality allows additional weight to be added depending on player luck
	 * @param minLootingBonus The minimum amount of additional items that will generate based on looting enchants
	 * @param maxLootingBonus The maximum amount of additional items that will generate based on looting enchants
	 */
	public static void addEntry(String key, String poolName, String entryName, ItemStack stack, int weight, int quality, int minLootingBonus, int maxLootingBonus)
	{
		TweakedLootHolder holder = getLootHolder(key);
		holder.addLootEntry(new TweakedLootEntry(poolName, entryName, stack, weight, quality, minLootingBonus, maxLootingBonus));

		LOG.debug("loot.addentry : " + entryName + " to " + key + ":" + poolName);
	}

	/**
	 * Flag a LootHolder to remove an entry from a pool
	 * @param key The ResourceLocation for the loot table
	 * @param poolName The name of the pool, must exist
	 * @param entryName The name of the entry, must be unique
	 */
	public static void removeEntry(String key, String poolName, String entryName)
	{
		TweakedLootHolder holder = getLootHolder(key);
		holder.removeLootEntry(new TweakedLootEntry(poolName, entryName));

		LOG.debug("loot.removeentry : " + entryName + " from " + key + ":" + poolName);
	}

	/**
	 * Flag a LootHolder to add the PlayerKillOnly condition to a pool
	 * @param key The ResourceLocation for the loot table
	 * @param poolName The name of the pool, must exist
	 */
	public static void addConditionPlayerKill(String key, String poolName)
	{
		TweakedLootHolder holder = getLootHolder(key);
		holder.addConditionPlayerKill(poolName);

		LOG.debug("loot.addcondition : playerOnly to " + key + ":" + poolName);
	}

	/**
	 * Flag a LootHolder to add the RandomChance condition to a pool
	 * @param key The ResourceLocation for the loot table
	 * @param poolName The name of the pool, must exist
	 * @param chance The chance for the condition to succeed, between 0.0 and 1.0
	 */
	public static void addConditionChance(String key, String poolName, float chance)
	{
		TweakedLootHolder holder = getLootHolder(key);
		holder.addConditionChance(poolName, chance);

		LOG.debug("loot.addcondition : chance(" + chance + ") to " + key + ":" + poolName);
	}

	/**
	 * Flag a LootHolder to add the RandomChanceWithLooting condition to a pool
	 * @param key The ResourceLocation for the loot table
	 * @param poolName The name of the pool, must exist
	 * @param chance The chance for the condition to succeed, between 0.0 and 1.0
	 * @param lootMultiplier The additional chance for the condition to succeed based on looting enchants, between 0.0 and 1.0
	 */
	public static void addConditionLooting(String key, String poolName, float chance, float lootMultiplier)
	{
		TweakedLootHolder holder = getLootHolder(key);
		holder.addConditionLooting(poolName, chance, lootMultiplier);

		LOG.debug("loot.addcondition : chanceWithLooting(" + chance + ", " + lootMultiplier + ") to " + key + ":" + poolName);
	}

	/**
	 * Flag a LootHolder to add the FunctionEnchant function to a entry, applying the enchantment to the generated item
	 * @param key The ResourceLocation for the loot table
	 * @param poolName The name of the pool, must exist
	 * @param entry The name of the entry, must exist
	 * @param enchantName The name of the enchant
	 * @param enchantLevel The level of the enchant
	 */
	public static void addFunctionEnchant(String key, String poolName, String entry, String enchantName, int enchantLevel)
	{
		TweakedLootHolder holder = getLootHolder(key);
		holder.addFunctionEnchant(poolName, entry, enchantName, enchantLevel);

		LOG.debug("loot.addfunction : enchant(" + enchantName + ", " + enchantLevel + ") to " + key + ":" + poolName);
	}

	/**
	 * Flag a LootHolder to add the EnchantWithLevels function to a entry, applying a random enchantment to the generated item
	 * @param key The ResourceLocation for the loot table
	 * @param poolName The name of the pool, must exist
	 * @param entry The name of the entry, must exist
	 * @param enchantMin The minimum enchantment power
	 * @param enchantMax The maximum enchantment power
	 * @param enchantTreasure Whether to allow treasure enchantments
	 */
	public static void addFunctionEnchantRandomly(String key, String poolName, String entry, int enchantMin, int enchantMax, boolean enchantTreasure)
	{
		TweakedLootHolder holder = getLootHolder(key);
		holder.addFunctionEnchantRandomly(poolName, entry, enchantMin, enchantMax, enchantTreasure);

		LOG.debug("loot.addfunction : enchantRandomly(" + enchantMin + ", " + enchantMax + ", " + enchantTreasure + ") to " + key + ":" + poolName);
	}

	/**
	 * Flag a LootHolder to add the Smelt function to a entry, smelting generated items when the entity is burning
	 * @param key The ResourceLocation for the loot table
	 * @param poolName The name of the pool, must exist
	 * @param entry The name of the entry, must exist
	 */
	public static void addFunctionSmelt(String key, String poolName, String entry)
	{
		TweakedLootHolder holder = getLootHolder(key);
		holder.addFunctionSmelt(poolName, entry);

		LOG.debug("loot.addfunction : smelt to " + key + ":" + poolName);
	}

}
