package com.mabinogi.tweaked.mods.vanilla.events;

import com.mabinogi.tweaked.helpers.AnvilHelper;
import com.mabinogi.tweaked.helpers.AnvilHelper.TweakedAnvilType;
import com.mabinogi.tweaked.helpers.anvil.TweakedAnvilRecipe;
import com.mabinogi.tweaked.script.ScriptHelper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mezz.jei.plugins.jei.JEIInternalPlugin.jeiRuntime;

public class Events_Vanilla_AnvilUpdate
{
	@SubscribeEvent
	public void onAnvilUpdate(AnvilUpdateEvent event)
	{
		//additions will always work, even if part of a cleared option
		if (AnvilHelper.hasAdditions())
		{
			for (TweakedAnvilRecipe recipe : AnvilHelper.getAdditions())
			{
				if (recipe.matches(event.getLeft(), event.getRight()))
				{
					event.setCanceled(false);
					event.setCost(recipe.getCost());
					event.setMaterialCost(recipe.getRight().getCount());
					event.setOutput(recipe.getOutput());
					return;
				}
			}
		}

		//if cleared, nothing else is valid
		if (AnvilHelper.isClear())
		{
			event.setCanceled(true);
			return;
		}

		//next check if it matches a disable
		TweakedAnvilType type = AnvilHelper.getAnvilRecipeType(event.getLeft(), event.getRight());
		if (type == TweakedAnvilType.BOOK)
		{
			if (AnvilHelper.isDisableBooks())
			{
				event.setCanceled(true);
				return;
			}
		}
		else if (type == TweakedAnvilType.REPAIR)
		{
			if (AnvilHelper.isDisableRepairs())
			{
				event.setCanceled(true);
				return;
			}
		}
		else if (type == TweakedAnvilType.COMBINE_BOOK)
		{
			if (AnvilHelper.isDisableCombineBooks())
			{
				event.setCanceled(true);
				return;
			}
		}
		else if (type == TweakedAnvilType.COMBINE_REPAIR)
		{
			if (AnvilHelper.isDisableCombineRepairs())
			{
				event.setCanceled(true);
				return;
			}
		}

		//finally check if it is removed

		if (AnvilHelper.hasRemovals())
		{
			for (ItemStack stack : AnvilHelper.getRemovals())
			{
				if (ScriptHelper.areScriptItemsEqual(stack, event.getLeft()) || ScriptHelper.areScriptItemsEqual(stack, event.getRight()))
				{
					//blacklisted, cancel event
					event.setCanceled(true);
					return;
				}
			}
		}
	}
}
