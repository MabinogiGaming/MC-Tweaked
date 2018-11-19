package com.mabinogi.tweaked.mods.vanilla.tests;

import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import com.mabinogi.tweaked.mods.vanilla.Tweaked_Vanilla;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Map;

@SuppressWarnings("unused")
public class Test_Vanilla_Recipes
{
    //**************************************************************************************//
    //										remove											//
    //**************************************************************************************//

    @TweakedTest()
    public static class Test_Recipes_Remove_Torch implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "recipes";
        }

        @Override
        public String getTestDescription()
        {
            return "recipes - remove";
        }

        @Override
        public String[] getVariables()
        {
        	return new String[] { "$varTorch = stack(<minecraft:torch>);" };
        }

        @Override
        public String[] getActions()
        {
			return new String[] { "tweak.recipes.remove($varTorch);" };
        }

        @Override
        public boolean runTest(World world)
        {
            //create an itemstack
            ItemStack stack = new ItemStack(Blocks.TORCH);

            //recipe search
            for (Map.Entry<ResourceLocation, IRecipe> recipe : Tweaked_Vanilla.RECIPE_REGISTRY.getEntries())
            {
                if (ItemStack.areItemsEqual(stack, recipe.getValue().getRecipeOutput()))
                {
                    //recipes still exist, failed
                    return false;
                }
            }

            //no recipes exist, passed
            return true;
        }
    }


    //**************************************************************************************//
    //										shaped											//
    //**************************************************************************************//

    @TweakedTest()
    public static class Test_Recipes_Shaped_CommandBlock implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "recipes";
        }

        @Override
        public String getTestDescription()
        {
            return "recipes - shaped";
        }

        @Override
        public String[] getVariables()
        {
            return new String[0];
        }

        @Override
        public String[] getActions()
        {
            return new String[] { "tweak.recipes.shaped(\"test_shaped_commandBlock\", <minecraft:command_block>, [[<minecraft:diamond>, <ingotIron>][<minecraft:coal>, <minecraft:coal>]]);" };
        }

        @Override
        public boolean runTest(World world)
        {
            //create an itemstack
            ItemStack stack = new ItemStack(Blocks.COMMAND_BLOCK);

            //count recipes
            int count = 0;
            for (Map.Entry<ResourceLocation, IRecipe> recipe : Tweaked_Vanilla.RECIPE_REGISTRY.getEntries())
            {
                if (ItemStack.areItemsEqual(stack, recipe.getValue().getRecipeOutput()))
                {
                    count++;
                }
            }

            return count == 2;
        }
    }


    //**************************************************************************************//
    //										shapeless										//
    //**************************************************************************************//

    @TweakedTest()
    public static class Test_Recipes_Shapeless_CommandBlock implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "recipes";
        }

        @Override
        public String getTestDescription()
        {
            return "recipes - shapeless";
        }

        @Override
        public String[] getVariables()
        {
            return new String[0];
        }

        @Override
        public String[] getActions()
        {
        	return new String[] { "tweak.recipes.shapeless(\"test_shapeless_commandBlock\", <minecraft:command_block>, [<minecraft:diamond>, <minecraft:coal:1>, <ingotIron>]);" };
        }

        @Override
        public boolean runTest(World world)
        {
            //create an itemstack
            ItemStack stack = new ItemStack(Blocks.COMMAND_BLOCK);

            //count recipes
            int count = 0;
            for (Map.Entry<ResourceLocation, IRecipe> recipe : Tweaked_Vanilla.RECIPE_REGISTRY.getEntries())
            {
                if (ItemStack.areItemsEqual(stack, recipe.getValue().getRecipeOutput()))
                {
                    count++;
                }
            }

            return count == 2;
        }
    }
}
