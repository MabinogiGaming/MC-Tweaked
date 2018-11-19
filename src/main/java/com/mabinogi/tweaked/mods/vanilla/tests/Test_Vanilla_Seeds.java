package com.mabinogi.tweaked.mods.vanilla.tests;

import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import com.mabinogi.tweaked.controllers.TweakedReflection;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public class Test_Vanilla_Seeds
{

	//**************************************************************************************//
	//											add											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Seeds_Add implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "seeds";
		}

		@Override
		public String getTestDescription()
		{
			return "seeds - add";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.seeds.add(<minecraft:diamond>, 10);" };
		}

		@Override
		public boolean runTest(World world)
		{
			for(WeightedRandom.Item entry : TweakedReflection.getSeedEntries())
			{
				ItemStack stack = TweakedReflection.getSeedStack(entry);
				if (stack.getItem() == Items.DIAMOND)
				{
					return true;
				}
			}
			//didn't find diamonds, fail
			return false;
		}
	}


	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Seeds_Remove implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "seeds";
		}

		@Override
		public String getTestDescription()
		{
			return "seeds - remove";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.seeds.remove(<minecraft:wheat_seeds>);" };
		}

		@Override
		public boolean runTest(World world)
		{
			for(WeightedRandom.Item entry : TweakedReflection.getSeedEntries())
			{
				ItemStack stack = TweakedReflection.getSeedStack(entry);
				if (stack.getItem() == Items.WHEAT_SEEDS)
				{
					//still contains seeds, fail
					return false;
				}
			}
			return true;
		}
	}
}
