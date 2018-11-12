package com.mabinogi.tweaked.helpers.loot;

import com.mabinogi.tweaked.helpers.loot.functions.FunctionEnchant;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.EntityHasProperty;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.*;
import net.minecraft.world.storage.loot.properties.EntityOnFire;
import net.minecraft.world.storage.loot.properties.EntityProperty;

import java.util.ArrayList;
import java.util.List;

public class TweakedLootEntry
{
	private String poolName;
	private String entryName;

	private ItemStack stack;
	private int weight;
	private int quality;

	private int minLootingBonus;
	private int maxLootingBonus;

	private List<EnchantmentData> enchants;
	private int enchantMin = 0;
	private int enchantMax = 0;
	private boolean enchantTreasure = false;

	private boolean autosmelt = false;

	public TweakedLootEntry(String poolName, String entryName)
	{
		this.poolName = poolName;
		this.entryName = entryName;
	}

	public TweakedLootEntry(String poolName, String entryName, ItemStack stack, int weight, int quality)
	{
		this(poolName, entryName, stack, weight, quality, 0, 0);
	}

	public TweakedLootEntry(String poolName, String entryName, ItemStack stack, int weight, int quality, int minLootingBonus, int maxLootingBonus)
	{
		this.poolName = poolName;
		this.entryName = entryName;

		this.stack = stack;
		this.weight = weight;
		this.quality = quality;

		this.minLootingBonus = minLootingBonus;
		this.maxLootingBonus = maxLootingBonus;
	}

	public String getPoolName()
	{
		return poolName;
	}

	public String getEntryName()
	{
		return entryName;
	}

	public ItemStack getStack()
	{
		return stack;
	}

	public void addEnchant(String name, int level)
	{
		Enchantment enchant = Enchantment.getEnchantmentByLocation(name);
		if (enchant != null)
		{
			if (enchants == null)
			{
				enchants = new ArrayList<>();
			}

			enchants.add(new EnchantmentData(enchant, level));
		}
	}

	public void setEnchantRandomly(int enchantMin, int enchantMax, boolean enchantTreasure)
	{
		this.enchantMin = enchantMin;
		this.enchantMax = enchantMax;
		this.enchantTreasure = enchantTreasure;
	}

	public void setAutosmelt(boolean b)
	{
		this.autosmelt = b;
	}

	public LootEntryItem generate()
	{
		List<LootFunction> functions = new ArrayList<>();

		//add stack functions
		if (stack.getMetadata() != 0) functions.add(new SetMetadata(new LootCondition[0], new RandomValueRange(stack.getMetadata())));
		if (stack.getCount() != 1) functions.add(new SetCount(new LootCondition[0], new RandomValueRange(stack.getCount())));
		if (stack.getTagCompound() != null) functions.add(new SetNBT(new LootCondition[0], stack.getTagCompound()));

		//add looting function
		if (minLootingBonus > 0 || maxLootingBonus > 0)
		{
			functions.add(new LootingEnchantBonus(new LootCondition[0], new RandomValueRange(minLootingBonus, maxLootingBonus), 0));
		}

		//add smelting
		if (autosmelt)
		{
			functions.add(new Smelt(new LootCondition[] { new EntityHasProperty(new EntityProperty[] { new EntityOnFire(true)}, LootContext.EntityTarget.THIS)}));
		}

		//add enchants
		if (enchants != null)
		{
			functions.add(new FunctionEnchant(new LootCondition[0], enchants));
		}
		else if (enchantMin > 0 || enchantMax > 0)
		{
			functions.add(new EnchantWithLevels(new LootCondition[0], new RandomValueRange(enchantMin, enchantMax), enchantTreasure));
		}

		return new LootEntryItem(stack.getItem(), weight, quality, functions.toArray(new LootFunction[0]), new LootCondition[0], entryName);
	}
}
