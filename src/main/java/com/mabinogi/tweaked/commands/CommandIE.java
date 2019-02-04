package com.mabinogi.tweaked.commands;

import com.mabinogi.tweaked.api.annotations.TweakedCommand;
import com.mabinogi.tweaked.api.commands.ITweakedCommand;
import com.mabinogi.tweaked.helpers.CommandHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import static blusunrize.immersiveengineering.api.tool.ExcavatorHandler.MineralMix;
import static blusunrize.immersiveengineering.api.tool.ExcavatorHandler.mineralList;
import static com.mabinogi.tweaked.Tweaked.LOG;
import static com.mabinogi.tweaked.controllers.TweakedLogging.TAB;

@TweakedCommand(value="ie", modid="immersiveengineering")
public class CommandIE implements ITweakedCommand
{
	@Override
	public boolean isHidden()
	{
		return false;
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args)
	{
		if (args.length >= 2)
		{
			String command = args[1];

			if (command.equals("excavator"))
			{
				//dump the message
				LOG.dump("/tweaked ie excavator");

				int total = 0;
				for (Entry<MineralMix, Integer> entry : mineralList.entrySet())
				{
					MineralMix mix = entry.getKey();
					Integer weight = entry.getValue();

					total++;

					LOG.dump(TAB + mix.name + " : " + weight + " : " + mix.failChance + " : " + Arrays.toString(mix.ores) + " : " + Arrays.toString(mix.chances));
				}

				if (total > 0)
				{
					CommandHelper.sendCopyMessage(player,"Dumped " + total + " mixes");
				}
				else
				{
					CommandHelper.sendMessage(player, "No mineral mixes defined");
				}
			}
			else
			{
				CommandHelper.sendMessage(player, "Unknown command, " + command + " not recognized.");
			}
		}
		else
		{
			CommandHelper.sendMessage(player, "Invalid arguments, requires /tw ie [command]");
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, EntityPlayer player, String[] args, BlockPos targetPos)
	{
		return Collections.emptyList();
	}
}
