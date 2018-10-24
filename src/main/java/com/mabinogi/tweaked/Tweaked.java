package com.mabinogi.tweaked;

import org.apache.logging.log4j.LogManager;

import com.mabinogi.tweaked.logging.LogHandler;
import com.mabinogi.tweaked.mods.ModManager;
import com.mabinogi.tweaked.network.MessageCopy;
import com.mabinogi.tweaked.proxy.CommonProxy;
import com.mabinogi.tweaked.script.ScriptLoader;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Tweaked.MODID, name = Tweaked.NAME, version = Tweaked.VERSION)
public class Tweaked
{
    public static final String MODID = "tweaked";
    public static final String NAME = "Tweaked";
    public static final String VERSION = "0.2.1";
    
    @SidedProxy(clientSide = "com.mabinogi.tweaked.proxy.ClientProxy", serverSide = "com.mabinogi.tweaked.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @Instance
    public static Tweaked instance;
    
    //log handler
    public static LogHandler LOG;
    
    public static SimpleNetworkWrapper NETWORK;
    
    @EventHandler
    public void construction(FMLConstructionEvent event)
    {
    	//load configuration
    	TweakedConfiguration.loadConfig(Minecraft.getMinecraft().mcDataDir);
    	
    	//create logger
    	LOG = new LogHandler(LogManager.getLogger(MODID));
    	
    	//load network
    	NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
    	NETWORK.registerMessage(MessageCopy.class, MessageCopy.class, 0, Side.CLIENT);
    	
    	//load mods
    	ModManager.loadMods();
    	
    	//annotations
    	TweakedAnnotations.build(event.getASMHarvestedData());
    	
    	//scripts
    	ScriptLoader.loadScripts();
    }

    @EventHandler
    public void pre(FMLPreInitializationEvent event)
    {
    	//register events
    	proxy.registerEvents();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	//perform tweaks
    	TweakedController.initTweaks();
    }
    
    @EventHandler
    public void post(FMLPostInitializationEvent event)
    {
    	//save configuration
    	TweakedConfiguration.saveConfig();
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
    	//register commands
    	event.registerServerCommand(new TweakedCommands());
    }
}
