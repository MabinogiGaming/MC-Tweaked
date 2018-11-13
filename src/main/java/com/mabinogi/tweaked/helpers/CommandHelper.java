package com.mabinogi.tweaked.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class CommandHelper
{
	public static void sendMessage(EntityPlayer player, String message)
	{
		//create text component
		TextComponentString txtComponent = new TextComponentString(message);

		//send to player
		player.sendMessage(txtComponent);
	}

	public static void sendCopyMessage(EntityPlayer player, String message)
	{
		//create text component
		TextComponentString txtComponent = new TextComponentString(message);

		//add a copy on click event
		ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tweaked copy " + message);
		txtComponent.getStyle().setClickEvent(click);

		//add a hover event
		HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to copy"));
		txtComponent.getStyle().setHoverEvent(hoverEvent);

		//send to player
		player.sendMessage(txtComponent);
	}
}
