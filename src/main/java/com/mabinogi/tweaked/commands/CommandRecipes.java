package com.mabinogi.tweaked.commands;

import com.mabinogi.tweaked.api.annotations.TweakedCommand;
import com.mabinogi.tweaked.api.commands.ITweakedCommand;
import com.mabinogi.tweaked.helpers.CommandHelper;
import com.mabinogi.tweaked.mods.vanilla.Tweaked_Vanilla;
import com.mabinogi.tweaked.script.ScriptHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.mabinogi.tweaked.Tweaked.LOG;
import static com.mabinogi.tweaked.controllers.TweakedLogging.DOUBLE_TAB;
import static com.mabinogi.tweaked.controllers.TweakedLogging.TAB;

@TweakedCommand("recipes")
public class CommandRecipes implements ITweakedCommand
{
	@Override
	public boolean isHidden()
	{
		return false;
	}
	
	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args)
	{
		//get held item
        ItemStack heldItem = player.getHeldItemMainhand();
        
        // Tries to get name of held item first
        if(!heldItem.isEmpty()) 
        {
        	//dump the message
    		LOG.dump("/tweaked recipes");
        	
    		//double check we have recipes loaded
			if (Tweaked_Vanilla.RECIPE_REGISTRY == null)
			{
				LOG.warn("Warning : Recipe Registry Missing");
				return;
			}
			
			//convert stacks into recipe names
			List<String> recipeList = new ArrayList<>();
			for (Map.Entry<ResourceLocation, IRecipe> recipe : Tweaked_Vanilla.RECIPE_REGISTRY.getEntries())
			{
				if (heldItem.isItemEqual(recipe.getValue().getRecipeOutput()))
				{
					recipeList.addAll(ScriptHelper.recipeToScript(recipe.getValue(), true));
				}
			}
			
			//make outputs
			if (recipeList.isEmpty())
			{
				CommandHelper.sendMessage(player, "No recipes found");
				
				//dump the message
	            LOG.dump(TAB + "No recipes found");
			}

			//reply
			CommandHelper.sendMessage(player, recipeList.size() + " recipes have been dumped");
			
			//dump the message
            LOG.dump(TAB + "Found " + recipeList.size() + " recipes :");
			
			for (String s : recipeList)
			{
				//dump the message
	            LOG.dump(DOUBLE_TAB + s);
			}
        }
        else
        {
			CommandHelper.sendMessage(player, "Requires an item in the main hand");
        }
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, EntityPlayer player, String[] args, BlockPos targetPos)
	{
		return Collections.emptyList();
	}

}
