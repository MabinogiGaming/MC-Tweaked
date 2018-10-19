package com.mabinogi.tweaked.mods.immersiveengineering;

import com.mabinogi.tweaked.Tweaked;
import com.mabinogi.tweaked.mods.ModManager;
import com.mabinogi.tweaked.mods.immersiveengineering.actions.Action_IE;
import com.mabinogi.tweaked.mods.immersiveengineering.actions.Action_IE_blastfurnace;
import com.mabinogi.tweaked.mods.immersiveengineering.actions.Action_IE_cokeoven;
import com.mabinogi.tweaked.mods.immersiveengineering.actions.Action_IE_crusher;
import com.mabinogi.tweaked.mods.immersiveengineering.events.Events_IE;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Tweaked_IE.MODID, name = Tweaked_IE.NAME, version = Tweaked.VERSION, dependencies=Tweaked_IE.DEPENDENCIES)
public class Tweaked_IE
{
    public static final String MODID = "tweaked_ie";
    public static final String NAME = "Tweaked_IE";
    public static final String DEPENDENCIES = "required-after:tweaked;after:immersiveengineering;";
    
    @EventHandler
    public void pre(FMLPreInitializationEvent event)
    {
    	if (ModManager.IE_LOADED)
    	{
    		MinecraftForge.EVENT_BUS.register(new Events_IE());
    	}
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	if (ModManager.IE_LOADED)
    	{
    		//disable multiblock formation
    		Action_IE.DISABLE_MULTIBLOCK.apply();
    		
    		//blast furnace fuels
    		Action_IE_blastfurnace.REMOVE_FUEL.apply();
    		Action_IE_blastfurnace.ADD_FUEL.apply();
    		
    		//crusher recipes
    		Action_IE_crusher.REMOVE.apply();
    		Action_IE_crusher.ADD.apply();
    		
    		//cokeoven recipes
    		Action_IE_cokeoven.REMOVE.apply();
    		Action_IE_cokeoven.ADD.apply();
    		
    		//blast furnace recipes
    		Action_IE_blastfurnace.REMOVE.apply();
    		Action_IE_blastfurnace.ADD.apply();
    	}
    }

}
