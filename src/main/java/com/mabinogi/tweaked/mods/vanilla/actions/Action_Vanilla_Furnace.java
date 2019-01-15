package com.mabinogi.tweaked.mods.vanilla.actions;

import com.google.common.collect.Maps;
import com.mabinogi.tweaked.api.actions.ActionAbstract;
import com.mabinogi.tweaked.api.annotations.TweakedAction;
import com.mabinogi.tweaked.helpers.FuelHelper;
import com.mabinogi.tweaked.helpers.furnace.TweakedFurnaceRecipe;
import com.mabinogi.tweaked.script.ScriptHelper;
import com.mabinogi.tweaked.script.objects.ObjAll;
import com.mabinogi.tweaked.script.objects.ObjStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mabinogi.tweaked.Tweaked.LOG;

public class Action_Vanilla_Furnace
{
	public static Action_Furnace_Add ADD = null;
	public static Action_Furnace_Remove REMOVE = null;
	public static Action_Furnace_AddFuel ADD_FUEL = null;
	public static Action_Furnace_RemoveFuel REMOVE_FUEL = null;


	//**************************************************************************************//
	//										add												//
	//**************************************************************************************//

	@TweakedAction("furnace.add")
	public static class Action_Furnace_Add extends ActionAbstract
	{
		private List<TweakedFurnaceRecipe> RECIPES = new ArrayList<>();

		public Action_Furnace_Add()
		{
			ADD = this;
		}

		public void build(ObjStack input, ObjStack output)
		{
			RECIPES.add(new TweakedFurnaceRecipe(input.getItemStack(), output.getItemStack(), 0.0f));
		}

		public void build(ObjStack input, ObjStack output, Float experience)
		{
			RECIPES.add(new TweakedFurnaceRecipe(input.getItemStack(), output.getItemStack(), experience));
		}

		@Override
		protected void run()
		{
			if (!RECIPES.isEmpty())
			{
				for (TweakedFurnaceRecipe recipe : RECIPES)
				{
					FurnaceRecipes.instance().addSmeltingRecipe(recipe.getInput(), recipe.getOutput(), recipe.getExperience());

					//debug
					LOG.debug("furnace.add : " + ScriptHelper.stackToScript(recipe.getInput()) + " = " + ScriptHelper.stackToScript(recipe.getOutput()));
				}
			}

			//cleanup
			RECIPES = null;
		}
	}


	//**************************************************************************************//
	//										remove											//
	//**************************************************************************************//

	@TweakedAction("furnace.remove")
	public static class Action_Furnace_Remove extends ActionAbstract
	{
		private boolean CLEAR = false;
		private List<ItemStack> OUTPUTS = new ArrayList<>();

		public Action_Furnace_Remove()
		{
			REMOVE = this;
		}

		@SuppressWarnings("unused")
		public void build(ObjAll all)
		{
			CLEAR = true;
		}

		public void build(ObjStack output)
		{
			OUTPUTS.add(output.getItemStack());
		}

		@Override
		protected void run()
		{
			if (CLEAR)
			{
				FurnaceRecipes.instance().getSmeltingList().clear();

				//debug
				LOG.debug("furnace.remove : clear");
			}
			else
			{
				if (!OUTPUTS.isEmpty())
				{
					Map<ItemStack, ItemStack> newRecipes = Maps.newHashMap();
					for (Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet())
					{
						for (ItemStack stack : OUTPUTS)
						{
							if (!ScriptHelper.areScriptItemsEqual(stack, entry.getValue()))
							{
								newRecipes.put(entry.getKey(), entry.getValue());
							}
						}
					}

					//debug
					LOG.debug("furnace.remove : " + (FurnaceRecipes.instance().getSmeltingList().size() - newRecipes.size()) + " furnace recipes");

					FurnaceRecipes.instance().getSmeltingList().clear();
					for (Map.Entry<ItemStack, ItemStack> entry : newRecipes.entrySet())
					{
						FurnaceRecipes.instance().getSmeltingList().put(entry.getKey(), entry.getValue());
					}
				}
			}

			//cleanup
			OUTPUTS = null;
		}
	}


	//**************************************************************************************//
	//										add fuel										//
	//**************************************************************************************//

	@TweakedAction(value="furnace.addfuel")
	public static class Action_Furnace_AddFuel extends ActionAbstract
	{
		public Action_Furnace_AddFuel()
		{
			ADD_FUEL = this;
		}

		public void build(ObjStack stack, Integer burnTime)
		{
			FuelHelper.addAddFuel(stack.getItemStack(), burnTime);
		}

		@Override
		protected void run()
		{
			//handled by build
		}
	}


	//**************************************************************************************//
	//										remove fuel										//
	//**************************************************************************************//

	@TweakedAction(value="furnace.removefuel")
	public static class Action_Furnace_RemoveFuel extends ActionAbstract
	{
		public Action_Furnace_RemoveFuel()
		{
			REMOVE_FUEL = this;
		}

		@SuppressWarnings("unused")
		public void build(ObjAll all)
		{
			FuelHelper.setClear(true);
		}

		public void build(ObjStack stack)
		{
			FuelHelper.addRemoveFuel(stack.getItemStack());
		}

		@Override
		protected void run()
		{
			//handled by build
		}
	}
}
