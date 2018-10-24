package com.mabinogi.tweaked.commands;

import static com.mabinogi.tweaked.Tweaked.LOG;
import static com.mabinogi.tweaked.logging.LogHandler.TAB;
import static com.mabinogi.tweaked.logging.LogHandler.DOUBLE_TAB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.mabinogi.tweaked.TweakedController;
import com.mabinogi.tweaked.api.annotations.TweakedCommand;
import com.mabinogi.tweaked.api.commands.ICommand;
import com.mabinogi.tweaked.script.ScriptHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

@TweakedCommand("recipes")
public class CommandRecipes implements ICommand
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
			if (TweakedController.RECIPE_REGISTRY == null)
			{
				LOG.warn("Warning : Recipe Registry Missing");
				return;
			}
			
			//convert stacks into recipe names
			List<String> recipeList = new ArrayList<>();
			for (Map.Entry<ResourceLocation, IRecipe> recipe : TweakedController.RECIPE_REGISTRY.getEntries())
			{
				if (heldItem.isItemEqual(recipe.getValue().getRecipeOutput()))
				{
					for (String s : ScriptHelper.recipeToScript(recipe.getValue(), true))
					{
						recipeList.add(s);
					}
				}
			}
			
			//make outputs
			if (recipeList.isEmpty())
			{
				//create the text component
	            TextComponentString txtComponent = new TextComponentString("No recipes found");
	            
	            //send the message
	            player.sendMessage(txtComponent);
				
				//dump the message
	            LOG.dump(TAB + "No recipes found");
			}
			
			//print summary
            TextComponentString txtComponent = new TextComponentString(recipeList.size() + " recipes have been dumped");
            
            //send the message
            player.sendMessage(txtComponent);
			
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
        	//create the text component
            TextComponentString txtComponent = new TextComponentString("Requires an item in the main hand");
            
            //send the message
            player.sendMessage(txtComponent);
        }
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, EntityPlayer player, String[] args, BlockPos targetPos)
	{
		return Collections.<String>emptyList();
	}

}
