package com.mabinogi.tweaked.mods.vanilla.proxy;

import com.mabinogi.tweaked.mods.vanilla.events.Events_Vanilla_Common;
import net.minecraftforge.common.MinecraftForge;

public class Proxy_Vanilla_Common
{
	public void registerEvents()
	{
		MinecraftForge.EVENT_BUS.register(new Events_Vanilla_Common());
	}
}
