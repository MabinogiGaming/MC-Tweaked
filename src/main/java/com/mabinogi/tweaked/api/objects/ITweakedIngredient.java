package com.mabinogi.tweaked.api.objects;

import net.minecraft.item.ItemStack;

public interface ITweakedIngredient
{
	Object getItem();
	
	boolean matches(ItemStack stack);
}
