package com.mabinogi.tweaked.mods.vanilla.tests;

import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public class Test_Vanilla_Lang
{

	//**************************************************************************************//
	//										setName											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Lang_SetName implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "lang";
		}

		@Override
		public String getTestDescription()
		{
			return "lang - setName";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.lang.setName(<minecraft:torch>, \"Disabled Torch\");" };
		}

		@Override
		public boolean runTest(World world)
		{
			return Blocks.TORCH.getLocalizedName().equals("Disabled Torch");
		}
	}
}
