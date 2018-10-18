package com.mabinogi.tweaked.script.objects;

import com.mabinogi.tweaked.api.objects.IIngredient;

import net.minecraft.item.ItemStack;

public class ObjNull implements IIngredient {

	@Override
	public Object getItem()
	{
		return null;
	}

	@Override
	public boolean matches(ItemStack stack)
	{
		return false;
	}

}
