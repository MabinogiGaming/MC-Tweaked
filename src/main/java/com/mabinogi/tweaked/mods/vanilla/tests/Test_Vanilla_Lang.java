package com.mabinogi.tweaked.mods.vanilla.tests;

import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import net.minecraft.init.Blocks;

public class Test_Vanilla_Lang
{

	//**************************************************************************************//
	//										setName											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Lang_SetName_Stone implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "lang";
		}

		@Override
		public String getTestDescription()
		{
			return "lang - setName stone";
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
							"tweak.lang.setName(<minecraft:stone>, \"Test Stone\");"
					};
			return scripts;
		}

		@Override
		public boolean runTest()
		{
			if (Blocks.STONE.getLocalizedName().equals("Test Stone"))
			{
				//name match, passed
				return true;
			}
			//no match, failed
			return false;
		}
	}
}
