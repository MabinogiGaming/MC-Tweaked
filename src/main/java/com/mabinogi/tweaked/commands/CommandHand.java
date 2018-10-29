package com.mabinogi.tweaked.commands;

import com.mabinogi.tweaked.api.annotations.TweakedCommand;
import com.mabinogi.tweaked.api.commands.ITweakedCommand;
import com.mabinogi.tweaked.script.ScriptHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import java.util.Collections;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.LOG;
import static com.mabinogi.tweaked.controllers.TweakedLogging.TAB;

@TweakedCommand("hand")
public class CommandHand implements ITweakedCommand
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
    		LOG.dump("/tweaked hand");
    		
    		//create a script version of the stack
    		String output = ScriptHelper.stackToScript(heldItem);
            
            //create the text component
            TextComponentString txtComponent = new TextComponentString(output);
            
            //add a copy on click event
            ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tweaked copy " + output);
            txtComponent.getStyle().setClickEvent(click);
            
            //add a hover event
            HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to copy"));
            txtComponent.getStyle().setHoverEvent(hoverEvent);
            
            //send the message
            player.sendMessage(txtComponent);
			
            //dump the message
			LOG.dump(TAB + output);
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
