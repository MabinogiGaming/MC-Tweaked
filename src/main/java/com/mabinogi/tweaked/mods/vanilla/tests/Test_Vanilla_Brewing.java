package com.mabinogi.tweaked.mods.vanilla.tests;

import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

@SuppressWarnings("unused")
public class Test_Vanilla_Brewing
{
	//**************************************************************************************//
	//											add											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Brewing_Add implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "brewing";
		}

		@Override
		public String getTestDescription()
		{
			return "brewing - add";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.brewing.add(<minecraft:diamond>, <minecraft:potion:0:1:{Potion:\"minecraft:water\"}>, <minecraft:potion:0:1:{Potion:\"minecraft:strong_leaping\"}>);" };
		}

		@Override
		public boolean runTest(World world)
		{
			return BrewingRecipeRegistry.isValidIngredient(new ItemStack(Items.DIAMOND));
		}
	}


	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//

	@TweakedTest()
	public static class Test_Brewing_Remove implements ITweakedTest
	{
		@Override
		public String getFilename()
		{
			return "brewing";
		}

		@Override
		public String getTestDescription()
		{
			return "brewing - remove";
		}

		@Override
		public String[] getVariables()
		{
			return new String[0];
		}

		@Override
		public String[] getActions()
		{
			return new String[] { "tweak.brewing.remove(<minecraft:sugar>);" };
		}

		@Override
		public boolean runTest(World world)
		{
			return !BrewingRecipeRegistry.isValidIngredient(new ItemStack(Items.SUGAR));
		}
	}
}
