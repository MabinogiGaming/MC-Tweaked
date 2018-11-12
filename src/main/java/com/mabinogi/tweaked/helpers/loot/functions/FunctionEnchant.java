package com.mabinogi.tweaked.helpers.loot.functions;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FunctionEnchant extends LootFunction
{
	private final List<EnchantmentData> enchantments;

	public FunctionEnchant(LootCondition[] conditionsIn, @Nullable List<EnchantmentData> enchantments)
	{
		super(conditionsIn);
		this.enchantments = enchantments == null ? Collections.emptyList() : enchantments;
	}

	@Override
	public ItemStack apply(ItemStack stack, Random rand, LootContext context)
	{
		if (enchantments != null && !enchantments.isEmpty())
		{
			if (stack.getItem() == Items.BOOK)
			{
				stack = new ItemStack(Items.ENCHANTED_BOOK);
			}

			for (EnchantmentData enchantment : enchantments)
			{
				if (stack.getItem() == Items.ENCHANTED_BOOK)
				{
					ItemEnchantedBook.addEnchantment(stack, enchantment);
				}
				else
				{
					stack.addEnchantment(enchantment.enchantment, enchantment.enchantmentLevel);
				}
			}
		}
		return stack;
	}
}
