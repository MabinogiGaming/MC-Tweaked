package com.mabinogi.tweaked.mods.immersiveengineering.tests;

import blusunrize.immersiveengineering.api.tool.ExcavatorHandler;
import blusunrize.immersiveengineering.api.tool.ExcavatorHandler.MineralMix;
import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public class Test_IE_Excavator
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
			return "ie (excavator) - add";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[]
			{
				"tweak.ie.excavator.add(\"Pure Iron\", 25, 0.1, \"oreIron\", 1.0);"
			};
		}

		@Override
		public boolean runTest(World world)
		{
			for (MineralMix mix : ExcavatorHandler.mineralList.keySet())
			{
				if (mix.name.equals("Pure Iron"))
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
	public static class Test_IE_Excavator_Remove implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "ie";
		}

		@Override
		public String getTestDescription()
		{
			return "ie (excavator) - remove";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[]
			{
				"tweak.ie.excavator.remove(*);"
			};
		}

		@Override
		public boolean runTest(World world)
		{
			return ExcavatorHandler.mineralList.size() <= 1;
		}
	}
}
