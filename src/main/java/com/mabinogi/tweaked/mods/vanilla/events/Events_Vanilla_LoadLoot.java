package com.mabinogi.tweaked.mods.vanilla.events;

import com.mabinogi.tweaked.helpers.LootHelper;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Events_Vanilla_LoadLoot
{
	@SubscribeEvent
	public void loadLoot(LootTableLoadEvent event)
	{
		//modify loot tables
		LootTable ret = LootHelper.modifyLootTable(event.getName().toString(), event.getTable());
		if (ret != null)
		{
			event.setTable(ret);
		}
	}
}
