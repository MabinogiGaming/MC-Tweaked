package com.mabinogi.tweaked.mods.immersiveengineering.tests;

import blusunrize.immersiveengineering.api.crafting.CrusherRecipe;
import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import net.minecraft.init.Items;
import net.minecraft.world.World;

@SuppressWarnings("unused")
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
			return new String[] { "tweak.ie.crusher.add(<minecraft:emerald>, <minecraft:coal>, 1000);" };
		}

		@Override
		public boolean runTest(World world)
		{
			for (CrusherRecipe recipe : CrusherRecipe.recipeList)
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
			return new String[] { "tweak.ie.crusher.remove(*);" };
		}

		@Override
		public boolean runTest(World world)
		{
			return CrusherRecipe.recipeList.size() == 1;
		}
	}
}
