package com.mabinogi.tweaked.mods.vanilla.events;

import com.mabinogi.tweaked.helpers.AnvilHelper;
import com.mabinogi.tweaked.helpers.anvil.TweakedAnvilRecipe;
import com.mabinogi.tweaked.script.ScriptHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Events_Vanilla_AnvilCraft
{
	@SubscribeEvent
	public void onAnvilCraft(AnvilRepairEvent event)
	{
		if (AnvilHelper.hasBreakChance())
		{
			event.setBreakChance(AnvilHelper.getBreakChance());
		}

		//fix an issue where the anvil was not designed for stacks with multiple items, unused items need to be returned to the player
		if (AnvilHelper.hasAdditions())
		{
			for (TweakedAnvilRecipe recipe : AnvilHelper.getAdditions())
			{
				if (ScriptHelper.areScriptItemsEqual(recipe.getLeft(), event.getItemInput()) && ScriptHelper.areScriptItemsEqual(recipe.getRight(), event.getIngredientInput()))
				{
					ItemStack stack = event.getItemInput().copy();
					stack.shrink(recipe.getLeft().getCount());

					if (!stack.isEmpty())
					{
						boolean drop = !event.getEntityPlayer().inventory.addItemStackToInventory(stack);
						if (drop)
						{
							event.getEntityPlayer().dropItem(stack, true, false);
						}
					}
				}
			}
		}
	}
}
