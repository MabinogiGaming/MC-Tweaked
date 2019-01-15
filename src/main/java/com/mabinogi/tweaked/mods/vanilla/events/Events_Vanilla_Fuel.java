package com.mabinogi.tweaked.mods.vanilla.events;

import com.mabinogi.tweaked.helpers.FuelHelper;
import com.mabinogi.tweaked.helpers.furnace.TweakedFuel;
import com.mabinogi.tweaked.script.ScriptHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Events_Vanilla_Fuel
{
	@SubscribeEvent
	public void loadFuel(FurnaceFuelBurnTimeEvent event)
	{
		if (FuelHelper.getAddFuels() != null)
		{
			for (TweakedFuel fuel : FuelHelper.getAddFuels())
			{
				if (ScriptHelper.areScriptItemsEqual(fuel.getFuel(), event.getItemStack()))
				{
					event.setBurnTime(fuel.getBurnTime());
					return;
				}
			}
		}

		if (FuelHelper.isClear())
		{
			event.setBurnTime(0);
			return;
		}
		else
		{
			if (FuelHelper.getRemoveFuels() != null)
			{
				for (ItemStack fuel : FuelHelper.getRemoveFuels())
				{
					if (ScriptHelper.areScriptItemsEqual(fuel, event.getItemStack()))
					{
						event.setBurnTime(0);
						return;
					}
				}
			}
		}
	}
}
