package com.mabinogi.tweaked.mods.jei;

import com.google.common.collect.Lists;
import com.mabinogi.tweaked.helpers.AnvilHelper;
import com.mabinogi.tweaked.helpers.AnvilHelper.TweakedAnvilType;
import com.mabinogi.tweaked.helpers.anvil.TweakedAnvilRecipe;
import com.mabinogi.tweaked.script.ScriptHelper;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IVanillaRecipeFactory;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.ingredients.Ingredients;
import mezz.jei.plugins.vanilla.anvil.AnvilRecipeMaker;
import mezz.jei.plugins.vanilla.anvil.AnvilRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

@mezz.jei.api.JEIPlugin
public class Plugin_JEI implements IModPlugin {

	private static IModRegistry registry;
	public static IIngredientRegistry itemRegistry;
	
	@Override
    public void register(IModRegistry registry) 
	{
		Plugin_JEI.registry = registry;
		Plugin_JEI.itemRegistry = registry.getIngredientRegistry();

        //register anvil recipes
		if (AnvilHelper.hasAdditions())
		{
			registry.addRecipes(getAnvilWrappers(), VanillaRecipeCategoryUid.ANVIL);
		}
	}

	private List<IRecipeWrapper> getAnvilWrappers()
	{
		IVanillaRecipeFactory factory = registry.getJeiHelpers().getVanillaRecipeFactory();

		List<IRecipeWrapper> wrappers = Lists.newArrayList();
		for (TweakedAnvilRecipe recipe : AnvilHelper.getAdditions())
		{
			wrappers.add(factory.createAnvilRecipe(recipe.getLeft(), Collections.singletonList(recipe.getRight()), Collections.singletonList(recipe.getOutput())));
		}
		return wrappers;
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime)
	{
		//modify anvil wrappers
		if (AnvilHelper.hasChanges())
		{
			IRecipeCategory<?> categoryAnvil = jeiRuntime.getRecipeRegistry().getRecipeCategory(VanillaRecipeCategoryUid.ANVIL);
			for (IRecipeWrapper wrapper : jeiRuntime.getRecipeRegistry().getRecipeWrappers(categoryAnvil))
			{
				if (wrapper instanceof AnvilRecipeWrapper)
				{
					hideAnvilRecipes(jeiRuntime, (AnvilRecipeWrapper) wrapper);
				}
			}
		}
	}

	public void hideAnvilRecipes(IJeiRuntime jeiRuntime, AnvilRecipeWrapper wrapper)
	{
		//get ingredients
		IIngredients ingredients = new Ingredients();
		wrapper.getIngredients(ingredients);

		//check ingredients to see if we need to hide them
		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		if (inputs.size() == 2)
		{
			if(!inputs.get(0).isEmpty() && !inputs.get(1).isEmpty())
			{
				ItemStack left = inputs.get(0).get(0);
				ItemStack right = inputs.get(1).get(0);

				//start by checking if the user added the item, if so it always shows
				if (AnvilHelper.hasAdditions())
				{
					for (TweakedAnvilRecipe recipe : AnvilHelper.getAdditions())
					{
						if (recipe.matches(left, right))
						{
							//this was an added recipe, make sure we show it
							return;
						}
					}
				}

				//clear recipes first
				if (AnvilHelper.isClear())
				{
					jeiRuntime.getRecipeRegistry().hideRecipe(wrapper, VanillaRecipeCategoryUid.ANVIL);
					return;
				}

				//next check if it matches a disable
				TweakedAnvilType type = AnvilHelper.getAnvilRecipeType(left, right);
				if (type == TweakedAnvilType.INVALID)
				{
					//items that cost 0 shouldn't exist, JEI shouldn't show these
					jeiRuntime.getRecipeRegistry().hideRecipe(wrapper, VanillaRecipeCategoryUid.ANVIL);
					return;
				}
				else if (type == TweakedAnvilType.BOOK)
				{
					if (AnvilHelper.isDisableBooks())
					{
						jeiRuntime.getRecipeRegistry().hideRecipe(wrapper, VanillaRecipeCategoryUid.ANVIL);
						return;
					}
				}
				else if (type == TweakedAnvilType.REPAIR)
				{
					if (AnvilHelper.isDisableRepairs())
					{
						jeiRuntime.getRecipeRegistry().hideRecipe(wrapper, VanillaRecipeCategoryUid.ANVIL);
						return;
					}
				}
				else if (type == TweakedAnvilType.COMBINE_BOOK)
				{
					if (AnvilHelper.isDisableCombineBooks())
					{
						jeiRuntime.getRecipeRegistry().hideRecipe(wrapper, VanillaRecipeCategoryUid.ANVIL);
						return;
					}
				}
				else if (type == TweakedAnvilType.COMBINE_REPAIR)
				{
					if (AnvilHelper.isDisableCombineRepairs())
					{
						jeiRuntime.getRecipeRegistry().hideRecipe(wrapper, VanillaRecipeCategoryUid.ANVIL);
						return;
					}
				}

				//finally check if it has been removed
				if (AnvilHelper.hasRemovals())
				{
					for (ItemStack stack : AnvilHelper.getRemovals())
					{
						if (ScriptHelper.areScriptItemsEqual(stack, left) || ScriptHelper.areScriptItemsEqual(stack, right))
						{
							jeiRuntime.getRecipeRegistry().hideRecipe(wrapper, VanillaRecipeCategoryUid.ANVIL);
							return;
						}
					}
				}
			}
		}
	}
}
