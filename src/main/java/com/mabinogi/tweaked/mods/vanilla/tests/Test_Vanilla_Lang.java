package com.mabinogi.tweaked.mods.vanilla.tests;

import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import net.minecraft.init.Blocks;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public class Test_Vanilla_Lang
{

	//**************************************************************************************//
	//											set											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Lang_Set implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "lang";
		}

		@Override
		public String getTestDescription()
		{
			return "lang - set";
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
					"tweak.lang.set(<minecraft:torch>, \"Disabled Torch\");",
					"tweak.lang.set(\"entity.Villager.fishlord\", \"The Fishlord\");"
			};
		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean runTest(World world)
		{
			return Blocks.TORCH.getLocalizedName().equals("Disabled Torch") && (I18n.translateToLocal("entity.Villager.fishlord").equals("The Fishlord"));
		}
	}
}
