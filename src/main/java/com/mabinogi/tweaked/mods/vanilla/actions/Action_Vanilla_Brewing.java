package com.mabinogi.tweaked.mods.vanilla.actions;

import com.mabinogi.tweaked.api.actions.ActionAbstract;
import com.mabinogi.tweaked.api.annotations.TweakedAction;
import com.mabinogi.tweaked.controllers.TweakedReflection;
import com.mabinogi.tweaked.helpers.brewing.TweakedBrewingRecipe;
import com.mabinogi.tweaked.helpers.brewing.TweakedBrewingRemover;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.objects.ObjAll;
import com.mabinogi.tweaked.script.objects.ObjStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.common.brewing.VanillaBrewingRecipe;

import java.util.ArrayList;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.*;

@SuppressWarnings("unused")
public class Action_Vanilla_Brewing
{
	public static Action_Brewing_Add ADD = null;
	public static Action_Brewing_Remove REMOVE = null;


	//**************************************************************************************//
	//											add											//
	//**************************************************************************************//

	@TweakedAction("brewing.add")
	public static class Action_Brewing_Add extends ActionAbstract
	{
		private boolean CLEAR = false;
		private List<TweakedBrewingRecipe> RECIPES = new ArrayList<>();

		public Action_Brewing_Add()
		{
			ADD = this;
		}

		public void build(ObjStack input, ObjStack container, ObjStack output)
		{
			RECIPES.add(new TweakedBrewingRecipe(container.getItemStack(), input.getItemStack(), output.getItemStack()));
		}

		@Override
		protected void run()
		{
			List<IBrewingRecipe> brewingRecipes = TweakedReflection.getBrewingRecipes();
			for (TweakedBrewingRecipe recipe : RECIPES)
			{
				brewingRecipes.add(recipe);

				//debug
				LOG.debug("brewing.add : " + ScriptHelper.stackToScript((ItemStack) recipe.getIngredient()) + " + " + ScriptHelper.stackToScript(recipe.getInput()) + " = " + ScriptHelper.stackToScript(recipe.getOutput()));
			}

			//cleanup
			RECIPES = null;
		}
	}
	
	
	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//

	@TweakedAction("brewing.remove")
	public static class Action_Brewing_Remove extends ActionAbstract
	{
		private boolean CLEAR = false;
		private List<ItemStack> INPUTS = new ArrayList<>();
		
		public Action_Brewing_Remove()
		{
			REMOVE = this;
		}

		public void build(ObjAll all)
		{
			CLEAR = true;
		}

		public void build(ObjStack input)
		{
			INPUTS.add(input.getItemStack());
		}
		
		@Override
		protected void run()
		{
			if (CLEAR)
			{
				List<IBrewingRecipe> brewingRecipes = TweakedReflection.getBrewingRecipes();
				brewingRecipes.removeIf(VanillaBrewingRecipe.class::isInstance);
				brewingRecipes.add(new TweakedBrewingRemover(CLEAR));

				//debug
				LOG.debug("brewing.remove : all");
			}
			else if (!INPUTS.isEmpty())
			{
				List<IBrewingRecipe> brewingRecipes = TweakedReflection.getBrewingRecipes();
				brewingRecipes.removeIf(VanillaBrewingRecipe.class::isInstance);
				brewingRecipes.add(new TweakedBrewingRemover(INPUTS));

				//debug
				for (ItemStack input : INPUTS)
				{
					LOG.debug("brewing.remove : " + ScriptHelper.stackToScript(input));
				}
			}

			//cleanup
			INPUTS = null;
		}
	}

}
