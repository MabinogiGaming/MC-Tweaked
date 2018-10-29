package com.mabinogi.tweaked.mods.vanilla.tests;

import com.mabinogi.tweaked.api.annotations.TweakedTest;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import com.mabinogi.tweaked.mods.vanilla.Tweaked_Vanilla;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

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
            return "recipes - remove torch";
        }

        @Override
        public String[] getVariables()
        {
            String[] variables =
                    {
                            "$varTorch = stack(<minecraft:torch>);"
                    };
            return variables;
        }

        @Override
        public String[] getActions()
        {
            String[] scripts =
                    {
                            "tweak.recipes.remove($varTorch);"
                    };
            return scripts;
        }

        @Override
        public boolean runTest()
        {
            //create an itemstack
            ItemStack stack = new ItemStack(Blocks.TORCH);

            //recipe search
            for (Map.Entry<ResourceLocation, IRecipe> recipe : Tweaked_Vanilla.RECIPE_REGISTRY.getEntries())
            {
                if (stack.isItemEqual(recipe.getValue().getRecipeOutput()))
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
            return "recipes - shaped command block";
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
				            "tweak.recipes.shaped(\"test_shaped_commandBlock\", <minecraft:command_block>, [[<minecraft:diamond>, <ingotIron>][<minecraft:coal:1>, <minecraft:coal:1>]]);"
		            };
        }

        @Override
        public boolean runTest()
        {
            //create an itemstack
            ItemStack stack = new ItemStack(Blocks.COMMAND_BLOCK);

            //count recipes
            int count = 0;
            for (Map.Entry<ResourceLocation, IRecipe> recipe : Tweaked_Vanilla.RECIPE_REGISTRY.getEntries())
            {
                if (stack.isItemEqual(recipe.getValue().getRecipeOutput()))
                {
                    count++;
                }
            }

            if (count == 2)
            {
                //we expect 1 shaped and 1 shapeless, passed
                return true;
            }
            //didnt find 2 recipes, failed
            return false;
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
            return "recipes - shapeless command block";
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
                            "tweak.recipes.shapeless(\"test_shapeless_commandBlock\", <minecraft:command_block>, [<minecraft:diamond>, <minecraft:coal:1>, <ingotIron>]);"
                    };
            return scripts;
        }

        @Override
        public boolean runTest()
        {
            //create an itemstack
            ItemStack stack = new ItemStack(Blocks.COMMAND_BLOCK);

            //count recipes
            int count = 0;
            for (Map.Entry<ResourceLocation, IRecipe> recipe : Tweaked_Vanilla.RECIPE_REGISTRY.getEntries())
            {
                if (stack.isItemEqual(recipe.getValue().getRecipeOutput()))
                {
                    count++;
                }
            }

            if (count == 2)
            {
                //we expect 1 shaped and 1 shapeless, passed
                return true;
            }
            //didnt find 2 recipes, failed
            return false;
        }
    }
}
