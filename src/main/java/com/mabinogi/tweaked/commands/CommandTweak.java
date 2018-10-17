package com.mabinogi.tweaked.commands;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.mabinogi.tweaked.Tweaked;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class CommandTweak extends CommandBase 
{	
	/**
     * Gets the name of the command
     */
    public String getName()
    {
        return "tweak";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    /**
     * Gets the usage string for the command.
     */
    public String getUsage(ICommandSender sender)
    {
        return "commands.help.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
    	if(sender.getCommandSenderEntity() instanceof EntityPlayer) 
    	{
    		// get player
            EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
    		
	    	if (args.length == 0)
	    	{
	    		//tweaked help
	    		player.sendMessage(new TextComponentString("Welcome to Tweaked!"));
	    	}
	    	if (args[0].equals("hand"))
	    	{
	    		if (args.length == 2)
	    		{
	    			if (args[1].equals("ore"))
	    			{
	    				player.sendMessage(new TextComponentString("TODO : Fetching OreDicts"));
	    			}
	    			if (args[1].equals("nbt"))
	    			{
	    				player.sendMessage(new TextComponentString("TODO : Fetching NBT"));
	    			}
	    			else
	    			{
	    				TextComponentString txtComponent = new TextComponentString("Invalid argument : " + args[1]);
	    				txtComponent.getStyle().setColor(TextFormatting.DARK_RED);
	    				player.sendMessage(txtComponent);
	    			}
	    		}
	    		else
	    		{
		    		//get held item
		            ItemStack heldItem = player.getHeldItemMainhand();
		            
		            // Tries to get name of held item first
		            if(!heldItem.isEmpty()) 
		            {
		                int meta = heldItem.getMetadata();
		                String itemName = "<" + heldItem.getItem().getRegistryName() + (meta == 0 ? "" : ":" + meta) + ">";
		                
		                TextComponentString txtComponent = new TextComponentString(itemName);
		                
		                ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND, itemName);
		                txtComponent.getStyle().setClickEvent(click);
		                
		                HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to copy [\u00A76" + itemName + "\u00A7r]"));
		                txtComponent.getStyle().setHoverEvent(hoverEvent);
		                
		                player.sendMessage(txtComponent);
	    				
	    				Tweaked.LOG.dump("/tweak hand\n\t" + itemName);
		            }
	    		}
	    	}
    	}
    }

    /**
     * Get a list of options for when the user presses the TAB key
     */
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        return Collections.<String>emptyList();
    }

}
