package com.mabinogi.tweaked.mods.immersiveengineering.tests;

import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import com.mabinogi.tweaked.mods.immersiveengineering.events.Events_IE;

public class Test_IE
{
	//**************************************************************************************//
	//								disableMultiblock										//
	//**************************************************************************************//

	@TweakedTest(modid="immersiveengineering")
	public static class Test_IE_DisableMultiblocks implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "ie";
		}

		@Override
		public String getTestDescription()
		{
			return "ie - disable mutliblocks";
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
							"tweak.ie.disableMultiblock(*);"
					};
			return scripts;
		}

		@Override
		public boolean runTest()
		{
			if (Events_IE.MULTIBLOCK_BLACKLIST_ALL)
			{
				//no multiblocks, passed
				return true;
			}
			else
			{
				//still multiblocks, failed
				return false;
			}
		}
	}
}
