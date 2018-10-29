package com.mabinogi.tweaked.mods.immersiveengineering.tests;

import blusunrize.immersiveengineering.api.crafting.CokeOvenRecipe;
import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;

public class Test_IE_CokeOven
{
	//**************************************************************************************//
	//										add												//
	//**************************************************************************************//

	@TweakedTest(modid="immersiveengineering")
	public static class Test_IE_CokeOven_Add implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "ie";
		}

		@Override
		public String getTestDescription()
		{
			return "ie (coke oven) - add";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			String[] scripts =
					{
							"tweak.ie.cokeoven.add(<minecraft:diamond>, <minecraft:coal>, 1000, 500);"
					};
			return scripts;
		}

		@Override
		public boolean runTest()
		{
			if (CokeOvenRecipe.recipeList.size() == 1)
			{
				//one recipe, passed
				return true;
			}
			else
			{
				//not one recipe, failed
				return false;
			}
		}
	}


	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//

	@TweakedTest(modid="immersiveengineering")
	public static class Test_IE_CokeOven_Remove implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "ie";
		}

		@Override
		public String getTestDescription()
		{
			return "ie (coke oven) - remove";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			String[] scripts =
					{
							"tweak.ie.cokeoven.remove(*);"
					};
			return scripts;
		}

		@Override
		public boolean runTest()
		{
			if (CokeOvenRecipe.recipeList.size() == 1)
			{
				//one recipe, passed
				return true;
			}
			else
			{
				//not one recipe, failed
				return false;
			}
		}
	}
}
