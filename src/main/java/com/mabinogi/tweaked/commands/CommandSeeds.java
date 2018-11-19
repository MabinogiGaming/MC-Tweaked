package com.mabinogi.tweaked.commands;

import com.mabinogi.tweaked.api.annotations.TweakedCommand;
import com.mabinogi.tweaked.api.commands.ITweakedCommand;
import com.mabinogi.tweaked.controllers.TweakedReflection;
import com.mabinogi.tweaked.helpers.CommandHelper;
import com.mabinogi.tweaked.script.ScriptHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.LOG;
import static com.mabinogi.tweaked.controllers.TweakedLogging.TAB;

@TweakedCommand("seeds")
public class CommandSeeds implements ITweakedCommand
{
	@Override
	public boolean isHidden()
	{
		return false;
	}
	
	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args)
	{
		if (args.length >= 1)
		{
			LOG.dump("/tweaked seeds");

			for(WeightedRandom.Item entry : TweakedReflection.getSeedEntries())
			{
				ItemStack stack = TweakedReflection.getSeedStack(entry);
				String out = ScriptHelper.stackToScript(stack);

				//create the text component
				CommandHelper.sendMessage(player, out);

				//dump the message
				LOG.dump(TAB + out);
			}
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, EntityPlayer player, String[] args, BlockPos targetPos)
	{
		return Collections.emptyList();
	}

}
