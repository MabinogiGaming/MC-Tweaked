package com.mabinogi.tweaked.commands;

import com.mabinogi.tweaked.api.annotations.TweakedCommand;
import com.mabinogi.tweaked.api.commands.ITweakedCommand;
import com.mabinogi.tweaked.helpers.CommandHelper;
import com.mabinogi.tweaked.script.ScriptHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
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
		if (args.length >= 2)
		{
			String oreName = args[1];

			if (OreDictionary.doesOreNameExist(oreName))
			{
				//dump the message
				LOG.dump("/tweaked dict " + oreName);

				NonNullList<ItemStack> ores = OreDictionary.getOres(oreName);
				for (ItemStack ore : ores)
				{
					String out = ScriptHelper.stackToScript(ore);

					//reply
					CommandHelper.sendCopyMessage(player, out);

					//dump the message
					LOG.dump(TAB + out);
				}
			}
			else
			{
				CommandHelper.sendMessage(player, "Ore name doesn't exist");
			}
		}
		else
		{
			//no dict specified, do held item scan
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
					CommandHelper.sendMessage(player, "No matching ore dictionary");

					//dump the message
					LOG.dump(TAB + "No matching ore dictionary");

					return;
				}

				//create output from ore items
				for (int id : idArray)
				{
					//get the ore name
					String out = "<" + OreDictionary.getOreName(id) + ">";

					//reply
					CommandHelper.sendCopyMessage(player, out);

					//dump the message
					LOG.dump(TAB + out);
				}
			}
			else
			{
				CommandHelper.sendMessage(player, "Requires an item in the main hand");
			}
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, EntityPlayer player, String[] args, BlockPos targetPos)
	{
		return Collections.emptyList();
	}

}
