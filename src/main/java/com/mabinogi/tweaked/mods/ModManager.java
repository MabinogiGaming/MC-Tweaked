package com.mabinogi.tweaked.mods;

import net.minecraftforge.fml.common.Loader;

public class ModManager {

	public static boolean IE_LOADED = false;
	public static boolean JEI_LOADED = false;
	public static boolean VANILLA_LOADED = false;
	
	public static void loadMods()
	{
		if (Loader.isModLoaded("immersiveengineering"))
		{
			IE_LOADED = true;
		}

		if (Loader.isModLoaded("jei"))
		{
			JEI_LOADED = true;
		}

		if (true)
		{
			VANILLA_LOADED = true;
		}
	}

}
