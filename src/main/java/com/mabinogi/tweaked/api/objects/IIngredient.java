package com.mabinogi.tweaked.api.objects;

import net.minecraft.item.ItemStack;

public interface IIngredient {
	
	public Object getItem();
	
	public boolean matches(ItemStack stack);

}
