package com.mabinogi.tweaked.mods.immersiveengineering.tests;

import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import com.mabinogi.tweaked.mods.immersiveengineering.events.Events_IE;
import net.minecraft.world.World;

@SuppressWarnings("unused")
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
			return new String[] { "tweak.ie.disableMultiblock(*);" };
		}

		@Override
		public boolean runTest(World world)
		{
			return Events_IE.MULTIBLOCK_BLACKLIST_ALL;
		}
	}
}
