package com.mabinogi.tweaked.script.objects.iface;

import net.minecraft.item.ItemStack;

public interface IIngredient {
	
	public Object getItem();
	
	public boolean matches(ItemStack stack);

}
