package com.mabinogi.tweaked.script;

import static com.mabinogi.tweaked.Tweaked.LOG;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptHelper {
	
	public static void reportScriptError(String script, String reason)
    {
    	if (script != null)
    	{
    		LOG.warn("Script Error : " + script);
    	}
    	if (reason != null)
    	{
    		LOG.warn("Reason : " + reason);
    	}
    }
	
	public static String cleanScript(String in)
	{
		//pattern to find strings
		Pattern pattern = Pattern.compile("\\\"(.*?)\\\"");

		//start by protecting all strings, replace " " with "|"
		Matcher matcher = pattern.matcher(in);
		while(matcher.find()) 
		{
			in = in.replace("\"" + matcher.group(1) + "\"", "\"" + matcher.group(1).replace(" ", "|") + "\"");
		}
		
		//now clear all whitespace from the string
		in = in.replaceAll("\\s", "");
		
		//finally restore the strings, replace "|" with " "
		matcher = pattern.matcher(in);
		while(matcher.find()) 
		{
			in = in.replace("\"" + matcher.group(1) + "\"", "\"" + matcher.group(1).replace("|", " ") + "\"");
		}
		
		return in;
	}
    
    public static Collection<File> listFiles(File dir) 
    {
        Set<File> fileTree = new HashSet<File>();
        if(dir==null||dir.listFiles()==null)
        {
            return fileTree;
        }
        
        for (File entry : dir.listFiles()) 
        {
            if (entry.isFile()) fileTree.add(entry);
            else fileTree.addAll(listFiles(entry));
        }
        return fileTree;
    }

}
