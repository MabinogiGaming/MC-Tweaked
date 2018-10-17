package com.mabinogi.tweaked.script.builders;

import static com.mabinogi.tweaked.Tweaked.LOG;

import com.mabinogi.tweaked.TweakedController;
import com.mabinogi.tweaked.script.objects.ObjDict;
import com.mabinogi.tweaked.script.objects.ObjNull;
import com.mabinogi.tweaked.script.objects.ObjStack;
import com.mabinogi.tweaked.script.objects.iface.IIngredient;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class IngredientBuilder 
{	
	public static IIngredient build(String in)
	{
		//check first that mods have finished registering items
		if (!TweakedController.ITEMS_REGISTERED)
		{
			LOG.warn("Warning : Ingredients created before mod registry");
			return null;
		}
		
		//extract nbt if it ends with it
		String nbt = null;
		if (in.endsWith("}") && in.contains("{"))
		{
			nbt = in.substring(in.indexOf("{"), in.length());
			
			in = in.substring(0, in.indexOf("{") - 1);
		}
		
		//split the input
		String[] args = in.split(":");
		
		if (args.length == 1)
		{
			if (args[0].isEmpty())
			{
				//null, returns a RecipeNull
				return new ObjNull();
			}
			else if(OreDictionary.doesOreNameExist(in))
			{
				//ore dict, returns a RecipeDict
				return new ObjDict(in);
			}
		}
		else if (args.length == 2)
		{
			//basic item or block, returns a RecipeItem or RecipeBlock
			Block block = Block.REGISTRY.getObject(new ResourceLocation(in));
			if (block != null && block != Blocks.AIR)
			{
				return new ObjStack(block);
			}
			
			Item item = Item.REGISTRY.getObject(new ResourceLocation(in));
			if (item != null)
			{
				return new ObjStack(item);
			}
			
			//item doesnt exist
			return null;
		}
		else if (args.length == 3)
		{

			try 
			{
				//get metadata
				int meta = Integer.parseInt(args[2]);
				
				//basic item or block with metadata, returns a RecipeStack
				Block block = Block.REGISTRY.getObject(new ResourceLocation(args[0] + ":" + args[1]));
				if (block != null && block != Blocks.AIR)
				{
					return new ObjStack(block, meta);
				}
				
				Item item = Item.REGISTRY.getObject(new ResourceLocation(args[0] + ":" + args[1]));
				if (item != null)
				{
					return new ObjStack(item, meta);
				}
			}
			catch(NumberFormatException e)
			{
				//parse meta error
				return null;
			}
		}
		else if (args.length == 4)
		{

			try 
			{
				//get metadata
				int meta = Integer.parseInt(args[2]);
				
				//get count
				int count = Integer.parseInt(args[3]);
				
				//basic item or block with metadata, returns a RecipeStack
				Block block = Block.REGISTRY.getObject(new ResourceLocation(args[0] + ":" + args[1]));
				if (block != null && block != Blocks.AIR)
				{
					ObjStack stack = new ObjStack(block, meta, count);
					if (nbt != null)
					{
						if (!stack.setNBT(nbt))
						{
							return null;
						}
					}
					return stack;
				}
				
				Item item = Item.REGISTRY.getObject(new ResourceLocation(args[0] + ":" + args[1]));
				if (item != null)
				{
					ObjStack stack = new ObjStack(item, meta, count);
					if (nbt != null)
					{
						if (!stack.setNBT(nbt))
						{
							return null;
						}
					}
					return stack;
				}
			}
			catch(NumberFormatException e)
			{
				//parse meta/count error
				return null;
			}
		}
		
		return null;
	}
}
