package com.mabinogi.tweaked.mods.immersiveengineering.tests;

import blusunrize.immersiveengineering.api.crafting.AlloyRecipe;
import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import net.minecraft.init.Items;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public class Test_IE_Alloy
{
	//**************************************************************************************//
	//										add												//
	//**************************************************************************************//

	@TweakedTest(modid="immersiveengineering")
	public static class Test_IE_Alloy_Add implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "ie";
		}

		@Override
		public String getTestDescription()
		{
			return "ie (alloy) - add";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.ie.alloy.add(<minecraft:emerald>, <minecraft:coal>, <ingotIron>, 1000);" };
		}

		@Override
		public boolean runTest(World world)
		{
			for (AlloyRecipe recipe : AlloyRecipe.recipeList)
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
	public static class Test_IE_Alloy_Remove implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "ie";
		}

		@Override
		public String getTestDescription()
		{
			return "ie (alloy) - remove";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.ie.alloy.remove(*);" };
		}

		@Override
		public boolean runTest(World world)
		{
			return AlloyRecipe.recipeList.size() <= 1;
		}
	}
}
