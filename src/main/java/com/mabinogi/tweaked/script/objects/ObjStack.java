package com.mabinogi.tweaked.script.objects;

import static com.mabinogi.tweaked.Tweaked.LOG;

import com.mabinogi.tweaked.api.objects.IIngredient;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

public class ObjStack implements IIngredient
{	
	ItemStack stack = null;
	
	public ObjStack(Item item)
	{
		stack = new ItemStack(item);
	}
	
	public ObjStack(Block block)
	{
		stack = new ItemStack(block);
	}
	
	public ObjStack(Item item, int meta)
	{
		stack = new ItemStack(item, 1, meta);
	}
	
	public ObjStack(Block block, int meta)
	{
		stack = new ItemStack(block, 1, meta);
	}
	
	public ObjStack(Item item, int meta, int count)
	{
		stack = new ItemStack(item, count, meta);
	}
	
	public ObjStack(Block block, int meta, int count)
	{
		stack = new ItemStack(block, count, meta);
	}
	
	public boolean setNBT(String nbt)
	{
		try
		{
			NBTTagCompound nbtCompound = (NBTTagCompound) JsonToNBT.getTagFromJson(nbt);
			if (nbtCompound == null)
			{
				LOG.warn("Warning : Failed to parse NBT \"" + nbt + "\"");
				return false;
			}
			
			stack.setTagCompound(nbtCompound);
			return true;
		} 
		catch (NBTException e)
		{
			LOG.warn("Warning : Failed to parse NBT \"" + nbt + "\"");
			return false;
		}
	}

	@Override
	public Object getItem()
	{
		return stack;
	}
	
	public ItemStack getItemStack()
	{
		return stack;
	}

	@Override
	public boolean matches(ItemStack match)
	{
		return stack != null && stack.isItemEqual(match);
	}

}
