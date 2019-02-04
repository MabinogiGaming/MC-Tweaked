package com.mabinogi.tweaked.mods.immersiveengineering.tests;

import blusunrize.immersiveengineering.api.crafting.BlastFurnaceRecipe;
import blusunrize.immersiveengineering.api.crafting.BlastFurnaceRecipe.BlastFurnaceFuel;
import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import net.minecraft.init.Items;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public class Test_IE_BlastFurnace
{
	//**************************************************************************************//
	//										add												//
	//**************************************************************************************//

	@TweakedTest(modid="immersiveengineering")
	public static class Test_IE_BlastFurnace_Add implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "ie";
		}

		@Override
		public String getTestDescription()
		{
			return "ie (blast furnace) - add";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.ie.blastfurnace.add(<minecraft:emerald>, <minecraft:coal>, 1000, <minecraft:diamond>);" };
		}

		@Override
		public boolean runTest(World world)
		{
			for (BlastFurnaceRecipe recipe : BlastFurnaceRecipe.recipeList)
			{
				if (recipe.output.getItem() == Items.EMERALD)
				{
					return true;
				}
			}
			return false;
		}
	}


	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//

	@TweakedTest(modid="immersiveengineering")
	public static class Test_IE_BlastFurnace_Remove implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "ie";
		}

		@Override
		public String getTestDescription()
		{
			return "ie (blast furnace) - remove";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.ie.blastfurnace.remove(*);" };
		}

		@Override
		public boolean runTest(World world)
		{
			return BlastFurnaceRecipe.recipeList.size() <= 1;
		}
	}


	//**************************************************************************************//
	//										add fuel										//
	//**************************************************************************************//

	@TweakedTest(modid="immersiveengineering")
	public static class Test_IE_BlastFurnace_AddFuel implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "ie";
		}

		@Override
		public String getTestDescription()
		{
			return "ie (blast furnace) - add fuel";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.ie.blastfurnace.addFuel(<minecraft:apple>, 1000);" };
		}

		@Override
		public boolean runTest(World world)
		{
			for (BlastFurnaceFuel fuel : BlastFurnaceRecipe.blastFuels)
			{
				if (fuel.input.stack.getItem() == Items.APPLE)
				{
					return true;
				}
			}
			return false;
		}
	}


	//**************************************************************************************//
	//										remove fuel										//
	//**************************************************************************************//

	@TweakedTest(modid="immersiveengineering")
	public static class Test_IE_BlastFurnace_RemoveFuel implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "ie";
		}

		@Override
		public String getTestDescription()
		{
			return "ie (blast furnace) - remove fuel";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.ie.blastfurnace.removeFuel(*);" };
		}

		@Override
		public boolean runTest(World world)
		{
			return BlastFurnaceRecipe.blastFuels.size() <= 1;
		}
	}
}
