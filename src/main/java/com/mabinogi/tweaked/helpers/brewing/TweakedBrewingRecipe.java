package com.mabinogi.tweaked.helpers.brewing;

import com.mabinogi.tweaked.script.ScriptHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.AbstractBrewingRecipe;

import javax.annotation.Nonnull;

public class TweakedBrewingRecipe extends AbstractBrewingRecipe
{
	public TweakedBrewingRecipe(@Nonnull ItemStack input, @Nonnull ItemStack ingredient, @Nonnull ItemStack output)
	{
		super(input, ingredient, output);
	}

	@Override
	public boolean isIngredient(@Nonnull ItemStack stack)
	{
		Object ingredient = getIngredient();
		if (ingredient instanceof ItemStack)
		{
			return ScriptHelper.areScriptItemsEqual((ItemStack) ingredient, stack);
		}
		return false;
	}
}
