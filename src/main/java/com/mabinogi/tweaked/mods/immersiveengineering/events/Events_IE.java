package com.mabinogi.tweaked.mods.immersiveengineering.events;

import blusunrize.immersiveengineering.api.MultiblockHandler.MultiblockFormEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class Events_IE
{
	public static Boolean MULTIBLOCK_BLACKLIST_ALL = false;
	public static List<String> MULTIBLOCK_BLACKLIST = new ArrayList<>();
	
	@SubscribeEvent
	public void formMultiblock(MultiblockFormEvent event)
	{
		if (MULTIBLOCK_BLACKLIST_ALL)
		{
			event.setCanceled(true);
		}
		else
		{
			if (MULTIBLOCK_BLACKLIST.contains(event.getMultiblock().getUniqueName()))
			{
				event.setCanceled(true);
			}
		}
	}
}
