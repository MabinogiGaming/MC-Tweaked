package com.mabinogi.tweaked.mods.immersiveengineering.tests;

import blusunrize.immersiveengineering.api.crafting.BlastFurnaceRecipe;
import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;

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
			String[] scripts =
					{
							"tweak.ie.blastfurnace.add(<minecraft:diamond>, <minecraft:coal>, 1000, <minecraft:emerald>);"
					};
			return scripts;
		}

		@Override
		public boolean runTest()
		{
			if (BlastFurnaceRecipe.recipeList.size() == 1)
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
			String[] scripts =
					{
							"tweak.ie.blastfurnace.remove(*);"
					};
			return scripts;
		}

		@Override
		public boolean runTest()
		{
			if (BlastFurnaceRecipe.recipeList.size() == 1)
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
			String[] scripts =
					{
							"tweak.ie.blastfurnace.addFuel(<minecraft:apple>, 1000);"
					};
			return scripts;
		}

		@Override
		public boolean runTest()
		{
			if (BlastFurnaceRecipe.blastFuels.size() == 1)
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
			String[] scripts =
					{
							"tweak.ie.blastfurnace.removeFuel(*);"
					};
			return scripts;
		}

		@Override
		public boolean runTest()
		{
			if (BlastFurnaceRecipe.blastFuels.size() == 1)
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
