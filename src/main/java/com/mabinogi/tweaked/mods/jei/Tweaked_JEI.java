package com.mabinogi.tweaked.mods.jei;

import com.mabinogi.tweaked.Tweaked;
import com.mabinogi.tweaked.mods.ModManager;
import com.mabinogi.tweaked.mods.jei.actions.Action_JEI;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = Tweaked_JEI.MODID, name = Tweaked_JEI.NAME, version = Tweaked.VERSION, dependencies=Tweaked_JEI.DEPENDENCIES)
public class Tweaked_JEI
{
    public static final String MODID = "tweaked_jei";
    public static final String NAME = "Tweaked_JEI";
    public static final String DEPENDENCIES = "required-after:tweaked;after:jei;";
    
    @EventHandler
    public void post(FMLPostInitializationEvent event)
    {
    	if (ModManager.JEI_LOADED)
    	{
    		
    	}
    }
    
    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event)
    {
    	if (ModManager.JEI_LOADED)
    	{
    		Action_JEI.addApply();
    		
    		Action_JEI.hideApply();
    	}
    }

}
