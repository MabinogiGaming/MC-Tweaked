package com.mabinogi.tweaked.script.objects;

import com.mabinogi.tweaked.script.objects.iface.IIngredient;

import net.minecraft.item.ItemStack;

public class ObjDict implements IIngredient {
	
	public String dict = null;
	
	public ObjDict(String dict)
	{
		this.dict = dict;
	}

	@Override
	public Object getItem()
	{
		return dict;
	}

	@Override
	public boolean matches(ItemStack stack)
	{
		return false;
	}

}
