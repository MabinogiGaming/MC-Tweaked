package com.mabinogi.tweaked.mods.immersiveengineering.tests;

import blusunrize.immersiveengineering.api.crafting.CrusherRecipe;
import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;

public class Test_IE_Crusher
{
	//**************************************************************************************//
	//										add												//
	//**************************************************************************************//

	@TweakedTest(modid="immersiveengineering")
	public static class Test_IE_Crusher_Add implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "ie";
		}

		@Override
		public String getTestDescription()
		{
			return "ie (crusher) - add";
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
							"tweak.ie.crusher.add(<minecraft:diamond>, <minecraft:coal>, 1000);"
					};
			return scripts;
		}

		@Override
		public boolean runTest()
		{
			if (CrusherRecipe.recipeList.size() == 1)
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
	public static class Test_IE_Crusher_Remove implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "ie";
		}

		@Override
		public String getTestDescription()
		{
			return "ie (crusher) - remove";
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
							"tweak.ie.crusher.remove(*);"
					};
			return scripts;
		}

		@Override
		public boolean runTest()
		{
			if (CrusherRecipe.recipeList.size() == 1)
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
