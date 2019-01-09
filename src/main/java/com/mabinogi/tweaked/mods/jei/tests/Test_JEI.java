package com.mabinogi.tweaked.mods.jei.tests;

import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import net.minecraft.world.World;

public class Test_JEI
{
	//**************************************************************************************//
	//										add												//
	//**************************************************************************************//

	@TweakedTest(modid="jei")
	public static class Test_JEI_Add implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "jei";
		}

		@Override
		public String getTestDescription()
		{
			return "jei - add";
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
							"tweak.jei.add(<minecraft:stone:0:1:{display:{Name: \"Test Stone\",Lore:[\"Test\", \"Lore\"]}}>);"
					};
			return scripts;
		}

		@Override
		public boolean runTest(World world)
		{
			//manual test, auto-pass
			return true;
		}
	}


	//**************************************************************************************//
	//										hide											//
	//**************************************************************************************//

	@TweakedTest(modid="jei")
	public static class Test_JEI_Hide implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "jei";
		}

		@Override
		public String getTestDescription()
		{
			return "jei - hide";
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
							"tweak.jei.hide(<minecraft:cobblestone>);"
					};
			return scripts;
		}

		@Override
		public boolean runTest(World world)
		{
			//manual test, auto-pass
			return true;
		}
	}


	//**************************************************************************************//
	//										addInfo											//
	//**************************************************************************************//

	@TweakedTest(modid="jei")
	public static class Test_JEI_AddInfo implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "jei";
		}

		@Override
		public String getTestDescription()
		{
			return "jei - addinfo";
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
							"tweak.jei.addinfo(<minecraft:diamond>, \"Test Information\");"
					};
			return scripts;
		}

		@Override
		public boolean runTest(World world)
		{
			//manual test, auto-pass
			return true;
		}
	}
}
