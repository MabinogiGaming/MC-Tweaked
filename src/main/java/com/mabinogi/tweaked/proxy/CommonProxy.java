package com.mabinogi.tweaked.proxy;

import com.mabinogi.tweaked.events.CommonEventHandler;

import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
	
	public void registerEvents()
	{
		MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
	}

}
