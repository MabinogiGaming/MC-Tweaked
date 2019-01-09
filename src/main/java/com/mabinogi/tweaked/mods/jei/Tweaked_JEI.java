package com.mabinogi.tweaked.mods.jei;

import com.mabinogi.tweaked.Tweaked;
import com.mabinogi.tweaked.mods.ModManager;
import com.mabinogi.tweaked.mods.jei.actions.Action_JEI;
import com.mabinogi.tweaked.mods.jei.proxy.Proxy_JEI_Common;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@SuppressWarnings("WeakerAccess")
@Mod(modid = Tweaked_JEI.MODID, name = Tweaked_JEI.NAME, version = Tweaked.VERSION, dependencies=Tweaked_JEI.DEPENDENCIES)
public class Tweaked_JEI
{
    public static final String MODID = "tweaked_jei";
    public static final String NAME = "Tweaked_JEI";
    public static final String DEPENDENCIES = "required-after:tweaked;after:jei;";

    @SidedProxy(clientSide = "com.mabinogi.tweaked.mods.jei.proxy.Proxy_JEI_Client", serverSide = "com.mabinogi.tweaked.mods.jei.proxy.Proxy_JEI_Common")
    public static Proxy_JEI_Common proxy;

    @EventHandler
	public void post(FMLPostInitializationEvent event)
	{
		if (ModManager.JEI_LOADED)
		{
			//add infos
			Action_JEI.ADD_INFO.apply();
		}
	}
    
    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event)
    {
    	if (ModManager.JEI_LOADED)
    	{
    		//hide items
    		Action_JEI.HIDE.apply();
    		
    		//add items
    		Action_JEI.ADD.apply();
    	}
    }

}
