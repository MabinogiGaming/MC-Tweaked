package com.mabinogi.tweaked.commands;

import com.mabinogi.tweaked.api.annotations.TweakedCommand;
import com.mabinogi.tweaked.api.commands.ITweakedCommand;
import com.mabinogi.tweaked.helpers.CommandHelper;
import com.mabinogi.tweaked.network.MessageBrowser;
import com.mabinogi.tweaked.network.MessageCopy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.NETWORK;

@TweakedCommand("wiki")
public class CommandWiki implements ITweakedCommand
{
	@Override
	public boolean isHidden()
	{
		return true;
	}
	
	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args)
	{
		if(player instanceof EntityPlayerMP)
		{
			NETWORK.sendTo(new MessageBrowser("https://tweaked.readthedocs.io/en/latest/"), (EntityPlayerMP) player);

			CommandHelper.sendMessage(player, "Wiki opened in the browser.");
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, EntityPlayer player, String[] args, BlockPos targetPos)
	{
		return Collections.emptyList();
	}

}