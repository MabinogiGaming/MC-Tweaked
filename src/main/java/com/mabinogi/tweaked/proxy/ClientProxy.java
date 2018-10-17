package com.mabinogi.tweaked.proxy;

import com.mabinogi.tweaked.events.ClientEventHandler;

import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
	
	public void registerEvents()
	{
		super.registerEvents();
		
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
	}

}
