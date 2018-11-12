package com.mabinogi.tweaked.commands;

import com.mabinogi.tweaked.api.annotations.TweakedCommand;
import com.mabinogi.tweaked.api.commands.ITweakedCommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collections;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.LOG;
import static com.mabinogi.tweaked.controllers.TweakedLogging.TAB;

@TweakedCommand("dict")
public class CommandDict implements ITweakedCommand
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
    		LOG.dump("/tweaked dict");
        	
        	int[] idArray = OreDictionary.getOreIDs(heldItem);
        	
        	if (idArray.length == 0)
        	{
        		//create the text component
                TextComponentString txtComponent = new TextComponentString("No matching ore dictionary");
              
                //send the message
                player.sendMessage(txtComponent);
    			
                //dump the message
                LOG.dump(TAB + "No matching ore dictionary");
    			
    			return;
        	}
        	
        	//create output from ore items
        	for (int id : idArray)
        	{
        		//get the ore name
        		String oreName = "<" + OreDictionary.getOreName(id) + ">";
        		
        		//create the text component
                TextComponentString txtComponent = new TextComponentString(oreName);
                
                //add a copy on click event
                ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tweaked copy " + oreName);
                txtComponent.getStyle().setClickEvent(click);
                
                //add a hover event
                HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to copy"));
                txtComponent.getStyle().setHoverEvent(hoverEvent);
                
                //send the message
                player.sendMessage(txtComponent);
    			
                //dump the message
                LOG.dump(TAB + oreName);
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
		return Collections.emptyList();
	}

}
