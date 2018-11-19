package com.mabinogi.tweaked.mods.vanilla.tests;

import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public class Test_Vanilla_Anvil
{
	//**************************************************************************************//
	//											add											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Anvil_Add implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "anvil";
		}

		@Override
		public String getTestDescription()
		{
			return "anvil - add";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] {
				"tweak.anvil.add(<minecraft:torch>, <minecraft:diamond>, <minecraft:obsidian>, 2);",
				"tweak.anvil.add(<minecraft:apple>, <minecraft:carrot>, <minecraft:diamond>, 5);",
			};
		}

		@Override
		public boolean runTest(World world)
		{
			//manual test, auto-pass
			return true;
		}
	}


	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Anvil_Remove implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "anvil";
		}

		@Override
		public String getTestDescription()
		{
			return "anvil - remove";
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
				"tweak.anvil.remove(<minecraft:iron_sword>);",
				"tweak.anvil.remove(<minecraft:iron_shovel>);",
				"tweak.anvil.remove(<minecraft:iron_pickaxe>);",
				"tweak.anvil.remove(<minecraft:iron_axe>);",
				"tweak.anvil.remove(<minecraft:iron_hoe>);"
			};
		}

		@Override
		public boolean runTest(World world)
		{
			//manual test, auto-pass
			return true;
		}
	}


	//**************************************************************************************//
	//										disable											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Anvil_Disable implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "anvil";
		}

		@Override
		public String getTestDescription()
		{
			return "anvil - disable";
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
				"tweak.anvil.disable(\"combinerepairs\");",
				"tweak.anvil.disable(\"combinebooks\");",
				"tweak.anvil.disable(\"books\");"
			};
		}

		@Override
		public boolean runTest(World world)
		{
			//manual test, auto-pass
			return true;
		}
	}


	//**************************************************************************************//
	//										breakChance										//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Anvil_BreakChance implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "anvil";
		}

		@Override
		public String getTestDescription()
		{
			return "anvil - breakchance";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.anvil.setbreakchance(0.5);" };
		}

		@Override
		public boolean runTest(World world)
		{
			//manual test, auto-pass
			return true;
		}
	}
}
