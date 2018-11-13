package com.mabinogi.tweaked.mods.vanilla.tests;

import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

@SuppressWarnings("unused")
public class Test_Vanilla_Dict
{
	//**************************************************************************************//
	//											add											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Dict_Add implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "dict";
		}

		@Override
		public String getTestDescription()
		{
			return "dict - add";
		}

		@Override
		public String[] getVariables()
		{
			return new String[] { "$varList = stackList(<minecraft:dirt>, <minecraft:cobblestone>);" };
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.dict.add(\"stone\", $varList);" };
		}

		@Override
		public boolean runTest(World world)
		{
			NonNullList<ItemStack> stackList = OreDictionary.getOres("stone");

			boolean matchDirt = false;
			boolean matchCobble = false;
			for (ItemStack stack : stackList)
			{
				if (Block.getBlockFromItem(stack.getItem()) == Blocks.DIRT)
				{
					matchDirt = true;
				}
				else if (Block.getBlockFromItem(stack.getItem()) == Blocks.COBBLESTONE)
				{
					matchCobble = true;
				}
			}
			return (matchDirt && matchCobble);
		}
	}

	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Dict_Remove implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "dict";
		}

		@Override
		public String getTestDescription()
		{
			return "dict - remove";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.dict.remove(\"chest\", <minecraft:trapped_chest>);" };
		}

		@Override
		public boolean runTest(World world)
		{
			NonNullList<ItemStack> stackList = OreDictionary.getOres("chest");
			for (ItemStack stack : stackList)
			{
				if (Block.getBlockFromItem(stack.getItem()) == Blocks.TRAPPED_CHEST)
				{
					//trapped chest hasn't been removed, failed
					return false;
				}
			}
			return true;
		}
	}


	//**************************************************************************************//
	//										replace											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Dict_Replace implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "dict";
		}

		@Override
		public String getTestDescription()
		{
			return "dict - replace";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.dict.replace(\"plankWood\", <minecraft:planks:0>);" };
		}

		@Override
		public boolean runTest(World world)
		{
			NonNullList<ItemStack> stackList = OreDictionary.getOres("plankWood");
			return stackList.size() == 1;
		}
	}
}
