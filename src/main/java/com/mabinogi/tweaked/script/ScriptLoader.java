package com.mabinogi.tweaked.script;

import com.mabinogi.tweaked.controllers.TweakedConfiguration;
import com.mabinogi.tweaked.script.loaders.ActionLoader;
import com.mabinogi.tweaked.script.loaders.VariableLoader;
import net.minecraftforge.common.config.Configuration.UnicodeInputStreamReader;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.mabinogi.tweaked.Tweaked.LOG;

public class ScriptLoader {
	
	public static int scriptCount = 0;
	public static int passCount = 0;
	public static int failCount = 0;
	
	public static void loadScripts()
	{
		//create scripts directory
        File scriptDir = new File(TweakedConfiguration.tweakedDir + File.separator + "scripts");
        if (!scriptDir.exists())
        {
        	scriptDir.mkdirs();
        }
        
        //parse script files
        for (File f : ScriptHelper.listFiles(scriptDir))
        {
        	if (f.getName().endsWith(".tweak"))
        	{
        		//parse file
        		BufferedReader buffer = null;
        		UnicodeInputStreamReader input = null;
        		
        		if (f.canRead())
        		{
        			try
					{
						input = new UnicodeInputStreamReader(new FileInputStream(f), "UTF-8");
						buffer = new BufferedReader(input);
						
						//full string we will now build up
						String in = "";
						
						String line;
						while ((line = buffer.readLine()) != null) 
						{
							if (line.startsWith("#") || line.startsWith("//"))
					    	{
					    		//comment, ignore line
					    	}
							else
							{
								in += line;
							}
					    }
						
						//clean script
						in = ScriptHelper.cleanScript(in);
						
						//split into separate scripts
						String[] scripts = in.split(";");
						
						//sanity check
						if (scripts.length == 0 || (scripts.length == 1 && scripts[0].isEmpty()))
						{
							//no point parsing
						}
						else
						{
							for (String script : scripts)
							{
								parseLine(script);
							}
							
							scriptCount++;
						}
					} 
        			catch (IOException e)
					{
						e.printStackTrace();
					}
        			finally
        	        {
        	            IOUtils.closeQuietly(buffer);
        	            IOUtils.closeQuietly(input);
        	        }
        		}
        	}
        }
        
        //debug
        LOG.debug("--------------------");
        LOG.info("Parsed " + scriptCount + " scripts");
        LOG.info("Parsed " + passCount + "/" + (passCount + failCount) + " tweaks");
        LOG.debug("--------------------");
	}
    
    public static void parseLine(String in)
    {
    	//debug
    	LOG.debug("--------------------");
    	
    	if (in.startsWith(".") || in.startsWith("tweak."))
    	{
    		in = in.substring(in.indexOf(".") + 1);
    		
    		if (ActionLoader.parseAction(in)) passCount++;
    		else failCount++;
    	}
    	else if (in.startsWith("$"))
    	{
    		in = in.substring(in.indexOf("$") + 1);
    		
    		if(VariableLoader.parseVariable(in)) passCount++;
    		else failCount++;
    	}
    	else if (in.startsWith("print"))
    	{
    		in = in.substring(in.indexOf("t") + 1);
    		
    		parsePrint(in);
    	}
    	else
    	{
    		ScriptHelper.reportScriptError(in, "Invalid line start");
    		return;
    	}    	
    }
    
    private static void parsePrint(String in)
    {
    	String start = in;
    	
    	if (in.startsWith("(") && in.endsWith(")"))
    	{
    		in = in.substring(in.indexOf("(") + 1, in.lastIndexOf(")"));
    	}
    	else
    	{
    		ScriptHelper.reportScriptError(start, "Unable to determine print, missing brackets");
    		return;
    	}
    	
    	if (in.startsWith("\"") && in.endsWith("\""))
    	{
    		in = in.substring(in.indexOf("\"") + 1, in.lastIndexOf("\""));
    	}
    	else
    	{
    		ScriptHelper.reportScriptError(start, "Unable to determine print, missing double speech marks");
    		return;
    	}
    	
    	if (!in.isEmpty()) 
    	{
    		LOG.print("Print : " + in);
    	}
    }

}
