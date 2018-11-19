package com.mabinogi.tweaked.commands;

import com.mabinogi.tweaked.api.annotations.TweakedCommand;
import com.mabinogi.tweaked.api.commands.ITweakedCommand;
import com.mabinogi.tweaked.helpers.CommandHelper;
import com.mabinogi.tweaked.helpers.LootHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.mabinogi.tweaked.Tweaked.LOG;
import static com.mabinogi.tweaked.controllers.TweakedLogging.TAB;

@TweakedCommand("loot")
public class CommandLoot implements ITweakedCommand
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
			String key = args[1];

			LootTable table;
			if (key.equals("entities/wither") || key.equals("minecraft:entities/wither"))
			{
				table = LootHelper.getWitherOverride();
			}
			else
			{
				table = server.getWorld(0).getLootTableManager().getLootTableFromLocation(new ResourceLocation(key));
			}

			if (table == null)
			{
				CommandHelper.sendMessage(player, "Loot table doesn't exist");
			}

			//the 3rd arg can specify a count
			int count = 1;
			if (args.length >= 3)
			{
				try
				{
					count = Integer.parseInt(args[2]);
				}
				catch (NumberFormatException e)
				{
					CommandHelper.sendMessage(player, "Invalid count argument");
					LOG.dump(TAB + "Invalid count argument");
					return;
				}
			}

			//dump the message
			LOG.dump("/tweaked loot " + key + " " + count);

			List<ItemStack> dropList = new ArrayList<>();
			for (int i = 0; i < count; i++)
			{
				//generate a loot context
				LootContext lootContext = new LootContext.Builder((WorldServer) player.getEntityWorld()).withPlayer(player).withLuck(3.0f).build();

				for (ItemStack stack : table.generateLootForPools(new Random(), lootContext))
				{
					boolean found = false;
					for (ItemStack existingStack : dropList)
					{
						//check if the item already exists in the list, if so add to its count
						if (ItemStack.areItemsEqual(stack, existingStack))
						{
							existingStack.setCount(existingStack.getCount() + stack.getCount());
							found = true;
							break;
						}
					}
					if (!found) dropList.add(stack);
				}
			}

			if (dropList.isEmpty())
			{
				CommandHelper.sendMessage(player, "Nothing was dropped");
				LOG.dump(TAB + "No Drops");
				return;
			}

			for (ItemStack stack : dropList)
			{
				player.dropItem(stack, false);
				LOG.dump(TAB + "Dropped " + stack.getCount() + " x " + stack.toString());
			}
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, EntityPlayer player, String[] args, BlockPos targetPos)
	{
		return Collections.emptyList();
	}

}
