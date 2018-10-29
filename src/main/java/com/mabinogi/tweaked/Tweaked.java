package com.mabinogi.tweaked;

import com.mabinogi.tweaked.controllers.*;
import com.mabinogi.tweaked.mods.ModManager;
import com.mabinogi.tweaked.network.MessageCopy;
import com.mabinogi.tweaked.script.ScriptLoader;
import com.mabinogi.tweaked.script.TestLoader;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;

@Mod(modid = Tweaked.MODID, name = Tweaked.NAME, version = Tweaked.VERSION)
public class Tweaked
{
    public static final String MODID = "tweaked";
    public static final String NAME = "Tweaked";
    public static final String VERSION = "0.2.2";
    
    @Instance
    public static Tweaked instance;
    
    //log handler
    public static TweakedLogging LOG;
    
    public static SimpleNetworkWrapper NETWORK;
    
    @EventHandler
    public void construction(FMLConstructionEvent event)
    {
    	//load configuration
    	TweakedConfiguration.loadConfig(Minecraft.getMinecraft().mcDataDir);
    	
    	//create logger
    	LOG = new TweakedLogging(LogManager.getLogger(MODID));
    	
    	//load network
    	NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
    	NETWORK.registerMessage(MessageCopy.class, MessageCopy.class, 0, Side.CLIENT);
    	
    	//load mods
    	ModManager.loadMods();
    	
    	//annotations
    	TweakedAnnotations.build(event.getASMHarvestedData());
    	
    	//scripts
        if (TweakedConfiguration.testMode)
        {
            TestLoader.loadScripts();
        }
        else
        {
            ScriptLoader.loadScripts();
        }
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

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event)
    {
        //run tests now that everything should be loaded
        if (TweakedConfiguration.testMode) TweakedTests.run();
    }
}
