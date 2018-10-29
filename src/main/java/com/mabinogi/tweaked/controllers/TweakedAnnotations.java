package com.mabinogi.tweaked.controllers;

import com.mabinogi.tweaked.api.actions.ITweakedAction;
import com.mabinogi.tweaked.api.arguments.ITweakedArgument;
import com.mabinogi.tweaked.api.commands.ITweakedCommand;
import com.mabinogi.tweaked.api.test.ITweakedTest;
import com.mabinogi.tweaked.api.variables.ITweakedVariable;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.mabinogi.tweaked.Tweaked.LOG;

public class TweakedAnnotations {
	
	//a map of actions associated with their script name
	public static final Map<String, ITweakedAction> ACTIONS = new HashMap<>();
	
	//a map of arguments that can be passed into actions, associated with their tokens
	public static final Map<String, ITweakedArgument> ARGUMENTS = new HashMap<>();
	
	//a map of variables that can be stored and then used as arguments, associated with their script name
	public static final Map<String, ITweakedVariable> VARIABLES = new HashMap<>();

	//a map of commands that can be called by the player when ingame, associated with their command name
	public static final Map<String, ITweakedCommand> COMMANDS = new HashMap<>();

	//a list of tests that are used when running in test mode
	public static final List<ITweakedTest> TESTS = new ArrayList<>();
	
	public static void build(ASMDataTable asm)
	{
		try
		{
			buildVariables(asm);
			buildArguments(asm);
			buildActions(asm);
			buildCommands(asm);
			if (TweakedConfiguration.testMode) buildTests(asm);
		} 
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void buildActions(ASMDataTable asm) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException
	{
		Set<ASMData> asmSet = asm.getAll("com.mabinogi.tweaked.api.annotations.TweakedAction");
    	for (ASMData data : asmSet)
    	{
    		Object modid = data.getAnnotationInfo().get("modid");
    		if (modid != null && modid instanceof String)
			{
    			if (!Loader.isModLoaded(((String) modid)))
				{
					LOG.debug("Disabled Action : " + (data.getClassName().contains(".") ? data.getClassName().substring(data.getClassName().lastIndexOf(".") + 1) : data.getClassName()) + " as '" + data.getAnnotationInfo().get("value") + "'");
					continue;
				}
			}
    		
    		//invoke the class
    		Class<?> clazz = Class.forName(data.getClassName());
			Object invokedClazz = clazz.newInstance();
			
			//get the input token
			Object value = data.getAnnotationInfo().get("value");
			
			if (value instanceof String && invokedClazz instanceof ITweakedAction)
			{
				//register action
				ACTIONS.put((String) value, (ITweakedAction) invokedClazz);
				
				//debug
				LOG.debug("Registered Action : " + (data.getClassName().contains(".") ? data.getClassName().substring(data.getClassName().lastIndexOf(".") + 1) : data.getClassName()) + " as '" + value + "'");
			}
    	}
	}
	
	private static void buildArguments(ASMDataTable asm) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException
	{
		Set<ASMData> asmSet = asm.getAll("com.mabinogi.tweaked.api.annotations.TweakedArgument");
    	for (ASMData data : asmSet)
    	{
    		//invoke the class
    		Class<?> clazz = Class.forName(data.getClassName());
			Object invokedClazz = clazz.newInstance();
			
			//get the input token
			Object value = data.getAnnotationInfo().get("value");
			
			if (value instanceof ArrayList<?> && invokedClazz instanceof ITweakedArgument)
			{
				ArrayList<?> tokenList = (ArrayList<?>) value;
				
				for (Object o : tokenList)
				{
					if (o instanceof String)
					{
						//register argument
						ARGUMENTS.put((String) o, (ITweakedArgument) invokedClazz);
						
						//debug
						LOG.debug("Registered Argument : " + (data.getClassName().contains(".") ? data.getClassName().substring(data.getClassName().lastIndexOf(".") + 1) : data.getClassName()) + " as '" + o + "'");
					}
				}
			}
    	}
	}
	
	private static void buildVariables(ASMDataTable asm) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException
	{
		Set<ASMData> asmSet = asm.getAll("com.mabinogi.tweaked.api.annotations.TweakedVariable");
    	for (ASMData data : asmSet)
    	{
    		//invoke the class
    		Class<?> clazz = Class.forName(data.getClassName());
			Object invokedClazz = clazz.newInstance();
			
			//get the input token
			Object value = data.getAnnotationInfo().get("value");
			
			if (value instanceof String && invokedClazz instanceof ITweakedVariable)
			{
				//register variable
				VARIABLES.put((String) value, (ITweakedVariable) invokedClazz);
				
				//debug
				LOG.debug("Registered Variable : " + (data.getClassName().contains(".") ? data.getClassName().substring(data.getClassName().lastIndexOf(".") + 1) : data.getClassName()) + " as '" + value + "'");
			}
    	}
	}
	
	private static void buildCommands(ASMDataTable asm) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException
	{
		Set<ASMData> asmSet = asm.getAll("com.mabinogi.tweaked.api.annotations.TweakedCommand");
    	for (ASMData data : asmSet)
    	{
    		Object modid = data.getAnnotationInfo().get("modid");
    		if (modid != null && modid instanceof String)
			{
    			if (!Loader.isModLoaded(((String) modid)))
				{
					LOG.debug("Disabled Command : " + (data.getClassName().contains(".") ? data.getClassName().substring(data.getClassName().lastIndexOf(".") + 1) : data.getClassName()) + " as '" + data.getAnnotationInfo().get("value") + "'");
					continue;
				}
			}
    		
    		//invoke the class
    		Class<?> clazz = Class.forName(data.getClassName());
			Object invokedClazz = clazz.newInstance();
			
			//get the input token
			Object value = data.getAnnotationInfo().get("value");
			
			if (value instanceof String && invokedClazz instanceof ITweakedCommand)
			{
				//register action
				COMMANDS.put((String) value, (ITweakedCommand) invokedClazz);
				
				//debug
				LOG.debug("Registered Command : " + (data.getClassName().contains(".") ? data.getClassName().substring(data.getClassName().lastIndexOf(".") + 1) : data.getClassName()) + " as '" + value + "'");
			}
    	}
	}

	private static void buildTests(ASMDataTable asm) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException
	{
		Set<ASMData> asmSet = asm.getAll("com.mabinogi.tweaked.api.annotations.TweakedTest");
		for (ASMData data : asmSet)
		{
			Object modid = data.getAnnotationInfo().get("modid");
			if (modid != null && modid instanceof String)
			{
				if (!Loader.isModLoaded(((String) modid)))
				{
					LOG.debug("Disabled Test : " + (data.getClassName().contains(".") ? data.getClassName().substring(data.getClassName().lastIndexOf(".") + 1) : data.getClassName()) + " as '" + data.getAnnotationInfo().get("value") + "'");
					continue;
				}
			}

			//invoke the class
			Class<?> clazz = Class.forName(data.getClassName());
			Object invokedClazz = clazz.newInstance();

			//get the input token

			if (invokedClazz instanceof ITweakedTest)
			{
				//register action
				TESTS.add((ITweakedTest) invokedClazz);

				//debug
				LOG.debug("Registered Test : " + (data.getClassName().contains(".") ? data.getClassName().substring(data.getClassName().lastIndexOf(".") + 1) : data.getClassName()));
			}
		}
	}

}
