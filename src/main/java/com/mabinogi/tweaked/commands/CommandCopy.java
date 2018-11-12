package com.mabinogi.tweaked.commands;

import com.mabinogi.tweaked.api.annotations.TweakedCommand;
import com.mabinogi.tweaked.api.commands.ITweakedCommand;
import com.mabinogi.tweaked.network.MessageCopy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.NETWORK;

@TweakedCommand("copy")
public class CommandCopy implements ITweakedCommand
{
	@Override
	public boolean isHidden()
	{
		return true;
	}
	
	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args)
	{
		if (args.length > 1 && !args[1].isEmpty())
		{
			if(player instanceof EntityPlayerMP) 
			{
	            NETWORK.sendTo(new MessageCopy(args[1]), (EntityPlayerMP) player);
	        }
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, EntityPlayer player, String[] args, BlockPos targetPos)
	{
		return Collections.emptyList();
	}

}
