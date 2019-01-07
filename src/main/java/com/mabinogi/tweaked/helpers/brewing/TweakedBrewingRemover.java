package com.mabinogi.tweaked.helpers.brewing;

import com.mabinogi.tweaked.script.ScriptHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraftforge.common.brewing.VanillaBrewingRecipe;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class TweakedBrewingRemover extends VanillaBrewingRecipe
{
	private boolean clear = false;
	private List<ItemStack> blacklist = new ArrayList<>();

	public TweakedBrewingRemover(boolean clear)
	{
		this.clear = clear;
	}

	public TweakedBrewingRemover(List<ItemStack> blacklist)
	{
		this.blacklist = blacklist;
	}

	@Nonnull
	@Override
	public ItemStack getOutput(@Nonnull ItemStack input, @Nonnull ItemStack ingredient)
	{
		if (clear)
		{
			return ItemStack.EMPTY;
		}

		for (ItemStack stack : blacklist)
		{
			if (ScriptHelper.areScriptItemsEqual(stack, ingredient))
			{
				return ItemStack.EMPTY;
			}
		}

		return super.getOutput(input, ingredient);
	}

	@Override
	public boolean isIngredient(@Nonnull ItemStack ingredient)
	{
		if (clear)
		{
			return false;
		}

		for (ItemStack stack : blacklist)
		{
			if (ScriptHelper.areScriptItemsEqual(stack, ingredient))
			{
				return false;
			}
		}

		return PotionHelper.isReagent(ingredient);
	}
}
