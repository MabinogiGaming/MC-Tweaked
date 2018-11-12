package com.mabinogi.tweaked.mods.vanilla.proxy;

import com.mabinogi.tweaked.helpers.LootHelper;
import com.mabinogi.tweaked.mods.vanilla.events.Events_Vanilla_Common;
import com.mabinogi.tweaked.mods.vanilla.events.Events_Vanilla_LivingDrop;
import com.mabinogi.tweaked.mods.vanilla.events.Events_Vanilla_LoadLoot;
import net.minecraftforge.common.MinecraftForge;

public class Proxy_Vanilla_Common
{
	public void registerEvents()
	{
		MinecraftForge.EVENT_BUS.register(new Events_Vanilla_Common());
	}

	public void registerEventsLate()
	{
		//only register LootLoad event if we have custom loot tables
		if (LootHelper.hasLootOverrides())
		{
			MinecraftForge.EVENT_BUS.register(new Events_Vanilla_LoadLoot());
		}

		//only register LivingDrop event if we have a special wither handler
		if (LootHelper.hasWitherOverride())
		{
			MinecraftForge.EVENT_BUS.register(new Events_Vanilla_LivingDrop());
		}
	}
}
