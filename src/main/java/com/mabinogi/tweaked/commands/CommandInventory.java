package com.mabinogi.tweaked.commands;

import com.mabinogi.tweaked.api.annotations.TweakedCommand;
import com.mabinogi.tweaked.api.commands.ITweakedCommand;
import com.mabinogi.tweaked.helpers.CommandHelper;
import com.mabinogi.tweaked.script.ScriptHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.LOG;
import static com.mabinogi.tweaked.controllers.TweakedLogging.TAB;

@TweakedCommand("inventory")
public class CommandInventory implements ITweakedCommand
{
	@Override
	public boolean isHidden()
	{
		return false;
	}
	
	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args)
	{
		//get player inventories
		InventoryPlayer inv = player.inventory;

        if(inv != null)
        {
        	//dump the message
    		LOG.dump("/tweaked inventory");

    		int total = 0;
    		for (ItemStack stack : inv.mainInventory)
			{
				if (!stack.isEmpty())
				{
					total++;

					LOG.dump(TAB + ScriptHelper.stackToScript(stack));
				}
			}

			if (total > 0)
			{
				CommandHelper.sendCopyMessage(player,"Dumped " + total + " stacks");
			}
			else
			{
				CommandHelper.sendMessage(player, "Requires items in players inventory");
			}
        }
        else
        {
			CommandHelper.sendMessage(player, "Requires items in players inventory");
        }
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, EntityPlayer player, String[] args, BlockPos targetPos)
	{
		return Collections.emptyList();
	}

}
