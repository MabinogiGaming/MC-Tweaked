package com.mabinogi.tweaked.mods.vanilla.tests;

import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

import java.util.Map;

@SuppressWarnings("unused")
public class Test_Vanilla_Furnace
{
	//**************************************************************************************//
	//											add											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Furnace_ADD implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "furnace";
		}

		@Override
		public String getTestDescription()
		{
			return "furnace - add";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] {
					"tweak.furnace.add(<minecraft:cobblestone>, <minecraft:stone>);",
					"tweak.furnace.add(<minecraft:dirt>, <minecraft:diamond>, 4.0);"
			};
		}

		@Override
		public boolean runTest(World world)
		{
			boolean stoneFound = false;
			boolean diamondFound = false;

			for (Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet())
			{
				if (entry.getKey().getItem().equals(Item.getItemFromBlock(Blocks.COBBLESTONE)))
				{
					if (entry.getValue().getItem().equals(Item.getItemFromBlock(Blocks.STONE)))
					{
						stoneFound = true;
					}
				}
				else if (entry.getKey().getItem().equals(Item.getItemFromBlock(Blocks.DIRT)))
				{
					if (entry.getValue().getItem().equals(Items.DIAMOND))
					{
						diamondFound = true;
					}
				}
				else
				{
					return false;
				}
			}
			return stoneFound && diamondFound;
		}
	}


	//**************************************************************************************//
	//											remove										//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Furnace_Remove implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "furnace";
		}

		@Override
		public String getTestDescription()
		{
			return "furnace - remove";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] {
					"tweak.furnace.remove(*);"
			};
		}

		@Override
		public boolean runTest(World world)
		{
			return FurnaceRecipes.instance().getSmeltingList().size() == 2;
		}
	}


	//**************************************************************************************//
	//										addFuel											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Furnace_AddFuel implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "furnace";
		}

		@Override
		public String getTestDescription()
		{
			return "furnace - addfuel";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] {
					"tweak.furnace.addFuel(<minecraft:diamond>, 1000);"
			};
		}

		@Override
		public boolean runTest(World world)
		{
			return TileEntityFurnace.getItemBurnTime(new ItemStack(Items.DIAMOND)) == 1000;
		}
	}


	//**************************************************************************************//
	//										removeFuel										//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Furnace_RemoveFuel implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "furnace";
		}

		@Override
		public String getTestDescription()
		{
			return "furnace - removefuel";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] {
					"tweak.furnace.removeFuel(*);"
			};
		}

		@Override
		public boolean runTest(World world)
		{
			return TileEntityFurnace.getItemBurnTime(new ItemStack(Items.COAL)) == 0;
		}
	}
}
