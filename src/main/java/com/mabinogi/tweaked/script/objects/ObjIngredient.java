package com.mabinogi.tweaked.script.objects;

import com.mabinogi.tweaked.api.objects.ITweakedIngredient;
import com.mabinogi.tweaked.mods.vanilla.Tweaked_Vanilla;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import static com.mabinogi.tweaked.Tweaked.LOG;

public class ObjIngredient
{
	public ITweakedIngredient ingredient;
	
	public ObjIngredient(String in)
	{
		//check first that mods have finished registering items
		if (!Tweaked_Vanilla.ITEMS_REGISTERED)
		{
			LOG.warn("Warning : Ingredients created before mod registry");
			return;
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
				ingredient = new ObjNull();
				return;
			}
			else if(OreDictionary.doesOreNameExist(in))
			{
				//ore dict, returns a RecipeDict
				ingredient = new ObjDict(in);
				return;
			}
		}
		else if (args.length == 2)
		{
			//basic item or block, returns a RecipeItem or RecipeBlock
			Block block = Block.REGISTRY.getObject(new ResourceLocation(in));
			if (block != null && block != Blocks.AIR)
			{
				ingredient = new ObjStack(block);
				return;
			}
			
			Item item = Item.REGISTRY.getObject(new ResourceLocation(in));
			if (item != null)
			{
				ingredient = new ObjStack(item);
				return;
			}
			
			//item doesnt exist
			return;
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
					ingredient = new ObjStack(block, meta);
					return;
				}
				
				Item item = Item.REGISTRY.getObject(new ResourceLocation(args[0] + ":" + args[1]));
				if (item != null)
				{
					ingredient = new ObjStack(item, meta);
					return;
				}
			}
			catch(NumberFormatException e)
			{
				//parse meta error
				return;
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
							return;
						}
					}
					ingredient =  stack;
					return;
				}
				
				Item item = Item.REGISTRY.getObject(new ResourceLocation(args[0] + ":" + args[1]));
				if (item != null)
				{
					ObjStack stack = new ObjStack(item, meta, count);
					if (nbt != null)
					{
						if (!stack.setNBT(nbt))
						{
							return;
						}
					}
					ingredient =  stack;
					return;
				}
			}
			catch(NumberFormatException e)
			{
				//parse meta/count error
				return;
			}
		}
	}

}
