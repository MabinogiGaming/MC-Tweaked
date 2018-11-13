package com.mabinogi.tweaked.commands;

import com.mabinogi.tweaked.api.annotations.TweakedCommand;
import com.mabinogi.tweaked.api.commands.ITweakedCommand;
import com.mabinogi.tweaked.helpers.CommandHelper;
import com.mabinogi.tweaked.script.ScriptHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

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
    		String out = ScriptHelper.stackToScript(heldItem);

    		//reply
    		CommandHelper.sendCopyMessage(player, out);
			
            //dump the message
			LOG.dump(TAB + out);
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
