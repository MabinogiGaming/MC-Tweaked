package com.mabinogi.tweaked.script.objects;

import com.mabinogi.tweaked.api.objects.ITweakedIngredient;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.loot.RandomValueRange;

import java.util.Random;

import static com.mabinogi.tweaked.Tweaked.LOG;

public class ObjStack implements ITweakedIngredient
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
		if (stack == null) return false;

		if (stack.hasTagCompound())
		{
			return ItemStack.areItemStacksEqual(stack, match);
		}
		else
		{
			return ItemStack.areItemsEqual(stack, match);
		}
	}

	public void addEnchantment(Enchantment enchant, int level)
	{
		if (stack == null) return;

		stack.addEnchantment(enchant, level);
	}

}
