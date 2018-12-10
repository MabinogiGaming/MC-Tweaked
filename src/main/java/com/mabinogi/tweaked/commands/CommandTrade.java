package com.mabinogi.tweaked.commands;

import com.mabinogi.tweaked.Tweaked;
import com.mabinogi.tweaked.api.annotations.TweakedCommand;
import com.mabinogi.tweaked.api.commands.ITweakedCommand;
import com.mabinogi.tweaked.controllers.TweakedReflection;
import com.mabinogi.tweaked.helpers.CommandHelper;
import com.mabinogi.tweaked.helpers.trade.FakeVillager;
import com.mabinogi.tweaked.script.ScriptHelper;
import net.minecraft.entity.passive.EntityVillager.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.mabinogi.tweaked.Tweaked.LOG;
import static com.mabinogi.tweaked.controllers.TweakedLogging.TAB;
import static net.minecraft.entity.passive.EntityVillager.*;

@TweakedCommand("trade")
public class CommandTrade implements ITweakedCommand
{
	@Override
	public boolean isHidden()
	{
		return false;
	}

	@Override
	public void execute(MinecraftServer server, EntityPlayer player, String[] args)
	{
		if (args.length == 1)
		{
			LOG.dump("/tweaked trade");

			for (VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS)
			{
				List<VillagerCareer> careers = TweakedReflection.getVillagerCareers(profession);
				if (careers != null)
				{
					for (VillagerCareer career : careers)
					{
						//create output
						String out = profession.getRegistryName() + "/" + career.getName();

						//send message
						CommandHelper.sendCopyMessage(player, out);

						//dump message
						LOG.dump(TAB + out);
					}
				}
			}
		}
		else if (args.length == 2)
		{
			String id = args[1];
			LOG.dump("/tweaked trade " + id);

			Random rand = new Random();
			for (VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS)
			{
				List<VillagerCareer> careers = TweakedReflection.getVillagerCareers(profession);
				if (careers != null)
				{
					for (VillagerCareer career : careers)
					{
						if (id.equals(profession.getRegistryName() + "/" + career.getName()))
						{
							List<List<ITradeList>> trades = TweakedReflection.getVillagerTrades(career);
							if (trades != null)
							{
								int ilvl = 0;
								int count = 0;
								for (List<ITradeList> level : trades)
								{
									MerchantRecipeList recipeList = new MerchantRecipeList();

									ilvl++;
									for (ITradeList trade : level)
									{
										trade.addMerchantRecipe(new FakeVillager(), recipeList, rand);
									}

									for (MerchantRecipe recipe : recipeList)
									{
										count++;

										String out = "(Level " + ilvl + ") Trading " + ScriptHelper.stackToScript(recipe.getItemToBuy()) + " for " + ScriptHelper.stackToScript(recipe.getItemToSell());

										//dump message
										LOG.dump(TAB + out);
									}
								}

								//send message
								CommandHelper.sendMessage(player, "Dumped " + count + " recipes");
							}
						}
					}
				}
			}
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, EntityPlayer player, String[] args, BlockPos targetPos)
	{
		return Collections.emptyList();
	}
}
