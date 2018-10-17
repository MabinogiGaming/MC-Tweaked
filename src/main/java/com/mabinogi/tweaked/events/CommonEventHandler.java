package com.mabinogi.tweaked.events;

import com.mabinogi.tweaked.TweakedController;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistryModifiable;

public class CommonEventHandler {

	@SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) 
    {
		//flag item registration complete, so we know when we can query it
		TweakedController.ITEMS_REGISTERED = true;
    }
	
	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event)
	{
		//set the recipe registry so we can manipulate it later
		TweakedController.RECIPE_REGISTRY = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
	}
	
}
